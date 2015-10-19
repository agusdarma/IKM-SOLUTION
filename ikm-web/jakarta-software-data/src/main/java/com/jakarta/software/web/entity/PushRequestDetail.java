package com.jakarta.software.web.entity; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class PushRequestDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L; 

	private int id; 
	private int headerId; 
	private int status; 
	private String msisdn; 
	private int groupMsisdnId; 

	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getId(){
		return id; 
	} 

	public void setId(int id){
		this.id = id; 
	}

	public int getHeaderId(){
		return headerId; 
	} 

	public void setHeaderId(int headerId){
		this.headerId = headerId; 
	}

	public int getStatus(){
		return status; 
	} 

	public void setStatus(int status){
		this.status = status; 
	}

	public String getMsisdn(){
		return msisdn; 
	} 

	public void setMsisdn(String msisdn){
		this.msisdn = msisdn; 
	}

	public int getGroupMsisdnId() {
		return groupMsisdnId;
	}

	public void setGroupMsisdnId(int groupMsisdnId) {
		this.groupMsisdnId = groupMsisdnId;
	}

}