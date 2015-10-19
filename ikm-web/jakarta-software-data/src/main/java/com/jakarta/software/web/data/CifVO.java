package com.jakarta.software.web.data;

import com.jakarta.software.web.entity.Cif;

public class CifVO extends Cif{
	private static final long serialVersionUID = 1L;
	
	private String mobileStatusDisplay;
	private String pinStatusDisplay;
	private String authStatusDisplay;
	private String cifGroupDisplay;
	private String createdByDisplay;
	private String updatedByDisplay;
	
	private String resultCode;
	private String errorMessage;
	
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
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getCifGroupDisplay() {
		return cifGroupDisplay;
	}
	public void setCifGroupDisplay(String cifGroupDisplay) {
		this.cifGroupDisplay = cifGroupDisplay;
	}
	public String getCreatedByDisplay() {
		return createdByDisplay;
	}
	public void setCreatedByDisplay(String createdByDisplay) {
		this.createdByDisplay = createdByDisplay;
	}
	public String getUpdatedByDisplay() {
		return updatedByDisplay;
	}
	public void setUpdatedByDisplay(String updatedByDisplay) {
		this.updatedByDisplay = updatedByDisplay;
	}
	
	
}
