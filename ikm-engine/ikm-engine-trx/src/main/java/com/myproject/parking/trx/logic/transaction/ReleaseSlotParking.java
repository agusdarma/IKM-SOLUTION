package com.myproject.parking.trx.logic.transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.parking.lib.data.SlotsParkingVO;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.service.ReleaseSlotParkingService;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class ReleaseSlotParking implements BaseQueryLogic {

	private static final Logger LOG = LoggerFactory.getLogger(ReleaseSlotParking.class);

	@Autowired
	private ReleaseSlotParkingService releaseSlotParkingService;
	
	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {						
			SlotsParkingVO slotsParkingVO = mapper.readValue(data, SlotsParkingVO.class);
			releaseSlotParkingService.releaseSlotParking(slotsParkingVO);
			result = MessageUtils.handleSuccess("Release Slot Parking " + slotsParkingVO.getMallName() + " Success" , mapper);
		} catch (ParkingEngineException e) {
			result = MessageUtils.handleException(e, e.getMessage(), mapper);
			LOG.error("ParkingEngineException when processing " + pathInfo + " Error Message : " + result);
		} catch (Exception e) {
			result = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), mapper);
			LOG.error("Unexpected exception when processing " + pathInfo + " Error Message " + result, e);
		}
		return result;
	}

}
