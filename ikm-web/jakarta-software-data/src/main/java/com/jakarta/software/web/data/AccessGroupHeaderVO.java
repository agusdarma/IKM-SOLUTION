package com.jakarta.software.web.data;

import com.jakarta.software.web.entity.AccessGroupHeader;

public class AccessGroupHeaderVO extends AccessGroupHeader {
	private static final long serialVersionUID = 1L;
	
	private String accessStatusDisplay;
	private String authStatusDisplay;
	private String createdByDisplay;
	private String updatedByDisplay;
	
	private String resultCode;
	private String errorMessage;
	
	public String getAccessStatusDisplay() {
		return accessStatusDisplay;
	}
	public void setAccessStatusDisplay(String accessStatusDisplay) {
		this.accessStatusDisplay = accessStatusDisplay;
	}
	public String getAuthStatusDisplay() {
		return authStatusDisplay;
	}
	public void setAuthStatusDisplay(String authStatusDisplay) {
		this.authStatusDisplay = authStatusDisplay;
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
	
}
