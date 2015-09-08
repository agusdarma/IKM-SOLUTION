// #CreateCreditCard Sample
// Using the 'vault' API, you can store a 
// Credit Card securely on PayPal. You can
// use a saved Credit Card to process
// a payment in the future.
// The following code demonstrates how 
// can save a Credit Card on PayPal using 
// the Vault API.
// API used: POST /v1/vault/credit-card
package com.myproject.parking.trx.logic.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.parking.lib.data.ForgetPasswordVO;
import com.myproject.parking.lib.data.PaymentVO;
import com.myproject.parking.lib.service.ActivateUserService;
import com.myproject.parking.lib.service.ForgetPasswordService;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class ForgetPassword implements BaseQueryLogic {

	private static final Logger LOG = LoggerFactory.getLogger(ForgetPassword.class);
	
	@Autowired
	private ForgetPasswordService forgetPasswordService;

	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {						
			ForgetPasswordVO forgetPasswordVO = mapper.readValue(data, ForgetPasswordVO.class);
			forgetPasswordService.forgetPassword(forgetPasswordVO.getEmail());
			result = MessageUtils.handleSuccess("Email : " +  forgetPasswordVO.getEmail() 					
					+ " has been reseted. Please check your email." , mapper);
		} catch (ParkingEngineException e) {			
			result = MessageUtils.handleException(e, "", mapper);
			LOG.error("ParkingEngineException when processing " + pathInfo + " Error Message " + result, e);
		} catch (Exception e) {			
			result = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), mapper);
			LOG.error("Unexpected exception when processing " + pathInfo + " Error Message " + result, e);
		}
		return result;
	}

}
