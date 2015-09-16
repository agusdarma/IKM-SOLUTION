package com.myproject.ikm.trx.logic.transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.ikm.lib.data.ReqListKelasData;
import com.myproject.ikm.lib.data.RespListKelasVO;
import com.myproject.ikm.lib.service.IkmEngineException;
import com.myproject.ikm.lib.service.ListKelasService;
import com.myproject.ikm.lib.utils.MessageUtils;
import com.myproject.ikm.trx.logic.BaseQueryLogic;

public class ListKelas implements BaseQueryLogic {

	private static final Logger LOG = LoggerFactory.getLogger(ListKelas.class);
	
	@Autowired
	private ListKelasService listKelasService;

	@Override
	public String process(HttpServletRequest request,HttpServletResponse response,String data, ObjectMapper mapper, String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {						
			ReqListKelasData reqListKelasData = mapper.readValue(data, ReqListKelasData.class);
			RespListKelasVO respListKelasVO = listKelasService.findAllKelasTeacher(reqListKelasData);
			String x = mapper.writeValueAsString(respListKelasVO);
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
