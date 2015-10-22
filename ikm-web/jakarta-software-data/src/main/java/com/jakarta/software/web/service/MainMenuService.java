package com.jakarta.software.web.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jakarta.software.web.data.MessageVO;
import com.jakarta.software.web.data.ReqListAgendaData;
import com.jakarta.software.web.data.RespListAgendaVO;
import com.jakarta.software.web.utils.Constants;

@Service
public class MainMenuService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuService.class);
	
	@Autowired
	private HttpClientService httpClientService;
	
	
	public RespListAgendaVO getListAgenda(ReqListAgendaData reqListAgendaData) throws MmbsWebException, JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
		reqListAgendaData.setOriginRequest(Constants.ORIGIN_SOURCE_WEB);
		String s = mapper.writeValueAsString(reqListAgendaData);
		String hasil = httpClientService.sendToEnginePostMethod(s,Constants.TRX_CODE_GET_LIST_AGENDA);
		MessageVO messageVO = mapper.readValue(hasil, MessageVO.class);
		LOGGER.debug("Hasil: " + messageVO);
		if(messageVO.getRc()!=0){
			throw new MmbsWebException(messageVO.getRc());
		}
		RespListAgendaVO respListAgendaVO = mapper.readValue(messageVO.getOtherMessage(), RespListAgendaVO.class);
		return respListAgendaVO;
	}
	
}
