package com.myproject.parking.lib.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.parking.lib.data.LoginData;
import com.myproject.parking.lib.entity.Mall;
import com.myproject.parking.lib.entity.MallSlotAvailable;
import com.myproject.parking.lib.entity.UserData;
import com.myproject.parking.lib.mapper.MallMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.Constants;

@Service
public class MallService {
	private static final Logger LOG = LoggerFactory.getLogger(MallService.class);
	
	@Autowired
	private MallMapper mallMapper;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private CheckSessionKeyService checkSessionKeyService;
	
	private HashMap<String, String> listMallCache;
	
	public List<Mall> findAllMall(LoginData loginData,ObjectMapper mapper) throws ParkingEngineException {
		LOG.info("process find All Mall ");	
		UserData user = userDataMapper.findUserDataByEmail(loginData.getEmail());
		if(user == null){
			LOG.error("Can't find User with email : " + loginData.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_FOUND);
		}
		if(Constants.BLOCKED == user.getStatus()){
			LOG.error("User already blocked");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatus()){
			LOG.error("User not active");
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_ACTIVE);
		}		
		if(StringUtils.isEmpty(user.getSessionKey())){
			LOG.error("User Must Login Before make transaction, Parameter email : " + loginData.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_LOGIN);
		}
		if(!user.getSessionKey().equals(loginData.getSessionKey())){
			LOG.error("Wrong Session Key, Parameter email : " + loginData.getEmail());
			throw new ParkingEngineException(ParkingEngineException.ENGINE_SESSION_KEY_DIFFERENT);
		}		
		checkSessionKeyService.checkSessionKey(user.getTimeGenSessionKey(), loginData.getEmail());
		List<Mall> listMall = null;
		if(listMallCache == null){
			listMallCache = new HashMap<String, String>();
		}
		LOG.info("User branch mall :  " + user.getBranchMall());	
		if(isValidGetFromCache(mapper,user)){
			listMall = getFromMallCache(mapper,user);
			setSlotAvailable(listMall);
		}else{		
			
			if(Constants.BRANCH_MALL_ALL.equalsIgnoreCase(user.getBranchMall())){
				listMall = mallMapper.findAllMall();
			}else{
				listMall = mallMapper.findMallByName(user.getBranchMall());
			}				
			setSlotAvailable(listMall);
			LOG.debug("Mall get from db List Mall : " + listMall.size());
			String listMallCacheJson = "";
			try {
				listMallCacheJson = mapper.writeValueAsString(listMall);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(Constants.BRANCH_MALL_ALL.equalsIgnoreCase(user.getBranchMall())){
				listMallCache.put(Constants.CACHE_MALL, listMallCacheJson);
			}else{
				listMallCache.put(Constants.CACHE_MALL+user.getBranchMall(), listMallCacheJson);
			}
			
		}
		
		
		LOG.info("process find All Mall Done");
		return listMall;
	}
	
	public void setSlotAvailable(List<Mall> listMall){
		List<MallSlotAvailable> listMallSlotAvailable = mallMapper.findSlotAvailablePerMall();			
		for (int i = 0; i < listMall.size(); i++) {
			listMall.get(i).setSlotAvailable(0);
			for (int j = 0; j < listMallSlotAvailable.size(); j++) {
				if(listMall.get(i).getId() == listMallSlotAvailable.get(j).getId()){
					listMall.get(i).setSlotAvailable(listMallSlotAvailable.get(j).getSlotAvailable());
					break;
				}
			}
		}
	}
	
	public void refreshCacheMall() throws ParkingEngineException {
		listMallCache = null;
	}
	
	private boolean isValidGetFromCache(ObjectMapper mapper,UserData user){
		boolean hasil = false;
		String listMallCacheJson = null;
		if(Constants.BRANCH_MALL_ALL.equalsIgnoreCase(user.getBranchMall())){
			listMallCacheJson = listMallCache.get(Constants.CACHE_MALL);
		}else{
			listMallCacheJson = listMallCache.get(Constants.CACHE_MALL+user.getBranchMall());
		}
		
		if(!StringUtils.isEmpty(listMallCacheJson)){
			hasil = true;
		}
		return hasil;
	}
	
	private List<Mall> getFromMallCache(ObjectMapper mapper,UserData user){
		List<Mall> listMall = new ArrayList<Mall>();
		try {
			String listMallCacheJson = null;
			if(Constants.BRANCH_MALL_ALL.equalsIgnoreCase(user.getBranchMall())){
				listMallCacheJson = listMallCache.get(Constants.CACHE_MALL);
			}else{
				listMallCacheJson = listMallCache.get(Constants.CACHE_MALL+user.getBranchMall());
			}			
			listMall = mapper.readValue(listMallCacheJson, new TypeReference<List<Mall>>(){});
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.debug("Mall get from cache List Mall : " + listMall.size());
		return listMall;
	}
}
