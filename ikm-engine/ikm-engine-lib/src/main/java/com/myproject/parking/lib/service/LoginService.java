package com.myproject.parking.lib.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.data.LoginData;
import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CipherUtil;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;

@Service
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private CheckSessionKeyService checkSessionKeyService;
	
	
	public LoginData login(LoginData loginData) throws ParkingEngineException {
		LOG.debug("login with param : " + " loginData: " + loginData );	
		UserData user = userDataMapper.findUserDataByEmail(loginData.getEmail());
		if(user == null){
			LOG.error("Can't find User with email " + loginData.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
		}
		if(Constants.BLOCKED == user.getStatus()){
			LOG.error("User already blocked");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatus()){
			LOG.error("User not active");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_ACTIVE);
		}	
		String passwordDB = user.getPassword();
		String passwordInput = CipherUtil.passwordDigest(loginData.getEmail(), loginData.getPassword());
		if(!passwordDB.equals(passwordInput)){
			throw new ParkingEngineException(ParkingEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
		}
		// generate session key
		String sessionKey = user.getPhoneNo() + CommonUtil.generateAlphaNumeric(30);
		user.setSessionKey(sessionKey);
		user.setTimeGenSessionKey(timeService.getCurrentTime());
		user.setUpdatedOn(timeService.getCurrentTime());
		userDataMapper.updateLoginSessionKey(user.getId(), user.getSessionKey(), user.getTimeGenSessionKey(), user.getUpdatedOn());
		loginData.setSessionKey(sessionKey);
		loginData.setPassword("*******");
		loginData.setName(user.getName());
		loginData.setPhoneNo(user.getPhoneNo());
		loginData.setName(user.getName());
		loginData.setGroupUser(user.getGroupUser());
		return loginData;
//		LOG.info("login done with param : " + " loginData: " + loginData);
	}
	
	public void logout(LoginData loginData) throws ParkingEngineException {
		LOG.debug("logout with param : " + " loginData: " + loginData );	
		UserData user = userDataMapper.findUserDataByEmail(loginData.getEmail());
		if(user == null){
			LOG.error("Can't find User with email : " + loginData.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_FOUND);
		}
		if(Constants.BLOCKED == user.getStatus()){
			LOG.error("User already blocked");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatus()){
			LOG.error("User not active");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_ACTIVE);
		}		
		if(StringUtils.isEmpty(user.getSessionKey())){
			LOG.error("User Must Login Before make transaction, Parameter email : " + loginData.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(loginData.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + loginData.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}		
		checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), loginData.getEmail());
		Date now = timeService.getCurrentTime();
		userDataMapper.removeSessionKey(user.getId(), null, now);
		
	}
	
}
