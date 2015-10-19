package com.jakarta.software.web.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CifHistory {
	private int id;
	private int cifId;
	private int parentId; //parent id isinya id cif history sebelum ini
	private int retailerId;
	private int accessId;
	private int pinCount;
	private int hasstk;
	private String deviceCode;

	private String accessType;
	private String name;
	private String identityCode;
	private String defAccountNo;
	private String language;
	private String group;
	private String email;
	private String pin;

	private String status;
	private String authStatus;
	private String remarks;
	private int useBlastSms;
	private Date createdOn;
	private int createdBy;
	private Date updatedOn;
	private int updatedBy;
	private Date authOn;
	private int authBy;
	private int branchId;
	private String activityType;
	private String needChangePin;
	private Date birthDate;
	private String motherName;
	private String address;
		
	private String hostCifId;
	private String cifName;
	private String cifLang;
	private String cifGroup;
	
	private Date activationDate;
	private Date pinChangeDate;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCifId() {
		return cifId;
	}
	public void setCifId(int cifId) {
		this.cifId = cifId;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public int getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;
	}

	public int getAccessId() {
		return accessId;
	}

	public void setAccessId(int accessId) {
		this.accessId = accessId;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getDefAccountNo() {
		return defAccountNo;
	}

	public void setDefAccountNo(String defAccountNo) {
		this.defAccountNo = defAccountNo;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public int getPinCount() {
		return pinCount;
	}

	public void setPinCount(int pinCount) {
		this.pinCount = pinCount;
	}

	public int getHasstk() {
		return hasstk;
	}

	public void setHasstk(int hasstk) {
		this.hasstk = hasstk;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getPinChangeDate() {
		return pinChangeDate;
	}

	public void setPinChangeDate(Date pinChangeDate) {
		this.pinChangeDate = pinChangeDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public int getUseBlastSms() {
		return useBlastSms;
	}

	public void setUseBlastSms(int useBlastSms) {
		this.useBlastSms = useBlastSms;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
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

	public Date getAuthOn() {
		return authOn;
	}

	public void setAuthOn(Date authOn) {
		this.authOn = authOn;
	}

	public int getAuthBy() {
		return authBy;
	}

	public void setAuthBy(int authBy) {
		this.authBy = authBy;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getHostCifId() {
		return hostCifId;
	}

	public void setHostCifId(String hostCifId) {
		this.hostCifId = hostCifId;
	}

	public String getCifName() {
		return cifName;
	}

	public void setCifName(String cifName) {
		this.cifName = cifName;
	}

	public String getCifLang() {
		return cifLang;
	}

	public void setCifLang(String cifLang) {
		this.cifLang = cifLang;
	}

	public String getCifGroup() {
		return cifGroup;
	}

	public void setCifGroup(String cifGroup) {
		this.cifGroup = cifGroup;
	}

	public String getNeedChangePin() {
		return needChangePin;
	}

	public void setNeedChangePin(String needChangePin) {
		this.needChangePin = needChangePin;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	
}
