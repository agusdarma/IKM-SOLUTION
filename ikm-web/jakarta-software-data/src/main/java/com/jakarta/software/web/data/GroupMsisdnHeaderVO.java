package com.jakarta.software.web.data; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.entity.GroupMsisdnHeader;

public class GroupMsisdnHeaderVO extends GroupMsisdnHeader {
	private static final long serialVersionUID = 1L; 

	private String createdByDisplay;
	private String updatedByDisplay;
	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
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