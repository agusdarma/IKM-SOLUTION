package com.myproject.parking.lib.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.data.HistoryBookingVO;
import com.myproject.parking.lib.data.LoginData;
import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.HistoryBookingMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;

@Service
public class HistoryBookingService {
	private static final Logger LOG = LoggerFactory.getLogger(HistoryBookingService.class);
	
	@Autowired
	private HistoryBookingMapper historyBookingMapper;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private CheckSessionKeyService checkSessionKeyService;
	
	public List<HistoryBookingVO> findHistoryBooking(LoginData loginData,ObjectMapper mapper) throws ParkingEngineException {
		LOG.info("process find history booking ");	
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
		List<HistoryBookingVO> listHistoryBooking = null;
		listHistoryBooking = historyBookingMapper.findHistoryBooking(loginData.getEmail());	
		for (HistoryBookingVO historyBookingVO : listHistoryBooking) {
			historyBookingVO.setBookingDateValue(CommonUtil.displayDateTime(historyBookingVO.getBookingDate()));
		}
		LOG.info("process find history booking Done");
		return listHistoryBooking;
	}
}
