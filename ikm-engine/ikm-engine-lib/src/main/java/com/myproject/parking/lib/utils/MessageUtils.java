package com.myproject.parking.lib.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.myproject.parking.lib.data.MessageVO;
import com.myproject.parking.lib.service.ParkingEngineException;

public class MessageUtils {
	private static final Logger LOG = LoggerFactory.getLogger(MessageUtils.class);
	private static String generateMessage(MessageVO messageVO,ObjectMapper mapper){
		String result = "";
		try {
			result = mapper.writeValueAsString(messageVO);
			result = CipherUtil.encryptTripleDES(result, CipherUtil.PASSWORD);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String handleException(Exception e,String otherMessage,ObjectMapper mapper) {
		MessageVO messageVO = new MessageVO();
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			if (e instanceof ParkingEngineException) {
				ParkingEngineException jme = (ParkingEngineException) e;
				if(jme.hasInfo()){
					messageVO.setRc(jme.getErrorCode());
					messageVO.setMessageRc(props.getProperty("rc."+jme.getErrorCode())+ " " + Arrays.toString(jme.getInfo()));
					messageVO.setOtherMessage(otherMessage);
					LOG.warn("MessageVO : [{}]", messageVO);
				}else{
					messageVO.setRc(jme.getErrorCode());
					messageVO.setMessageRc(props.getProperty("rc."+jme.getErrorCode()));
					messageVO.setOtherMessage(otherMessage);
					LOG.warn("MessageVO : [{}]", messageVO);
				}
			} else {
				messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
				messageVO.setMessageRc(props.getProperty("rc."+ParkingEngineException.ENGINE_UNKNOWN_ERROR));
				messageVO.setOtherMessage(e.getMessage());
				LOG.warn("MessageVO : [{}]", messageVO);
			}
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
			messageVO.setOtherMessage(ew.getMessage());
		}
		return generateMessage(messageVO, mapper);
	}
	
	public static String handleExceptionOther(Exception e,String otherMessage,ObjectMapper mapper) {
		MessageVO messageVO = new MessageVO();
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			if (e instanceof ParkingEngineException) {
				ParkingEngineException jme = (ParkingEngineException) e;
				messageVO.setRc(jme.getErrorCode());
				messageVO.setMessageRc(props.getProperty("rc."+jme.getErrorCode()));
				messageVO.setOtherMessage(otherMessage);
				LOG.warn("MessageVO : [{}]", messageVO);
			} else {
				messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
				messageVO.setMessageRc(props.getProperty("rc."+ParkingEngineException.ENGINE_UNKNOWN_ERROR));
				messageVO.setOtherMessage(e.getMessage());
				LOG.warn("MessageVO : [{}]", messageVO);
			}
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
			messageVO.setOtherMessage(ew.getMessage());
		}
		return messageVO.getMessageRc();
	}
	
	public static String handleSuccess(String otherMessage,ObjectMapper mapper) {
		MessageVO messageVO = new MessageVO();
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
				messageVO.setRc(0);
				messageVO.setMessageRc(props.getProperty("rc.0"));
				messageVO.setOtherMessage(otherMessage);
				LOG.warn("MessageVO : [{}]", messageVO);
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
			messageVO.setOtherMessage(ew.getMessage());
		}
		return generateMessage(messageVO, mapper);
	}

}
