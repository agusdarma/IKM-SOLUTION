package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.param.SystemSettingParamVO;
import com.jakarta.software.web.entity.SystemSetting;

public interface SystemSettingMapper {
	
	public List<SystemSetting> findSystemSettingAll();
	
	public void updateSystemSetting(SystemSetting systemSetting);
	
	public SystemSetting findSystemSettingById(int settingId);
	
	public List<SystemSetting> findSystemSettingByParam(SystemSettingParamVO systemParamVO);
	
	public int countSystemSettingByParam(SystemSettingParamVO systemParamVO);
		
	public SystemSetting findSystemSettingBySettingName(String settingName);
}
