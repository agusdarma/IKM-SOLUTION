package com.myproject.parking.lib.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.data.BookingVO;
import com.myproject.parking.lib.entity.Booking;
import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.BookingMapper;
import com.myproject.parking.lib.mapper.TransactionDetailMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;

@Service
public class CheckInService {
	private static final Logger LOG = LoggerFactory.getLogger(CheckInService.class);
	
	@Autowired
	private BookingMapper bookingMapper;
	
	@Autowired
	private TransactionDetailMapper transactionDetailMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private CheckSessionKeyService checkSessionKeyService;
	
	public void checkInConfirm(BookingVO bookingVO) throws ParkingEngineException {
		UserData user = userDataMapper.findUserDataByEmail(bookingVO.getEmail());
		if(user == null){
			LOG.error("Can't find User with email : " + bookingVO.getEmail());
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
			LOG.error("User Must Login Before make transaction, Parameter email : " + bookingVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(bookingVO.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + bookingVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}		
		checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), bookingVO.getEmail());
		Booking booking2 = null;
		booking2 = bookingMapper.findBookingByCodeAllowCheckIn(bookingVO.getBookingCode());
		if(booking2 == null){
			throw new ParkingEngineException(ParkingEngineException.BOOKING_CODE_NOT_AVAILABLE);
		}
		// check waktu hanya 2 jam
		checkExpiredBookingCode(booking2.getBookingDate());
		
		booking2.setBookingStatus(Constants.STATUS_ALREADY_CHECK_IN);
		bookingMapper.updateBookingStatus(booking2);
		
	}
	
	public BookingVO allowCheckIn(BookingVO bookingVO) throws ParkingEngineException {
		UserData user = userDataMapper.findUserDataByEmail(bookingVO.getEmail());
		if(user == null){
			LOG.error("Can't find User with email : " + bookingVO.getEmail());
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
			LOG.error("User Must Login Before make transaction, Parameter email : " + bookingVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(bookingVO.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + bookingVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}		
		checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), bookingVO.getEmail());
		Booking booking2 = null;
		booking2 = bookingMapper.findBookingByCodeAllowCheckIn(bookingVO.getBookingCode());
		if(booking2 == null){
			throw new ParkingEngineException(ParkingEngineException.BOOKING_CODE_NOT_AVAILABLE);
		}
		// check waktu hanya 2 jam
		checkExpiredBookingCode(booking2.getBookingDate());
				
		bookingVO.setBookingCode(booking2.getBookingCode());
		bookingVO.setId(booking2.getId());
		bookingVO.setBookingDate(booking2.getBookingDate());
		bookingVO.setBookingDateValue(CommonUtil.displayDateTime(booking2.getBookingDate()));
		bookingVO.setBookingId(booking2.getBookingId());
		bookingVO.setBookingStatus(booking2.getBookingStatus());
		if(Constants.STATUS_ALREADY_PAY==booking2.getBookingStatus()){
			bookingVO.setBookingStatusValue(Constants.BOOKING_ALREADY_PAY_VALUE);	
		}else {
			bookingVO.setBookingStatusValue(Constants.BOOKING_VALUE);
		}
		bookingVO.setEmail(booking2.getEmail());
		bookingVO.setIdSlot(booking2.getIdSlot());
		bookingVO.setMallName(booking2.getMallName());
		bookingVO.setPhoneNo(booking2.getPhoneNo());
		bookingVO.setName(booking2.getName());
		return bookingVO;
		
	}
	
	
	private void checkExpiredBookingCode(Date bookingCodeDB) throws ParkingEngineException {			
		Date now = timeService.getCurrentTime();
		LOG.info("checkExpiredBookingCode with param : " + " BookingCodeDate: " + bookingCodeDB + " current time : " + now );
		long hours = CommonUtil.dateDifferentInHours(bookingCodeDB, now);
		LOG.info("different hours : " + hours);
		if(hours>Constants.EXPIRED_BOOKING_CODE_IN_HOURS){
			LOG.error("Expired Booking Code with setting : " + Constants.EXPIRED_BOOKING_CODE_IN_HOURS + " hours.");
			throw new ParkingEngineException(ParkingEngineException.BOOKING_CODE_EXPIRED);		
		}		
		LOG.info("checkExpiredBookingCode Done with param : " + " BookingCodeDate: " + bookingCodeDB + " current time : " + now );	
	}
	
}
