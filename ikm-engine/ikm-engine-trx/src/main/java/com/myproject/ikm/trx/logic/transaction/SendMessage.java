package com.myproject.ikm.trx.logic.transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.ikm.lib.data.ReqSendMessageData;
import com.myproject.ikm.lib.service.IkmEngineException;
import com.myproject.ikm.lib.service.ListInboxService;
import com.myproject.ikm.lib.utils.MessageUtils;
import com.myproject.ikm.trx.logic.BaseQueryLogic;

public class SendMessage implements BaseQueryLogic {

	private static final Logger LOG = LoggerFactory.getLogger(SendMessage.class);
	
	@Autowired
	private ListInboxService listInboxService;

	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {						
			ReqSendMessageData reqSendMessageData = mapper.readValue(data, ReqSendMessageData.class);
			listInboxService.sendMessage(reqSendMessageData);			
			result = MessageUtils.handleSuccess("", mapper);
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
