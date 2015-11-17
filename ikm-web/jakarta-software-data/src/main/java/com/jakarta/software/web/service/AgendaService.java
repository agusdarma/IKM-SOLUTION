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
import com.jakarta.software.web.data.ReqAddAgendaData;
import com.jakarta.software.web.data.RespLoginVO;
import com.jakarta.software.web.utils.Constants;
import com.jakarta.software.web.utils.ConverterUtils;

@Service
public class AgendaService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AgendaService.class);
	
	@Autowired
	private HttpClientService httpClientService;
	
	
	public MessageVO addAgenda(ReqAddAgendaData reqAddAgendaData,RespLoginVO loginData) throws MmbsWebException, JsonGenerationException, JsonMappingException, IOException {		
		reqAddAgendaData.setKodeSekolah(loginData.getKodeSekolah());
		reqAddAgendaData.setNoInduk(loginData.getNoInduk());
		reqAddAgendaData.setUserType(loginData.getUserType());
		reqAddAgendaData.setPassword(loginData.getPassword());
		reqAddAgendaData.setTanggalAgenda(ConverterUtils.strToDate(reqAddAgendaData.getTanggalAgendaInput(), "dd-mm-yy"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
		reqAddAgendaData.setOriginRequest(Constants.ORIGIN_SOURCE_WEB);
		String s = mapper.writeValueAsString(reqAddAgendaData);
		String hasil = httpClientService.sendToEnginePostMethod(s,Constants.TRX_CODE_GET_ADD_AGENDA);
		MessageVO messageVO = mapper.readValue(hasil, MessageVO.class);
		LOGGER.debug("Hasil: " + messageVO);
		if(messageVO.getRc()!=0){
			throw new MmbsWebException(messageVO.getRc());
		}
		return messageVO;
	}
	
}
