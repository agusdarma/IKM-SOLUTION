package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.MapVO;
import com.jakarta.software.web.data.param.UserActivityParamVO;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.entity.UserData;

public interface UserActivityMapper {
	
	public void createUserActivity(UserActivity historyActivity);

	public List<UserActivity> selectUserActivityByParam(UserActivityParamVO paramVO);
	
	public int countUserActivityByParam(UserActivityParamVO paramVO);
	
	public UserActivity selectUserActivityById(int id);
	
	public List<MapVO> getViewActivityAction();
	
	public List<MapVO> getViewActivityModuleName();
	
	public List<MapVO> getViewActivityTargetTable();
	
	public List<UserData> getViewActivityUser();
	
	public List<UserActivity> selectUserActivityByParamNoPaging(UserActivityParamVO paramVO);
	
}
