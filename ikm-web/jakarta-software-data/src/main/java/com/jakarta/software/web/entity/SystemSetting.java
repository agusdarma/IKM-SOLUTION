package com.jakarta.software.web.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class SystemSetting implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String settingName;
	private String settingDesc;
	private String settingValue;
	private String valueType;
	private Date updatedOn;
	private int updatedBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSettingName() {
		return settingName;
	}
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	public String getSettingDesc() {
		return settingDesc;
	}
	public void setSettingDesc(String settingDesc) {
		this.settingDesc = settingDesc;
	}
	public String getSettingValue() {
		return settingValue;
	}
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	
}
