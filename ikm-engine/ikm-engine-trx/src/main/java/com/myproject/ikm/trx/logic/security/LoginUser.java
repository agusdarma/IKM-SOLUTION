package com.myproject.ikm.trx.logic.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.ikm.lib.data.LoginData;
import com.myproject.ikm.lib.data.RespLoginVO;
import com.myproject.ikm.lib.service.IkmEngineException;
import com.myproject.ikm.lib.service.LoginService;
import com.myproject.ikm.lib.utils.MessageUtils;
import com.myproject.ikm.trx.logic.BaseQueryLogic;

public class LoginUser implements BaseQueryLogic {

	private static final Logger LOG = LoggerFactory.getLogger(LoginUser.class);
	
	@Autowired
	private LoginService loginService;

	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {						
			LoginData loginData = mapper.readValue(data, LoginData.class);
			RespLoginVO respLoginVO = loginService.login(loginData);
			String x = mapper.writeValueAsString(respLoginVO);
			result = MessageUtils.handleSuccess(x, mapper);
		} catch (IkmEngineException e) {
			LOG.error("ParkingEngineException when processing " + pathInfo, e);
			result = MessageUtils.handleException(e, "", mapper);
		} catch (Exception e) {
			LOG.error("Unexpected exception when processing " + pathInfo, e);
			result = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), mapper);
		}
		return result;
	}

}
