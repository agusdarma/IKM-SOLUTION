package com.myproject.parking.trx.logic.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.service.AppsTimeService;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.service.UserDataService;
import com.myproject.parking.lib.utils.CipherUtil;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class UserRegistration implements BaseQueryLogic {
	private static final Logger LOG = LoggerFactory.getLogger(UserRegistration.class);
			
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private UserDataService userDataService;
	
	@Override	
	public String process(HttpServletRequest request, HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {
			UserData user = mapper.readValue(data, UserData.class);
			user.setPassword(CipherUtil.passwordDigest(user.getEmail(), user.getPassword()));
			user.setPhoneNo(CommonUtil.normalizePhoneNo(user.getPhoneNo()));
			user.setCreatedBy(Constants.SYSTEM);
			user.setCreatedOn(timeService.getCurrentTime());
			user.setUpdatedBy(Constants.SYSTEM);
			user.setUpdatedOn(timeService.getCurrentTime());
			user.setStatus(Constants.PENDING);
			user.setActivateKey(user.getPhoneNo() + CommonUtil.generateAlphaNumeric(10));
			user.setGroupUser(Constants.USER);
			
			userDataService.processingRegistrationUser(user);
			result = MessageUtils.handleSuccess("[Registration User : " + user.getName() + "]"+" [PhoneNo: " + user.getPhoneNo() + "]"+" [Email: " + user.getEmail() + "]"+" Created Date : " + user.getCreatedOn() , mapper);
			return result;
		} catch (ParkingEngineException e) {
			LOG.error("ParkingEngineException when processing " + pathInfo, e);
			result = MessageUtils.handleException(e, "", mapper);
		} catch (Exception e) {
			LOG.error("Unexpected exception when processing " + pathInfo, e);
			result = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), mapper);
		}
		return result;
	}
	
}
