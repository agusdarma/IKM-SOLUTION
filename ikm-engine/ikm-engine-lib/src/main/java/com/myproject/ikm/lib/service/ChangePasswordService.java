package com.myproject.ikm.lib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ikm.lib.data.ChangePasswordVO;
import com.myproject.ikm.lib.entity.User;
import com.myproject.ikm.lib.mapper.UserDataMapper;
import com.myproject.ikm.lib.utils.CipherUtil;
import com.myproject.ikm.lib.utils.Constants;

@Service
public class ChangePasswordService {
	private static final Logger LOG = LoggerFactory.getLogger(ChangePasswordService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	
	public void changePassword(ChangePasswordVO changePasswordVO) throws IkmEngineException {
		LOG.info("changePassword with param : " + " ChangePasswordVO: " + changePasswordVO);	
		User user = userDataMapper.findUserByKodeSekolahAndNoIndukAndUserType(changePasswordVO.getKodeSekolah(), changePasswordVO.getNoInduk(),changePasswordVO.getUserType());
		if(user == null){
			LOG.error("Can't find User with parameter: " + changePasswordVO);
			throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
		}
		if(Constants.BLOCKED == user.getStatusUser()){
			LOG.error("User already blocked");
			throw new IkmEngineException(IkmEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatusUser()){
			LOG.error("User not active");
			throw new IkmEngineException(IkmEngineException.ENGINE_USER_NOT_ACTIVE);
		}
		String passwordDB = user.getPassword();
		String passwordInput = CipherUtil.passwordDigest(user.getKodeSekolah()+user.getNoInduk(), changePasswordVO.getOldPassword());
		if(!passwordDB.equalsIgnoreCase(passwordInput)){
			throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_OLD_PASSWORD);
		}
		
		passwordInput = CipherUtil.passwordDigest(user.getKodeSekolah()+user.getNoInduk(), changePasswordVO.getNewPassword());
		userDataMapper.updatePasswordUser(changePasswordVO.getKodeSekolah(), changePasswordVO.getNoInduk(),changePasswordVO.getUserType()
				, passwordInput, timeService.getCurrentTime());
		
		
		LOG.info("changePassword done with param : " + changePasswordVO);
	}
	
	
}
