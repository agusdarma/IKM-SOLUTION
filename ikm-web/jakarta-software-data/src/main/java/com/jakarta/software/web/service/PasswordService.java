package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.PasswordHistory;
import com.jakarta.software.web.entity.UserData;
import com.jakarta.software.web.mapper.PasswordMapper;
import com.jakarta.software.web.mapper.UserDataMapper;
import com.jakarta.software.web.utils.RandomStringGenerator;
import com.jakarta.software.web.utils.SecureUtils;

@Service
public class PasswordService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordService.class);
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private PasswordMapper passwordMapper;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO changePassword(int userId, String oldPassword, String newPassword, Locale language) 
			throws MmbsWebException {
		WebResultVO wrv = new WebResultVO();
		Date current = new Date();
		UserDataVO userData = userDataMapper.findUserById(userId);
		if (userData == null) {
			LOGGER.debug("Invalid userInfo with Id: [{}]", new String[] { "" + userId});
			throw new MmbsWebException(MmbsWebException.NE_INVALID_USER);				
		}
		int minLength = settingService.getSettingAsInt(SettingService.SETTING_PASSWORD_MIN_LENGTH);
		if(newPassword.length() < minLength)
		{
			throw new MmbsWebException(MmbsWebException.NE_INVALID_PASSWORD_LENGTH, Integer.toString(minLength));
		}
		if(!SecureUtils.regexPasswordChecker(minLength, newPassword)) {
			LOGGER.warn("Password need to contain alphanumeric and special symbol");
			throw new MmbsWebException(MmbsWebException.NE_INVALID_PASS_FORMAT);	
		}
		String encOldPassword = SecureUtils.passwordDigest(userData.getUserCode(), oldPassword);
		if (!encOldPassword.equals(userData.getUserPassword())) {
			LOGGER.warn("Invalid password for {}", new String[] { userData.getUserCode() });
			throw new MmbsWebException(MmbsWebException.NE_WRONG_OLD_PASSWORD);
		}
		String encNewPassword = SecureUtils.passwordDigest(userData.getUserCode(), newPassword);
		userData.setUpdatedBy(userData.getId());
		userData.setUpdatedOn(current);
		userData.setUserPassword(encNewPassword);
		userData.setPassChangedOn(timeService.getCurrentTime());
		userData.setNeedChangePass(WebConstants.NO_NEED_CHANGE_PASS);
		
		try {
			//validate user password history
			checkForOldPassword(encNewPassword, encOldPassword, userId);
			LOGGER.info("changePassword with param : " + userData);
			passwordMapper.changePassword(userData);
			
			/** SET TO USER ACTIVITY **/
			try {
				UserDataVO oriUser=new UserDataVO();
				Collection<String> excludes = new ArrayList<String>();
				excludes.add("createdOn");
				excludes.add("createdBy");
				excludes.add("updatedOn");
				excludes.add("updatedBy");
				
				userActivityService.generateHistoryActivity(excludes, oriUser, userData, userId, 
						WebConstants.ACT_MODULE_CHANGE_PASSWORD, WebConstants.ACT_TYPE_UPDATE,
						WebConstants.ACT_TABLE_USER_DATA, userId);
			} catch (Exception e) {
				LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
			}
			/** SET TO USER ACTIVITY **/
			
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.userPassword", language), messageService.getMessageFor("l.updated", language)}, language));
			wrv.setType(WebConstants.TYPE_INSERT);
			wrv.setPath(WebConstants.PATH_UPDATE_CHANGE_PASSWORD);
			
		} catch(MmbsWebException mwe){
			throw mwe;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			throw new MmbsWebException(e);
		}	
		return wrv;
	}
	
	public void checkForOldPassword(String encNewPassword, String encOldPassword, int userId)
			throws MmbsWebException {
		if (encNewPassword.equals(encOldPassword)) 
			throw new MmbsWebException(MmbsWebException.NE_PASSWORD_NEW_OLD_SAME);
		
		int checkAmount = settingService.getSettingAsInt(SettingService.SETTING_CHECK_OLD_PASSWORD);

		//6 because maximum field old_password is 6
		if (checkAmount > 6) 
		{
			checkAmount = 6;
		}
		else if (checkAmount < 0)
		{
			checkAmount = 0;
		}
		
		// no need to access password history if checkAmount = 0
		if (checkAmount == 0) return;
		PasswordHistory history = passwordMapper.findPassHistoryByUserId(userId);
		if (history == null) {
			history = new PasswordHistory();
			history.setUserId(userId);
			history.setPassHistory1(encNewPassword);
			passwordMapper.createPassHistory(history);
		} else {
			if (checkAmount >= 1 && encNewPassword.equals(history.getPassHistory1()))
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_NEW_OLD_SAME);
			if (checkAmount >= 2 && encNewPassword.equals(history.getPassHistory2()))
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_NEW_OLD_SAME);
			if (checkAmount >= 3 && encNewPassword.equals(history.getPassHistory3()))
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_NEW_OLD_SAME);
			if (checkAmount >= 4 && encNewPassword.equals(history.getPassHistory4()))
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_NEW_OLD_SAME);
			if (checkAmount >= 5 && encNewPassword.equals(history.getPassHistory5()))
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_NEW_OLD_SAME);
			if (checkAmount >= 6 && encNewPassword.equals(history.getPassHistory6()))
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_NEW_OLD_SAME);
			if (!StringUtils.isEmpty(history.getPassHistory5()))
				history.setPassHistory6(history.getPassHistory5());
			if (!StringUtils.isEmpty(history.getPassHistory4()))
				history.setPassHistory5(history.getPassHistory4());
			if (!StringUtils.isEmpty(history.getPassHistory3()))
				history.setPassHistory4(history.getPassHistory3());
			if (!StringUtils.isEmpty(history.getPassHistory2()))
				history.setPassHistory3(history.getPassHistory2());
			if (!StringUtils.isEmpty(history.getPassHistory1()))
				history.setPassHistory2(history.getPassHistory1());
			//because of this logic the newest password is always passhistory 1
			history.setPassHistory1(encNewPassword);
			history.setUpdatedBy(userId);
			passwordMapper.updatePassHistory(history);
		}
	}
	
	public int countResetPasswordUserByParam(UserDataParamVO paramVO) throws MmbsWebException{
		LOGGER.info("Processing -> Count Reset Password User By Params: {}",  paramVO );
		try {
			int record = passwordMapper.countResetPasswordUserByParam(paramVO);
			return record;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return 0;
		}
	}
	
	public List<UserDataVO> findResetPasswordUserByParam(UserDataParamVO paramVO) throws MmbsWebException{
		try {
			LOGGER.info("Processing -> Find Reset Password User By Param : {}", paramVO);
			List<UserDataVO> listUserInfo = passwordMapper.findResetPasswordUserByParam(paramVO);
			return listUserInfo;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<UserDataVO>();
		}
	}
	
	public UserData findResetPasswordUserByUserId(int id) throws MmbsWebException{
		try {
			LOGGER.info("Processing -> Find Reset Password User By UserId : {}", id);
			UserData userData = userDataMapper.findUserById(id);
			return userData;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return null;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public WebResultVO resetPassword(UserDataLoginVO loginVO, UserDataParamVO paramVO, Locale language) throws Exception {
		WebResultVO wrv = new WebResultVO();
		Date current = new Date();
		UserData userData = userDataMapper.findUserById(paramVO.getId());
		
		//validateInput(paramVO, language);
		
		userData.setUpdatedBy(loginVO.getId());
		userData.setUpdatedOn(current);
		userData.setNeedChangePass(WebConstants.NEED_CHANGE_PASS);
		userData.setInvalidCount(0);
		String password = RandomStringGenerator.generateRandomString(WebConstants.PASS_COMPOSITION_ALPHA_LENGTH, 
				WebConstants.PASS_COMPOSITION_NUMERIC_LENGTH, 
				WebConstants.PASS_COMPOSITION_SPECIALCHAR_LENGTH);
		userData.setUserPassword(SecureUtils.passwordDigest(paramVO.getUserCode().trim(), password));
		try {
						
			passwordMapper.resetPassword(userData);
			emailSenderService.sendSimpleMail("", userData.getEmail(), 
					"Reset Password Jetsweb", "Password anda adalah : "  + password);
			LOGGER.info("reset password with param : " + userData);
			/** SET TO USER ACTIVITY **/
			/*userDataMapper.insertUserDataHistory(userData);*/
			
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.resetPassword", language), messageService.getMessageFor("l.updated", language)}, language));
			wrv.setType(WebConstants.TYPE_UPDATE);
			wrv.setPath(WebConstants.PATH_UPDATE_RESET_PASSWORD);
			
		} catch (Exception e) {
//			LOGGER.error("Unable to Reset Password for userData: {}", userData.getUserCode());
			LOGGER.error("exception: " + e, e);
			throw new MmbsWebException(e);
		}
		
		return wrv;
	}
	
	public void validateInput(UserDataParamVO paramVO, Locale language) throws MmbsWebException{
		if(StringUtils.isEmpty(paramVO.getNewPassword())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.newPassword", language)});
		}
		if(StringUtils.isEmpty(paramVO.getConfirmPassword())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.confirmPassword", language)});
		}
		if(!(paramVO.getNewPassword()).equals(paramVO.getConfirmPassword())) {
			throw new MmbsWebException(MmbsWebException.NE_PASSWORD_DIFFERENT);
		}
		int minLength = settingService.getSettingAsInt(SettingService.SETTING_PASSWORD_MIN_LENGTH);
		if(paramVO.getNewPassword().length() < minLength) {
			throw new MmbsWebException(MmbsWebException.NE_INVALID_PASSWORD_LENGTH,new String[] {messageService.getMessageFor("l.password", language)});
		}
		if(!SecureUtils.regexPasswordChecker(minLength, paramVO.getNewPassword())) {
			throw new MmbsWebException(MmbsWebException.NE_INVALID_PASS_FORMAT);	
		}
	}
}
