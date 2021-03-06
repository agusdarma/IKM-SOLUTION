
package com.myproject.ikm.trx.logic.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.ikm.lib.data.ChangePasswordVO;
import com.myproject.ikm.lib.service.ChangePasswordService;
import com.myproject.ikm.lib.service.IkmEngineException;
import com.myproject.ikm.lib.utils.MessageUtils;
import com.myproject.ikm.trx.logic.BaseQueryLogic;

public class ChangePassword implements BaseQueryLogic {

	private static final Logger LOG = LoggerFactory.getLogger(ChangePassword.class);
	
	@Autowired
	private ChangePasswordService changePasswordService;

	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {						
			ChangePasswordVO changePasswordVO = mapper.readValue(data, ChangePasswordVO.class);
			changePasswordService.changePassword(changePasswordVO);
			result = MessageUtils.handleSuccess("Password has been changed", mapper);
		} catch (IkmEngineException e) {			
			result = MessageUtils.handleException(e, "", mapper);
			LOG.error("IkmEngineException when processing " + pathInfo + " Error Message : " + result);
		} catch (Exception e) {			
			result = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), mapper);
			LOG.error("Unexpected exception when processing " + pathInfo + " Error Message " + result, e);
		}
		return result;
	}

}
