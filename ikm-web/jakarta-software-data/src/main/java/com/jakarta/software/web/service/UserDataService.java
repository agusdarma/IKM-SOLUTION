package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.UserData;
import com.jakarta.software.web.entity.UserPreference;
import com.jakarta.software.web.mapper.UserDataMapper;
import com.jakarta.software.web.mapper.UserPreferenceMapper;
import com.jakarta.software.web.utils.RandomStringGenerator;
import com.jakarta.software.web.utils.SecureUtils;

@Service
public class UserDataService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDataService.class);
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private UserPreferenceMapper userPreferenceMapper;
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Transactional(rollbackFor=Exception.class)
	public void insertUserData(UserData userData) throws MmbsWebException
	{
		try {
			userDataMapper.insertUserData(userData);
		} catch (Exception e) {
			
			LOGGER.warn("INSERT USER DATA FAIL PLEASE CHECK :" + e);
			throw new MmbsWebException(e);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateUserData(UserData userData) throws MmbsWebException
	{
		try {
			userDataMapper.updateUserData(userData);
		} catch (Exception e) {
			LOGGER.warn("UPDATE USER DATA FAIL PLEASE CHECK :" + e);
			throw new MmbsWebException(e);
		}
	}
	
	@SuppressWarnings("unused")
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO insertOrUpdateUserDataHistory(UserData userData, UserDataLoginVO loginVO, String confirmPassword, Locale language) throws MmbsWebException {
		WebResultVO wrv = new WebResultVO();
		Date now = new Date();
		userData.setCreatedBy(loginVO.getId());
		userData.setCreatedOn(now);
		userData.setUpdatedBy(loginVO.getId());
		userData.setUpdatedOn(now);
		if(StringUtils.isEmpty(userData.getUserName())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.userName")});
		}
		
		if(userData.getId()==0)
		{
			try {
				validateInsertUserData(userData, confirmPassword);
			} catch (MmbsWebException MWE) {
				throw MWE;
			}
		}
		if(WebConstants.APP_FLOW_USER_DATA_AUTH==1) //USE AUTHORIZE FLOW
		{
			userData.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			try {
				wrv=insertFlowUseAuthorization(userData, loginVO, language);
			} catch (MmbsWebException MWE) {
				throw MWE;
			}			
		}
		else if(WebConstants.APP_FLOW_USER_DATA_AUTH==0)
		{
			userData.setAuthStatus(WebConstants.STAT_APPROVED);
			try {
				wrv=insertFlowNoAuthorization(userData, loginVO, language);
			} catch (MmbsWebException MWE) {
				throw MWE;
			}	
		}		

		return wrv;
	}
	
	private WebResultVO insertFlowNoAuthorization(UserData userData, UserDataLoginVO loginVO, Locale language) throws MmbsWebException
	{
		WebResultVO wrv = new WebResultVO();
		if(userData.getId()==0)
		{
			userData.setUserPassword(SecureUtils.passwordDigest(userData.getUserCode().trim(), userData.getUserPassword()));
			userData.setSessionId("");
			LOGGER.info("Insert user data history: {}", userData);
			try {
				userData.setNeedChangePass(WebConstants.NEED_CHANGE_PASS);
				userData.setActivityType(WebConstants.ACT_TYPE_INSERT);
				userDataMapper.insertUserData(userData);		
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
						new String[] {messageService.getMessageFor("l.newUserRegistration", language), 
						messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);		
			} catch (Exception e) {
				LOGGER.warn("Gagal insert user data :" + e);
				throw new MmbsWebException(e);
			}
		}
		else
		{
			LOGGER.info("Update user data: {}", userData);
			try{
				userData.setActivityType(WebConstants.ACT_TYPE_UPDATE);
				userDataMapper.changeUserDataAuthStatus(userData);
				userDataMapper.insertUserDataHistory(userData);
			}catch(Exception e){
				LOGGER.warn("Gagal update user data :" + e);
				throw new MmbsWebException(e);
			}
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
					new String[] {messageService.getMessageFor("l.userData", language), 
					messageService.getMessageFor("l.updated", language)}, language));
			wrv.setType(WebConstants.TYPE_UPDATE);
			wrv.setPath(WebConstants.PATH_UPDATE_USER_DATA);
		}
	
		/** SET TO USER ACTIVITY **/
		try {
			UserData oriUser=new UserData();
			Collection<String> excludes = new ArrayList<String>();
			excludes.add("createdOn");
			excludes.add("createdBy");
			excludes.add("updatedOn");
			excludes.add("updatedBy");
			
			userActivityService.generateHistoryActivity(excludes, oriUser, userData, loginVO.getId(), 
					WebConstants.ACT_MODULE_USER_DATA, WebConstants.ACT_TYPE_INSERT,
					WebConstants.ACT_TABLE_USER_DATA, loginVO.getId());
		} catch (Exception e) {
			LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
		}
		/** SET TO USER ACTIVITY **/
		return wrv;
	}
	
	private WebResultVO insertFlowUseAuthorization(UserData userData, UserDataLoginVO loginVO, Locale language) throws MmbsWebException
	{		
		WebResultVO wrv = new WebResultVO();
		if(userData.getId()==0)
		{
			userData.setUserPassword(SecureUtils.passwordDigest(userData.getUserCode().trim(), userData.getUserPassword()));
			userData.setSessionId("");
			LOGGER.info("Insert user data history: {}", userData);
			try {
				userData.setNeedChangePass(WebConstants.NEED_CHANGE_PASS);
				userDataMapper.insertUserDataHistory(userData);		
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.successInsert",
						new String[] {messageService.getMessageFor("l.userData", language),
							messageService.getMessageFor("l.userData", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);		
			} catch (Exception e) {
				LOGGER.warn("Gagal insert user data :" + e);
				throw new MmbsWebException(e);
			}
		}
		else
		{
			LOGGER.info("Update user data: {}", userData);
			try{
				if(userData.getActivityType().equals(WebConstants.ACT_TYPE_RESET_PASSWORD))
				{
					userData.setNeedChangePass(WebConstants.NEED_CHANGE_PASS);
					userData.setUserPassword("@@@@@@");
					wrv.setMessage(messageService.getMessageFor("rm.successUpdate",new String[] {messageService.getMessageFor("d.resetPassword")}, language));
					wrv.setPath(WebConstants.PATH_UPDATE_RESET_PASSWORD);
				}
				else
				{
					wrv.setMessage(messageService.getMessageFor("rm.successUpdate",new String[] {messageService.getMessageFor("l.userData")}, language));
					wrv.setPath(WebConstants.PATH_UPDATE_USER_DATA);
				}
				userDataMapper.changeUserDataAuthStatus(userData);
				userDataMapper.insertUserDataHistory(userData);
			}catch(Exception e){
				LOGGER.warn("Gagal update user data :" + e);
				throw new MmbsWebException(e);
			}
			wrv.setRc(WebConstants.RESULT_SUCCESS);			
			wrv.setType(WebConstants.TYPE_UPDATE);
		}
	
		/** SET TO USER ACTIVITY **/
		try {
			UserDataVO oriUser=new UserDataVO();
			Collection<String> excludes = new ArrayList<String>();
			excludes.add("createdOn");
			excludes.add("createdBy");
			excludes.add("updatedOn");
			excludes.add("updatedBy");
			
			userActivityService.generateHistoryActivity(excludes, oriUser, userData, loginVO.getId(), 
					WebConstants.ACT_MODULE_USER_DATA, WebConstants.ACT_TYPE_INSERT,
					WebConstants.ACT_TABLE_USER_DATA_HISTORY, loginVO.getId());
		} catch (Exception e) {
			LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
		}
		/** SET TO USER ACTIVITY **/
		return wrv;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO authorizeUserData(UserDataVO userDataVO, UserDataLoginVO loginVO, Locale language) throws MmbsWebException
	{
		WebResultVO wrv = new WebResultVO();
		try {
			Date now = new Date();
			userDataVO.setUpdatedBy(loginVO.getId());
			userDataVO.setUpdatedOn(now);
			userDataVO.setAuthBy(loginVO.getId());
			userDataVO.setAuthOn(now);
			
			//authorize
			UserData userData=new UserData();
			BeanUtils.copyProperties(userDataVO, userData);
			userData.setId(userDataVO.getUserId());
			if(userDataVO.getUserId()==0)
			{			
				if(userDataVO.getAuthStatus().equals(WebConstants.STAT_APPROVED))//tidak reject
				{
					//insert to user data
					if(settingService.getSettingAsInt(SettingService.SETTING_USER_PASSWORD_MODE)==WebConstants.USE_RANDOMIZE_PASS_YES)
					{
						String password = RandomStringGenerator.generateRandomString(WebConstants.PASS_COMPOSITION_ALPHA_LENGTH, 
								WebConstants.PASS_COMPOSITION_NUMERIC_LENGTH, 
								WebConstants.PASS_COMPOSITION_SPECIALCHAR_LENGTH);
						userData.setUserPassword(SecureUtils.passwordDigest(userData.getUserCode().trim(), password));
						insertUserData(userData);
						emailSenderService.sendSimpleMail("", userData.getEmail(), 
								"Registrasi User Jetsweb", "Password anda adalah : "  + password);
					}
					else
					{
						insertUserData(userData);
					}					
					UserPreference userPreference=fillUserPreference(userData.getId());
					insertUserPreference(userPreference);
					wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
							new String[] {messageService.getMessageFor("l.newUserRegistration", language), 
								messageService.getMessageFor("l.approved", language)}, language));
					userDataVO.setUserId(userData.getId());
					/** SET TO USER ACTIVITY **/
					try {
						UserData oriUser=new UserData();
						Collection<String> excludes = new ArrayList<String>();
						excludes.add("createdOn");
						excludes.add("createdBy");
						excludes.add("updatedOn");
						excludes.add("updatedBy");
						
						userActivityService.generateHistoryActivity(excludes, oriUser, userData, loginVO.getId(), 
								WebConstants.ACT_MODULE_USER_DATA, WebConstants.ACT_TYPE_INSERT,
								WebConstants.ACT_TABLE_USER_DATA, loginVO.getId());
					} catch (Exception e) {
						LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
					}		
					/** SET TO USER ACTIVITY **/
					
					/** SET TO USER ACTIVITY **/
					try {
						UserPreference oriUserPref=new UserPreference();
						Collection<String> excludes = new ArrayList<String>();
						
						userActivityService.generateHistoryActivity(excludes, oriUserPref, userPreference, loginVO.getId(), 
								WebConstants.ACT_MODULE_USER_DATA, WebConstants.ACT_TYPE_INSERT,
								WebConstants.ACT_TABLE_USER_PREFERENCE, loginVO.getId());
					} catch (Exception e) {
						LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
					}		
					/** SET TO USER ACTIVITY **/
				}
				else
				{
					//reject
					wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
							new String[] {messageService.getMessageFor("l.newUserRegistration", language), 
								messageService.getMessageFor("l.rejected", language)}, language));
				}
			}
			else
			{
				UserData oriUser=findUserById(userData.getId());
				String activityType = "";
				//update to user data
				if(userDataVO.getAuthStatus().equals(WebConstants.STAT_APPROVED)) //tidak reject
				{					
					//update to user data
					if(userData.getActivityType().equals(WebConstants.ACT_TYPE_RESET_PASSWORD))
					{
						activityType=WebConstants.ACT_TYPE_RESET_PASSWORD;
						if(settingService.getSettingAsInt(SettingService.SETTING_USER_PASSWORD_MODE)==WebConstants.USE_RANDOMIZE_PASS_YES)
						{
							String password = RandomStringGenerator.generateRandomString(WebConstants.PASS_COMPOSITION_ALPHA_LENGTH, 
									WebConstants.PASS_COMPOSITION_NUMERIC_LENGTH, 
									WebConstants.PASS_COMPOSITION_SPECIALCHAR_LENGTH);
							userData.setUserPassword(SecureUtils.passwordDigest(userData.getUserCode().trim(), password));
							updateUserData(userData);							
							emailSenderService.sendSimpleMail("", userData.getEmail(), 
									"Reset Password User Jetsweb", "Password anda adalah : "  + password);
							wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
									new String[] {messageService.getMessageFor("l.resetPassword", language),
									messageService.getMessageFor("l.approved", language)}, language));
						}
					}
					else
					{
						activityType=WebConstants.ACT_TYPE_UPDATE;
						updateUserData(userData);
						wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
								new String[] {messageService.getMessageFor("l.userChanges", language),
								messageService.getMessageFor("l.approved", language)}, language));
					}
				}
				else //reject
				{
					activityType=WebConstants.ACT_TYPE_UPDATE;
					wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
							new String[] {messageService.getMessageFor("l.userChanges", language),
								messageService.getMessageFor("l.rejected", language)}, language));
					userData.setAuthStatus(WebConstants.STAT_APPROVED);
					userDataMapper.changeUserDataAuthStatus(userData);
					userData.setAuthStatus(WebConstants.STAT_REJECTED);
				}
				
				
				/** SET TO USER ACTIVITY **/
				try {
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriUser, userData, loginVO.getId(), 
							WebConstants.ACT_MODULE_AUTH_USER_DATA, activityType,
							WebConstants.ACT_TABLE_USER_DATA, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/
			}
			userDataMapper.changeUserDataHistoryAuthStatus(userDataVO);
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setPath(WebConstants.PATH_UPDATE_AUTH_USER_DATA);
			wrv.setType(WebConstants.TYPE_UPDATE);
			return wrv;
		} catch (Exception e) {
			LOGGER.error("error: " + e );
			throw new MmbsWebException(e);
		}
		
	}
	
	public void updateNewUser(UserData userData, UserDataLoginVO loginVO){
		try {
			Date now = new Date();
			userData.setCreatedBy(loginVO.getId());
			userData.setCreatedOn(now);
			userData.setUpdatedBy(loginVO.getId());
			userData.setUpdatedOn(now);
			userDataMapper.updateUserData(userData);
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
		}
	}
	
	public Integer countLevelUsedByName(int levelId){
		try {
			int count = userDataMapper.countLevelUsedByName(levelId);
			return count;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return 0;
		}
	}
	
	public Integer countUserValidate(String userCode){
		try {
			int count = userDataMapper.countUserValidate(userCode);
			return count;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return 0;
		}
	}
	
	public Integer countUserValidateHistory(String userCode){
		try {
			int count = userDataMapper.countUserValidateHistory(userCode);
			return count;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return 0;
		}
	}

	public List<UserDataVO> findUserByParam(UserDataParamVO addNewUserParamVO) {
		try {
			addNewUserParamVO.setAuthStatus(WebConstants.STAT_APPROVED);
			 List<UserDataVO> listUser = userDataMapper.findUserByParam(addNewUserParamVO);
			return listUser; 
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return null;
		}
	}

	public int countUserByParam(UserDataParamVO addNewUserParamVO) {
		try {
			addNewUserParamVO.setAuthStatus(WebConstants.STAT_APPROVED);
			int count = userDataMapper.countUserByParam(addNewUserParamVO);
			return count;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return 0;
		}
	}

	public UserData findUserById(int userId) {
		try {
			UserData user = userDataMapper.findUserById(userId);
			return user;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return null;
		}
	}
	
	public UserDataVO findUserByIdHistory(int userId) {
		try {
			UserDataVO user = userDataMapper.findUserByIdHistory(userId);
			return user;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return null;
		}
	}
	
	public List<UserDataVO> findUserHistoryByParam(UserDataParamVO addNewUserParamVO) {
		try {
			addNewUserParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			 List<UserDataVO> listUser = userDataMapper.findUserHistoryByParam(addNewUserParamVO);
			return listUser; 
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return null;
		}
	}

	public int countUserHistoryByParam(UserDataParamVO addNewUserParamVO) {
		try {
			addNewUserParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			int count = userDataMapper.countUserHistoryByParam(addNewUserParamVO);
			return count;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return 0;
		}
	}
	
	// USER PREFERENCES
	@Transactional(rollbackFor=Exception.class)
	public void insertUserPreference(UserPreference userPreference) throws MmbsWebException
	{
		try {
			userPreferenceMapper.insertUserPreference(userPreference);
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}
	
	public UserPreference fillUserPreference(int userID)
	{
		UserPreference userPreference = new UserPreference();
		userPreference.setFontFamily(WebConstants.DEFAULT_FONTFAMILY);
		userPreference.setFontSize(WebConstants.DEFAULT_FONTSIZE);
		userPreference.setLanguage(WebConstants.DEFAULT_LANGUAGE);
		userPreference.setTheme(WebConstants.DEFAULT_THEME);
		userPreference.setUserID(userID);
		return userPreference;
	}
	
	public UserPreference findUserPreferenceByID(int userID)
	{
		UserPreference userPreference = userPreferenceMapper.findUserPreferenceByID(userID);
		return userPreference;
	}
	
	public void updateUserPreferenceByID(int userID, UserPreference userPreference, UserPreference prevUserPreference) throws MmbsWebException 
	{
		userPreference.setUserID(userID);
		userPreferenceMapper.updateUserPreferenceByID(userPreference);
		LOGGER.info("update user preference with param : {}" + userPreference);
		/** SET TO USER ACTIVITY **/
		try {
			Collection<String> excludes = new ArrayList<String>();
			
			userActivityService.generateHistoryActivity(excludes, prevUserPreference, userPreference, userID, 
					WebConstants.ACT_MODULE_USER_PREFERENCE, WebConstants.ACT_TYPE_UPDATE,
					WebConstants.ACT_TABLE_USER_PREFERENCE, userPreference.getUserID());
		} catch (Exception e) {
			LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
		}		
		/** SET TO USER ACTIVITY **/
	}
	
	public UserDataVO findCurrentUserById(int userId)
	{
		try {
			UserDataVO user = userDataMapper.findUserById(userId);
			return user;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return null;
		}
	}
	
	private void validateInsertUserData(UserData userData, String confirmPassword) throws MmbsWebException
	{
		LOGGER.debug("validate properties user data!");
		if(StringUtils.isEmpty(userData.getUserCode())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.userCode")});
		}
		
		if (!StringUtils.isEmpty(userData.getEmail())) 
		{
			if (!userData.getEmail().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) 
			{
				throw new MmbsWebException(MmbsWebException.NE_INVALID_EMAIL_FORMAT, 
						new String[] { messageService.getMessageFor("l.email") });
			}
		} 
		else 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { messageService.getMessageFor("l.email") });
		}
		
		if(settingService.getSettingAsInt(SettingService.SETTING_USER_PASSWORD_MODE)==WebConstants.USE_RANDOMIZE_PASS_NO)
		{
			//VALIDATE PASS
			if(StringUtils.isEmpty(userData.getUserPassword())) {
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.password")});
			}
			
			int minimumLength = settingService.getSettingAsInt(SettingService.SETTING_PASSWORD_MIN_LENGTH);
			if(userData.getUserPassword().length() < minimumLength){
				throw new MmbsWebException(MmbsWebException.NE_INVALID_PASSWORD_LENGTH, Integer.toString(minimumLength));
			}
			if(StringUtils.isEmpty(confirmPassword)) {
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.confirmPassword")});
			}
			if(!(userData.getUserPassword()).equals(confirmPassword)) {
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_DIFFERENT);
			}
			if(!SecureUtils.regexPasswordChecker(settingService.getSettingAsInt(SettingService.SETTING_PASSWORD_MIN_LENGTH), userData.getUserPassword())) {
				throw new MmbsWebException(MmbsWebException.NE_INVALID_PASS_FORMAT);	
			}
		}
		else
		{
			userData.setUserPassword("@@@@@@@@@@@");
		}
		int countUser = countUserValidate(userData.getUserCode().toUpperCase());
		if(countUser!=0)
		{
			throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.userCode")});	
		}
		else
		{
			countUser=countUserValidateHistory(userData.getUserCode().toUpperCase());
			if(countUser!=0)
			{
				throw new MmbsWebException(MmbsWebException.NE_USER_REGISTRATION_NOT_YET_AUTHORIZED,new String[] {messageService.getMessageFor("l.userData")});	
			}
		}
		
	}
}
