package com.jakarta.software.web.data; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.entity.GroupMsisdnHeader;

public class GroupMsisdnDetailVO extends GroupMsisdnHeader {
	private static final long serialVersionUID = 1L; 

	private String arrayMsisdn;
	private int groupId;
	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getArrayMsisdn() {
		return arrayMsisdn;
	}

	public void setArrayMsisdn(String arrayMsisdn) {
		this.arrayMsisdn = arrayMsisdn;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}