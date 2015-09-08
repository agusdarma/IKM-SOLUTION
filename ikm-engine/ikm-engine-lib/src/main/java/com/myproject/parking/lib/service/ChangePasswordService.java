package com.myproject.parking.lib.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.data.ChangePasswordVO;
import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CipherUtil;
import com.myproject.parking.lib.utils.Constants;
import com.myproject.parking.lib.utils.EmailSender;

@Service
public class ChangePasswordService {
	private static final Logger LOG = LoggerFactory.getLogger(ChangePasswordService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private CheckSessionKeyService checkSessionKeyService;
	
	@Autowired
	private EmailSender emailSender;
	
	public void changePassword(ChangePasswordVO changePasswordVO) throws ParkingEngineException {
		LOG.info("changePassword with param : " + " ChangePasswordVO: " + changePasswordVO.getEmail());	
		UserData user = userDataMapper.findUserDataByEmail(changePasswordVO.getEmail());
		if(user == null){
			LOG.error("Can't find User with email : " + changePasswordVO.getEmail());
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
			LOG.error("User Must Login Before make transaction, Parameter email : " + changePasswordVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(changePasswordVO.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + changePasswordVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}
		
		String passwordDB = user.getPassword();
		String passwordInput = CipherUtil.passwordDigest(user.getEmail(), changePasswordVO.getPassword());
		if(!passwordDB.equalsIgnoreCase(passwordInput)){
			throw new ParkingEngineException(ParkingEngineException.ENGINE_WRONG_OLD_PASSWORD);
		}
		
		checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), changePasswordVO.getEmail());
		
		String passwordHash = CipherUtil.passwordDigest(user.getEmail(), changePasswordVO.getNewPassword());
		userDataMapper.updatePasswordUser(user.getEmail(), passwordHash, timeService.getCurrentTime());
		
		LOG.info("changePassword done with param : " + " EMAIL: " + changePasswordVO.getEmail());
	}
	
	
}
