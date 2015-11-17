package com.jakarta.software.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jakarta.software.web.data.MessageVO;
import com.jakarta.software.web.data.ReqListKelasData;
import com.jakarta.software.web.data.RespListKelasVO;
import com.jakarta.software.web.data.RespLoginVO;
import com.jakarta.software.web.entity.Kelas;
import com.jakarta.software.web.utils.Constants;

@Service
public class KelasService {
	private static final Logger LOG = LoggerFactory.getLogger(KelasService.class);
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private HttpClientService httpClientService;
	
	public RespListKelasVO findAllKelasTeacherEngine(RespLoginVO loginData) throws MmbsWebException, JsonGenerationException, JsonMappingException, IOException {
		ReqListKelasData reqListKelasData = new ReqListKelasData();
		reqListKelasData.setKodeSekolah(loginData.getKodeSekolah());
		reqListKelasData.setNoInduk(loginData.getNoInduk());
		reqListKelasData.setUserType(loginData.getUserType());
		reqListKelasData.setPassword(loginData.getPassword());
		LOG.debug("findAllKelasTeacherEngine with param : " + " reqListKelasData: " + reqListKelasData);	
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
		reqListKelasData.setOriginRequest(Constants.ORIGIN_SOURCE_WEB);
		String s = mapper.writeValueAsString(reqListKelasData);
		String hasil = httpClientService.sendToEnginePostMethod(s,Constants.TRX_CODE_GET_LIST_KELAS);
		MessageVO messageVO = mapper.readValue(hasil, MessageVO.class);
		LOG.debug("Hasil: " + messageVO);
		if(messageVO.getRc()!=0){
			throw new MmbsWebException(messageVO.getRc());
		}
		RespListKelasVO respListKelasVO = new RespListKelasVO();
		respListKelasVO = mapper.readValue(messageVO.getOtherMessage(), RespListKelasVO.class);
		LOG.info("findAllKelasTeacherEngine done with param : " + " respListKelasVO: " + respListKelasVO);
		return respListKelasVO;		
	}
	
}
