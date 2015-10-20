package com.jakarta.software.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.LoginData;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.UserLevelVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebLoginData;
import com.jakarta.software.web.entity.UserLevel;
import com.jakarta.software.web.entity.UserPreference;
import com.jakarta.software.web.mapper.UserDataMapper;
import com.jakarta.software.web.mapper.UserLevelMapper;
import com.jakarta.software.web.mapper.UserPreferenceMapper;
import com.jakarta.software.web.utils.Constants;
import com.jakarta.software.web.utils.SecureUtils;

@Service
public class SecurityService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private UserLevelMapper userLevelMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private SettingService settingService;

	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private UserPreferenceMapper userPreferenceMapper;
	
	@Autowired
	private HttpClientService httpClientService;
	
	
	public void validateUserToEngine(WebLoginData webLoginData) throws MmbsWebException, JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();		
		String s = mapper.writeValueAsString(webLoginData);
		httpClientService.sendToEnginePostMethod(s,Constants.TRX_CODE_LOGIN);
	}
	
	@Transactional
	public UserDataLoginVO validateUserLogin(LoginData loginData, String sessionId) throws MmbsWebException 
	{
		LOGGER.info("Validate Login for: {}", new String[] { loginData.toString() });
		try 
		{
			UserDataVO userData = userDataMapper.findUserDataByUserCode(loginData.getUserCode());
			
			if (userData == null) 
			{
				LOGGER.warn("Invalid userInfo with Code: [{}]", new String[] {loginData.getUserCode()});
				throw new MmbsWebException(MmbsWebException.NE_INVALID_USER);				
			}
			
			UserDataVO original = new UserDataVO();
			BeanUtils.copyProperties(userData, original);

			// check user status
			if (WebConstants.STATUS_ACTIVE != userData.getUserStatus() ) {
				LOGGER.warn("Invalid Status: [{} ]", new String[] { "" + userData.getUserStatus() });
				throw new MmbsWebException(MmbsWebException.NE_USER_DATA_INACTIVE);		
			}
			
			if(userData.getAuthStatus().equals(WebConstants.STAT_NOT_AUTHORIZED))
			{
				LOGGER.warn("Invalid Authorize Status: [{} ]", new String[] { "" + userData.getAuthStatus() });				
				throw new MmbsWebException(MmbsWebException.NE_USER_REQUEST_NOT_YET_AUTHORIZED);		
			}
			
			int invalidCount = userData.getInvalidCount();
			int maxInvalidLogin = settingService.getSettingAsInt(SettingService.SETTING_MAX_INVALID_LOGIN);
			if (invalidCount >= maxInvalidLogin)
			{
				// block user
				throw new MmbsWebException(MmbsWebException.NE_USER_DATA_BLOCKED);
			}
			
			String plainPass = loginData.getPassword();  // SecureUtils.decodeUserPassword(loginData.getPassword());
			String encPassword = SecureUtils.passwordDigest(userData.getUserCode(), plainPass);
			if (!encPassword.equals(userData.getUserPassword()))
			{
				LOGGER.warn("Invalid password for {}", new String[] { userData.getUserCode() });
				invalidCount = invalidCount + 1;
				userData.setInvalidCount(invalidCount);
				
				userDataMapper.updateUserData(userData);
				throw new MmbsWebException(MmbsWebException.NE_USER_WRONG_PASSWORD);
			}  // end if check password
			
			userData.setSessionId(sessionId);
			
			// get user level
			UserLevel userLevel = userLevelMapper.findUserLevelById(userData.getLevelId());
			if (userLevel == null || userLevel.getListMenu().size() == 0) 
			{
				throw new MmbsWebException(MmbsWebException.NE_USER_DATA_INVALID_LEVEL, new String[] { userData.getUserCode(), userData.getUserLevelDisplay() });
			}
			
			UserLevelVO levelVO = new UserLevelVO(userLevel.getListMenu());
			levelVO.setLevelName(userLevel.getLevelName());
			levelVO.setLevelDesc(userLevel.getLevelDesc());
			
			UserDataLoginVO loginVO = new UserDataLoginVO();
			BeanUtils.copyProperties(userData, loginVO);
			loginVO.setLevelVO(levelVO);
			userData.setInvalidCount(0);
			userData.setLastLoginOn(timeService.getCurrentTime());
			userDataMapper.updateUserData(userData);
			
			/** SET TO USER ACTIVITY **/
			try 
			{
				Collection<String> excludes = new ArrayList<String>();

				excludes.add("userLevelDisplay");
				excludes.add("userStatusDisplay");
				excludes.add("branchDisplay");
				excludes.add("createdByDisplay");
				excludes.add("updatedByDisplay");
				excludes.add("createdOn");
				excludes.add("createdBy");
				excludes.add("updatedOn");
				excludes.add("updatedBy");
				userActivityService.generateHistoryActivity(excludes, original, userData, loginVO.getId(), WebConstants.ACT_MODULE_LOGIN, 
						WebConstants.ACT_TYPE_LOGIN, WebConstants.ACT_TABLE_USER_DATA, loginVO.getId());
			} 
			catch (Exception e) 
			{
				LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
			}
			
			// USER PREFERENCES
			
			UserPreference userPreference = userPreferenceMapper.findUserPreferenceByID(loginVO.getId());
			loginVO.setUserPreference(userPreference);
			return loginVO;
		} catch (MmbsWebException mwe) {
			throw mwe;
		} catch (Exception e) {
			LOGGER.error("Exception: " + e, e);
			String msgError = e.getMessage();
			if (msgError.length() > 160)
				msgError = msgError.substring(0, 157) + "..";
			throw new MmbsWebException(e);
		}
	}
	
	public void logoutUser(UserDataLoginVO loginVO) {
		if (loginVO == null) return;
		LOGGER.info("Logout User: {}", new String[] { loginVO.getUserCode() });
		try {
			userDataMapper.clearSessionLogin(loginVO.getId());
		} catch (Exception e) {
			LOGGER.error("Exception: " + e, e);
		}
	}
	
	@Transactional
	public UserDataLoginVO validateForcedAuthLogin(LoginData loginData, String sessionId, int webModule, int branchId) throws MmbsWebException 
	{
		LOGGER.info("Validate Authorize Login for: {}", new String[] { loginData.toString() });
		try 
		{
			UserDataVO userData = userDataMapper.findUserDataByUserCode(loginData.getUserCode());
			
			if (userData == null) 
			{
				LOGGER.warn("Invalid userInfo with Code: [{}]", new String[] {loginData.getUserCode()});
				throw new MmbsWebException(MmbsWebException.NE_INVALID_USER);				
			}
			
			if(userData.getBranchId()!=branchId)
			{
				LOGGER.warn("Invalid userInfo with Code: [{}]", new String[] {loginData.getUserCode()});
				throw new MmbsWebException(MmbsWebException.NE_USER_BRANCH_NOT_SAME_WITH_CIF);
			}
			
			UserDataVO original = new UserDataVO();
			BeanUtils.copyProperties(userData, original);

			// check user status
			if (WebConstants.STATUS_ACTIVE != userData.getUserStatus() ) {
				LOGGER.warn("Invalid Status: [{} ]", new String[] { "" + userData.getUserStatus() });
				throw new MmbsWebException(MmbsWebException.NE_USER_DATA_INACTIVE);		
			}
			
			if(userData.getAuthStatus().equals(WebConstants.STAT_NOT_AUTHORIZED))
			{
				LOGGER.warn("Invalid Authorize Status: [{} ]", new String[] { "" + userData.getAuthStatus() });				
				throw new MmbsWebException(MmbsWebException.NE_USER_REQUEST_NOT_YET_AUTHORIZED);		
			}
			
			int invalidCount = userData.getInvalidCount();
			int maxInvalidLogin = settingService.getSettingAsInt(SettingService.SETTING_MAX_INVALID_LOGIN);
			if (invalidCount >= maxInvalidLogin)
			{
				// block user
				throw new MmbsWebException(MmbsWebException.NE_USER_DATA_BLOCKED);
			}
			
			String plainPass = loginData.getPassword();  // SecureUtils.decodeUserPassword(loginData.getPassword());
			String encPassword = SecureUtils.passwordDigest(userData.getUserCode(), plainPass);
			if (!encPassword.equals(userData.getUserPassword()))
			{
				LOGGER.warn("Invalid password for {}", new String[] { userData.getUserCode() });
				invalidCount = invalidCount + 1;
				userData.setInvalidCount(invalidCount);
				
				userDataMapper.updateUserData(userData);
				throw new MmbsWebException(MmbsWebException.NE_USER_WRONG_PASSWORD);
			}  // end if check password
			
			userData.setSessionId(sessionId);
			
			// get user level
			int validateAccess = userLevelMapper.checkMenuByLevelIdAndMenuId(webModule, userData.getLevelId());
			if(validateAccess==0)
			{
				throw new MmbsWebException(MmbsWebException.NE_USER_DATA_INVALID_LEVEL);
			}			
			UserDataLoginVO loginVO = new UserDataLoginVO();
			BeanUtils.copyProperties(userData, loginVO);
			userData.setInvalidCount(0);
			userData.setLastLoginOn(timeService.getCurrentTime());
			userDataMapper.updateUserData(userData);
			
			/** SET TO USER ACTIVITY **/
			try 
			{
				Collection<String> excludes = new ArrayList<String>();

				excludes.add("userLevelDisplay");
				excludes.add("userStatusDisplay");
				excludes.add("branchDisplay");
				excludes.add("createdByDisplay");
				excludes.add("updatedByDisplay");
				excludes.add("createdOn");
				excludes.add("createdBy");
				excludes.add("updatedOn");
				excludes.add("updatedBy");
				userActivityService.generateHistoryActivity(excludes, original, userData, loginVO.getId(), WebConstants.ACT_MODULE_LOGIN, 
						WebConstants.ACT_TYPE_LOGIN, WebConstants.ACT_TABLE_USER_DATA, loginVO.getId());
			} 
			catch (Exception e) 
			{
				LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
			}
			
			return loginVO;
		} catch (MmbsWebException mwe) {
			throw mwe;
		} catch (Exception e) {
			LOGGER.error("Exception: " + e, e);
			String msgError = e.getMessage();
			if (msgError.length() > 160)
				msgError = msgError.substring(0, 157) + "..";
			throw new MmbsWebException(e);
		}
	}
	
}
