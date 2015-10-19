package com.jakarta.software.web.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Cif implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String deviceCode;
	private String hostCifId;
	private int retailerId;
	private int accessId;
	private String cifName;
	private String identityCode;
	private String language;
	private String group;
	private String email;
	private String pin;
	private int pinCount;
	private int hasstk;
	private Date activationDate;
	private Date pinChangeDate;
	private String status;
	private String authStatus;
	private String remarks;
	private Date createdOn;
	private int createdBy;
	private Date authOn;
	private int authBy;
	private Date updatedOn;
	private int updatedBy;
	private boolean featureBlastSMS;
	private int useBlastSms;
	private String branchId;
	private String branchDisplay;
	private String needChangePin;
	private String address;
	private Date birthDate;
	private String motherName;
	private String statusDisplay;
	
	/* additional data */
	private List<Account> listAccount;
	private int accountOption;
	private String cardNo;
	private String accountNo;
	private String accountType;
	private String confirmPin;
	private String mobileStatusDisplay;
	private String pinStatusDisplay;
	private String authStatusDisplay;
	private Date blastTimeDisplay;
	private String groupDisplay;
	
	@Override
	public String toString() {
		List<String> listExclude = new ArrayList<String>();
		listExclude.add("pin");
		listExclude.add("confirmPin");
		return ReflectionToStringBuilder.toStringExclude(this, listExclude);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<Account> getListAccount() {
		if (listAccount == null)
			listAccount = new ArrayList<Account>();
		return listAccount;
	}

	public void setListAccount(List<Account> listAccount) {
		this.listAccount = listAccount;
	}

	public int getAccountOption() {
		return accountOption;
	}

	public void setAccountOption(int accountOption) {
		this.accountOption = accountOption;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getConfirmPin() {
		return confirmPin;
	}

	public void setConfirmPin(String confirmPin) {
		this.confirmPin = confirmPin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isFeatureBlastSMS() {
		return featureBlastSMS;
	}

	public void setFeatureBlastSMS(boolean featureBlastSMS) {
		this.featureBlastSMS = featureBlastSMS;
	}

	public String getMobileStatusDisplay() {
		return mobileStatusDisplay;
	}

	public void setMobileStatusDisplay(String mobileStatusDisplay) {
		this.mobileStatusDisplay = mobileStatusDisplay;
	}

	public String getPinStatusDisplay() {
		return pinStatusDisplay;
	}

	public void setPinStatusDisplay(String pinStatusDisplay) {
		this.pinStatusDisplay = pinStatusDisplay;
	}

	public String getAuthStatusDisplay() {
		return authStatusDisplay;
	}

	public void setAuthStatusDisplay(String authStatusDisplay) {
		this.authStatusDisplay = authStatusDisplay;
	}

	public Date getBlastTimeDisplay() {
		return blastTimeDisplay;
	}

	public void setBlastTimeDisplay(Date blastTimeDisplay) {
		this.blastTimeDisplay = blastTimeDisplay;
	}
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchDisplay() {
		return branchDisplay;
	}

	public void setBranchDisplay(String branchDisplay) {
		this.branchDisplay = branchDisplay;
	}

	public int getUseBlastSms() {
		return useBlastSms;
	}

	public void setUseBlastSms(int useBlastSms) {
		this.useBlastSms = useBlastSms;
	}

	public String getNeedChangePin() {
		return needChangePin;
	}

	public void setNeedChangePin(String needChangePin) {
		this.needChangePin = needChangePin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getGroupDisplay() {
		return groupDisplay;
	}

	public void setGroupDisplay(String groupDisplay) {
		this.groupDisplay = groupDisplay;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

}
