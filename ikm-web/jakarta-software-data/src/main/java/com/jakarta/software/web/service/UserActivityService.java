package com.jakarta.software.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.local.common.exception.DifferenceException;
import ch.local.common.util.DifferenceFinder;

import com.jakarta.software.web.data.MapVO;
import com.jakarta.software.web.data.UserActivityVO;
import com.jakarta.software.web.data.param.UserActivityParamVO;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.entity.UserData;
import com.jakarta.software.web.mapper.UserActivityMapper;

@Service
public class UserActivityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserActivityService.class);
		
	@Autowired
	private UserActivityMapper userActivityMapper;
	
	private ObjectMapper mapper;
	
	@Transactional
	public void createHistoryActivity(UserActivity userActivity)
	{
		Date now = new Date();
		userActivity.setUpdatedOn(now);
		LOGGER.info("Insert HistoryActivity : " + userActivity.toString());
		userActivityMapper.createUserActivity(userActivity);
	}

	public List<UserActivity> selectUserActivityByParam(UserActivityParamVO paramVO)
	{
		List<UserActivity> listUserActivity = userActivityMapper.selectUserActivityByParam(paramVO);
		if(listUserActivity == null)
		{
			listUserActivity = new ArrayList<UserActivity>();
		}
		return listUserActivity;
	}
	
	public List<UserActivity> selectUserActivityByParamNoPaging(UserActivityParamVO paramVO)
	{
		List<UserActivity> listUserActivity = userActivityMapper.selectUserActivityByParamNoPaging(paramVO);
		if(listUserActivity == null)
		{
			listUserActivity = new ArrayList<UserActivity>();
		}
		return listUserActivity;
	}
	
	public int countUserActivityByParam(UserActivityParamVO paramVO)
	{
		int countUserActivity = userActivityMapper.countUserActivityByParam(paramVO);
		return countUserActivity;
	}
	
	public void generateHistoryActivity(Collection<String> excludes,Object ori,Object current,
			int userId,String moduleName,String action,String targetTable,int targetId)
	{
		/*
		 * added history activity
		 * */
		try{
			HashMap<String, Collection<String>> resultCompare =  DifferenceFinder.getDifferences(ori,current,excludes);
			LOGGER.debug("resultCompare: " + resultCompare.toString());
			Iterator<String> listField = resultCompare.keySet().iterator();
			List<UserActivityVO> listChangedValue = new ArrayList<UserActivityVO>();
			UserActivity userActivity = new UserActivity();
			userActivity.setAction(action);
			userActivity.setTargetId(targetId);
			userActivity.setTargetTable(targetTable);
			userActivity.setModuleName(moduleName);
			userActivity.setUserDataId(userId);
			
			while (listField.hasNext()) {
				String field = listField.next();
				Iterator<String> listValue = resultCompare.get(field).iterator();
				while (listValue.hasNext()) {
					String value = listValue.next();
					String[] splitValue = value.split("(;|,)");
					if(splitValue.length == 2){
						LOGGER.debug("Field Name : " + field + " Original Value : " + splitValue[0]+ " New Value: " + splitValue[1]);
						try {
							UserActivityVO userActivityVO = new UserActivityVO();
							HashMap<String, String> newHash= new HashMap<String, String>();
							if(splitValue[0].contains("="))
							{
								splitValue[0]=splitValue[0].replaceAll("=", ",");
							}
							if(splitValue[1].contains("="))
							{
								splitValue[1]=splitValue[1].replaceAll("=", ",");
							}
							newHash.put(splitValue[0], splitValue[1]);
							userActivityVO.setField(field);
							userActivityVO.setOldValue(splitValue[0]);
							userActivityVO.setNewValue(splitValue[1]);
							listChangedValue.add(userActivityVO);							
						} catch (Exception e) {
							LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
						}
					}
				}// end loop cek detail yang berubah
			}// end loop cek field apa yang berubah
			
			mapper = new ObjectMapper();
			// faster this way, not default
//			mapper.configure(SerializationConfig.Feature.USE_STATIC_TYPING, true); 
			try {
				String detailInfo = mapper.writeValueAsString(listChangedValue);
				userActivity.setChangedAttribute(detailInfo);
				createHistoryActivity(userActivity);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (DifferenceException e) {
			LOGGER.warn("DifferenceException " + e.toString());
		}		
	}
	
	public List<MapVO> getViewActivityAction()
	{
		List<MapVO> listAction=userActivityMapper.getViewActivityAction();
		return listAction;
	}
	
	public List<MapVO> getViewActivityModuleName()
	{
		List<MapVO> listModuleName=userActivityMapper.getViewActivityModuleName();
		return listModuleName;
	}
	
	public List<MapVO> getViewActivityTargetTable()
	{
		List<MapVO> listTargetTable=userActivityMapper.getViewActivityTargetTable();
		return listTargetTable;
	}
	
	public List<UserData> getViewActivityUser()
	{
		List<UserData> listUser=userActivityMapper.getViewActivityUser();
		return listUser;
	}
	
	public UserActivity selectUserActivityById(int id)
	{
		UserActivity ua = userActivityMapper.selectUserActivityById(id);
		return ua;
	}
	
	public List<UserActivityVO> convertStringToUserActivityVO(String changedAttribute)
	{
		List<UserActivityVO> listUserActivityVO = new ArrayList<UserActivityVO>();
		try {
			listUserActivityVO = mapper.readValue(changedAttribute, new TypeReference<List<UserActivityVO>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listUserActivityVO;
	}
}