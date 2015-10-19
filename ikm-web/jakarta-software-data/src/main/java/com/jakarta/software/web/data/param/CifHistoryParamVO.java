package com.jakarta.software.web.data.param;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CifHistoryParamVO extends ParamPagingVO {

	private static final long serialVersionUID = 1L;

	private int id;
	private int cifId;
	private String deviceCode;
	private int accessId;
	private String accessName;
	private String hostCifId;
	private String cifName;
	private String identityCode;
	private String cifLang;
	private String cifGroup;
	private String pin;
	private int pinCount;
	private int hasstk;
	private Date activationDate;
	private Date pin_changeDate;
	private String status;
	private String authStatus;
	private String remarks;
	private Date createdOn;
	private int createdBy;
	private Date authOn;
	private int authBy;
	private Date updatedOn;
	private int updatedBy;
	private String email;
	private String blastSmsTime;
	private int blastSms;
	private int branchId;
	private String activityType;

	@Override
	protected String getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
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

	public int getAccessId() {
		return accessId;
	}

	public void setAccessId(int accessId) {
		this.accessId = accessId;
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

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
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

	public Date getPin_changeDate() {
		return pin_changeDate;
	}

	public void setPin_changeDate(Date pin_changeDate) {
		this.pin_changeDate = pin_changeDate;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBlastSmsTime() {
		return blastSmsTime;
	}

	public void setBlastSmsTime(String blastSmsTime) {
		this.blastSmsTime = blastSmsTime;
	}

	public int getBlastSms() {
		return blastSms;
	}

	public void setBlastSms(int blastSms) {
		this.blastSms = blastSms;
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

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getAccessName() {
		return accessName;
	}

	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}

}
