package com.myproject.parking.lib.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.Constants;

@Service
public class ActivateUserService {
	private static final Logger LOG = LoggerFactory.getLogger(ActivateUserService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	public void activateUser(String actKey,String email,String noHp) throws ParkingEngineException {
		LOG.info("activateUser with param : " + "ACT KEY : " + actKey + " EMAIL: " + email + " NO HP : " + noHp);			
		if (StringUtils.isEmpty(actKey)||StringUtils.isEmpty(email)||StringUtils.isEmpty(noHp)) {					
			throw new ParkingEngineException(ParkingEngineException.PARAMETER_NOT_COMPLETE);															
		}			
		UserData user = userDataMapper.findUserByEmailAndPhoneNoAndActKey(email, noHp,actKey);
		if(user == null){
			LOG.error("Can't find User");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_FOUND);
		}
		if(Constants.ACTIVE == user.getStatus()){
			LOG.error("User already activated");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_ALREADY_ACTIVATED);
		}
		if(Constants.BLOCKED == user.getStatus()){
			LOG.error("User already blocked");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_BLOCKED);
		}
		// update status user menjadi active
		// update updated on		
		userDataMapper.updateStatusUser(email, Constants.ACTIVE, timeService.getCurrentTime());
		LOG.info("activateUser done with param : " + "ACT KEY : " + actKey + " EMAIL: " + email + " NO HP : " + noHp);
	}
}
