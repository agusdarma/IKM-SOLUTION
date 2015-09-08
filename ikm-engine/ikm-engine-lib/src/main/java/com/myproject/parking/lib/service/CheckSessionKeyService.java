package com.myproject.parking.lib.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;

@Service
public class CheckSessionKeyService {
	private static final Logger LOG = LoggerFactory.getLogger(CheckSessionKeyService.class);
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	public void checkSessionKey(Date sessionDB,String email) throws ParkingEngineException {
		LOG.info("checkSessionKey with param : " + " sessionDB: " + sessionDB + " email : " + email );	
		Date now = timeService.getCurrentTime();
		long minutes = CommonUtil.dateDifferentInMinutes(sessionDB, now);
		if(minutes>=Constants.TIMEOUT_IN_MINUTES){
			LOG.error("Expired Session Key, Parameter email : " + email + " with setting : " + Constants.TIMEOUT_IN_MINUTES + " minutes.");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_EXPIRED);		
		}
		// update session db dengan current
		userDataMapper.updateSessionKeyUser(now, email);
		LOG.info("checkSessionKey Done with param : " + " sessionDB: " + now + " email : " + email );	
	}
}
