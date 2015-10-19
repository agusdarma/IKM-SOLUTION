package com.jakarta.software.web.entity; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GroupMsisdnDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L; 

	private String msisdn; 
	private int groupId; 

	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getMsisdn(){
		return msisdn; 
	} 

	public void setMsisdn(String msisdn){
		this.msisdn = msisdn; 
	}

	public int getGroupId(){
		return groupId; 
	} 

	public void setGroupId(int groupId){
		this.groupId = groupId; 
	}

}