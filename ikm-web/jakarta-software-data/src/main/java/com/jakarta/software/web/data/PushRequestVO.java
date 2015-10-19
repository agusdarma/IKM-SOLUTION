package com.jakarta.software.web.data; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.entity.PushRequestHeader;

public class PushRequestVO extends PushRequestHeader {
	private static final long serialVersionUID = 1L; 

	private String msisdn;
	private String statusDisplay;
	private String groupName;
	private String createdByDisplay;
	private String updatedByDisplay;
	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

}