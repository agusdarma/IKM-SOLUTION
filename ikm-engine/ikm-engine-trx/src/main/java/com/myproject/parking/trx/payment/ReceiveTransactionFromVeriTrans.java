package com.myproject.parking.trx.payment;

import id.co.veritrans.mdk.v1.exception.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.parking.lib.data.VeriTransVO;
import com.myproject.parking.lib.entity.TransactionVO;
import com.myproject.parking.lib.service.AppsTimeService;
import com.myproject.parking.lib.service.CheckUserService;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.service.VeriTransManagerService;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class ReceiveTransactionFromVeriTrans implements BaseQueryLogic {
	private static final Logger LOG = LoggerFactory.getLogger(ReceiveTransactionFromVeriTrans.class);
			
	@Autowired
	private CheckUserService checkUserService;
	
	@Autowired
	private VeriTransManagerService veriTransManagerService;
	
	@Autowired
	private AppsTimeService timeServer;
	
	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {		
			TransactionVO transaction = new TransactionVO(); 
			VeriTransVO veriTransVO = mapper.readValue(data, VeriTransVO.class);
			transaction = veriTransManagerService.charge(veriTransVO.getTokenId(),transaction,veriTransVO);
			result = MessageUtils.handleSuccess(" Nama : " + veriTransVO.getCustomerDetail().getFirstName()+"\r\n "
					+ "Email : " + veriTransVO.getCustomerDetail().getEmail()+"\r\n "
					+ "No Hp : " + veriTransVO.getCustomerDetail().getPhone()+"\r\n "
					+ "Price : " + transaction.getTotalPriceIdr()+"\r\n " 				
					+ "Area Parkir : " + veriTransVO.getListProducts().get(0).getLongName()+"\r\n "
					+ "Booking Code : " + transaction.getBookingCode(), mapper);
		} catch (ParkingEngineException e) {
			result = MessageUtils.handleException(e, "", mapper);
			LOG.error("ParkingEngineException when processing " + pathInfo + " Error Message " + result);
		} catch (RestClientException e) {			
			result = MessageUtils.handleException(e, "RestClientException when processing "+ e.getMessage(), mapper);
			LOG.error("RestClientException when processing " + pathInfo + " Error Message " + result);
		} catch (Exception e) {			
			result = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), mapper);
			LOG.error("Unexpected exception when processing " + pathInfo + " Error Message " + result);
		}
		return result;
	}
	
}
