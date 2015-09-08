package com.myproject.parking.lib.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.parking.lib.data.BookingVO;
import com.myproject.parking.lib.entity.Booking;
import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.BookingMapper;
import com.myproject.parking.lib.mapper.TransactionDetailMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.lib.utils.Constants;

@Service
public class BookingService {
	private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);
	
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
	
	public String generateBookingCode(String phoneNo){
		String bookingCode = "BCODE"+CommonUtil.generateNumeric(Constants.LENGTH_GENERATE_BOOKING_CODE);
		return bookingCode;		
	}
	
	public String generateBookingId(String phoneNo){
		String bookingId = "BID"+phoneNo+CommonUtil.generateNumeric(Constants.LENGTH_GENERATE_BOOKING_CODE);
		return bookingId;		
	}
	
	protected void updateSlotStatus(int idSlot) {	
		// update mall slot menjadi 1 alias booked tapi belum bayar
		transactionDetailMapper.updateMallSlotStatus(idSlot);
	}
	
	public void checkBookingIdAllowPay(BookingVO bookingVO) throws ParkingEngineException {
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
		booking2 = bookingMapper.findBookingByIdAllowPay(bookingVO.getBookingId());
		if(booking2 == null){
			throw new ParkingEngineException(ParkingEngineException.BOOKING_ID_EXPIRED_TO_PAY);
		}
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void saveBooking(Booking booking,int idSlot) throws ParkingEngineException {
		LOG.info("saveBooking with param : " + booking + " mall slot id : " + idSlot);
		bookingMapper.createBooking(booking);
		transactionDetailMapper.updateMallSlotStatus(idSlot);
		
		LOG.info("saveBooking done with param : " + booking);
	}
}
