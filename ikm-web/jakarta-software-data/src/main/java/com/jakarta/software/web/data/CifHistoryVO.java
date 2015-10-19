package com.jakarta.software.web.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.entity.AccountHistory;
import com.jakarta.software.web.entity.Cif;

public class CifHistoryVO extends Cif {

	private static final long serialVersionUID = 1L;
	
	private int cifHistoryId;
	private int cifId;
	private int parentId;
	private String deviceCodeOld;
	private int accessIdOld;
	private String mobileStatusOld;
	private int pinCountOld; //pin status old
	private String cifGroupOld;
	private String hostCifIdOld;
	private String cifNameOld;
	private String identityCodeOld;
	private String emailOld;
	private String statusOld;
	private int useBlastSmsOld;
	private String branchIdOld;
	private String branchDisplayOld;
	private String activityType;
	private String statusDisplayOld;
	private String blastSmsOldDisplay;
	private String blastSmsDisplay;
	private List<AccountHistory> listAccountHistory;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getCifHistoryId() {
		return cifHistoryId;
	}

	public void setCifHistoryId(int cifHistoryId) {
		this.cifHistoryId = cifHistoryId;
	}

	public int getCifId() {
		return cifId;
	}

	public void setCifId(int cifId) {
		this.cifId = cifId;
	}

	public String getCifGroupOld() {
		return cifGroupOld;
	}

	public void setCifGroupOld(String cifGroupOld) {
		this.cifGroupOld = cifGroupOld;
	}

	public int getAccessIdOld() {
		return accessIdOld;
	}
	
	public void setAccessIdOld(int accessIdOld) {
		this.accessIdOld = accessIdOld;
	}
	
	public String getMobileStatusOld() {
		return mobileStatusOld;
	}
	
	public void setMobileStatusOld(String mobileStatusOld) {
		this.mobileStatusOld = mobileStatusOld;
	}
	
	public int getPinCountOld() {
		return pinCountOld;
	}
	
	public void setPinCountOld(int pinCountOld) {
		this.pinCountOld = pinCountOld;
	}
	
	public String getDeviceCodeOld() {
		return deviceCodeOld;
	}

	public void setDeviceCodeOld(String deviceCodeOld) {
		this.deviceCodeOld = deviceCodeOld;
	}

	public String getHostCifIdOld() {
		return hostCifIdOld;
	}

	public void setHostCifIdOld(String hostCifIdOld) {
		this.hostCifIdOld = hostCifIdOld;
	}

	public String getCifNameOld() {
		return cifNameOld;
	}

	public void setCifNameOld(String cifNameOld) {
		this.cifNameOld = cifNameOld;
	}

	public String getIdentityCodeOld() {
		return identityCodeOld;
	}

	public void setIdentityCodeOld(String identityCodeOld) {
		this.identityCodeOld = identityCodeOld;
	}

	public String getEmailOld() {
		return emailOld;
	}

	public void setEmailOld(String emailOld) {
		this.emailOld = emailOld;
	}

	public String getStatusOld() {
		return statusOld;
	}

	public void setStatusOld(String statusOld) {
		this.statusOld = statusOld;
	}

	public int getUseBlastSmsOld() {
		return useBlastSmsOld;
	}

	public void setUseBlastSmsOld(int useBlastSmsOld) {
		this.useBlastSmsOld = useBlastSmsOld;
	}

	public String getBranchIdOld() {
		return branchIdOld;
	}

	public void setBranchIdOld(String branchIdOld) {
		this.branchIdOld = branchIdOld;
	}

	public String getBranchDisplayOld() {
		return branchDisplayOld;
	}

	public void setBranchDisplayOld(String branchDisplayOld) {
		this.branchDisplayOld = branchDisplayOld;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public List<AccountHistory> getListAccountHistory() {
		return listAccountHistory;
	}

	public void setListAccountHistory(List<AccountHistory> listAccountHistory) {
		this.listAccountHistory = listAccountHistory;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getStatusDisplayOld() {
		return statusDisplayOld;
	}

	public void setStatusDisplayOld(String statusDisplayOld) {
		this.statusDisplayOld = statusDisplayOld;
	}

	public String getBlastSmsOldDisplay() {
		return blastSmsOldDisplay;
	}

	public void setBlastSmsOldDisplay(String blastSmsOldDisplay) {
		this.blastSmsOldDisplay = blastSmsOldDisplay;
	}

	public String getBlastSmsDisplay() {
		return blastSmsDisplay;
	}

	public void setBlastSmsDisplay(String blastSmsDisplay) {
		this.blastSmsDisplay = blastSmsDisplay;
	}
	

}
