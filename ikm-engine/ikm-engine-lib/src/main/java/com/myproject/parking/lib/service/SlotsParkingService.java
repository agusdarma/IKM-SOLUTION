package com.myproject.parking.lib.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.data.SlotsParkingVO;
import com.myproject.parking.lib.entity.Booking;
import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.SlotsParkingMapper;
import com.myproject.parking.lib.mapper.TransactionDetailMapper;
import com.myproject.parking.lib.mapper.TransactionMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.Constants;

@Service
public class SlotsParkingService {
	private static final Logger LOG = LoggerFactory.getLogger(SlotsParkingService.class);
	
	@Autowired
	private SlotsParkingMapper slotsParkingMapper;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@Autowired
	private TransactionDetailMapper transactionDetailMapper;
	
	@Autowired
	private CheckSessionKeyService checkSessionKeyService;
	
	
	public SlotsParkingVO findSlotsByMall(SlotsParkingVO slotsParkingVO) throws ParkingEngineException {
		LOG.debug("process find Slots By Mall with param slotsParkingVO : " + slotsParkingVO);	
		UserData user = userDataMapper.findUserDataByEmail(slotsParkingVO.getEmail());
		if(user == null){
			LOG.error("Can't find User with email : " + slotsParkingVO.getEmail());
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
			LOG.error("User Must Login Before make transaction, Parameter email : " + slotsParkingVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(slotsParkingVO.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + slotsParkingVO.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}		
		checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), slotsParkingVO.getEmail());
		SlotsParkingVO slotsAvailable = null;
		slotsAvailable = slotsParkingMapper.findSlotsParkingAvailable(slotsParkingVO.getMallName());
		if(slotsAvailable == null){
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SLOT_NOT_AVAILABLE);
		}
			slotsAvailable.setEmail(slotsParkingVO.getEmail());
			slotsAvailable.setSessionKey(slotsParkingVO.getSessionKey());
			Booking booking = new Booking();
			booking.setName(user.getName());
			booking.setEmail(user.getEmail());
			booking.setMallName(slotsAvailable.getMallName());
			booking.setIdSlot(slotsAvailable.getIdSlot());
			booking.setPhoneNo(user.getPhoneNo());	
			booking.setBookingCode(bookingService.generateBookingCode(user.getPhoneNo()));
			booking.setBookingId(bookingService.generateBookingId(user.getPhoneNo()));
			booking.setBookingDate(timeService.getCurrentTime());
			bookingService.saveBooking(booking,slotsAvailable.getIdSlot());
			slotsAvailable.setBookingId(booking.getBookingId());
		LOG.debug("process find Slots By Mall DONE with param slotsAvailable : " + slotsAvailable);
		return slotsAvailable;
	}
	
}
