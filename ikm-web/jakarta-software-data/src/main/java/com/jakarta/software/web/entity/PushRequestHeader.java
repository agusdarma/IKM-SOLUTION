package com.jakarta.software.web.entity; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class PushRequestHeader implements java.io.Serializable {
	private static final long serialVersionUID = 1L; 

	private int id; 
	private Date sentTime; 
	private int groupMsisdnId;
	private int status; 
	private String message; 
	private Date createdOn; 
	private int createdBy; 
	private Date updatedOn; 
	private int updatedBy; 
	private String subject;
	
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

	public Date getSentTime(){
		return sentTime; 
	} 

	public void setSentTime(Date sentTime){
		this.sentTime = sentTime; 
	}

	public int getStatus(){
		return status; 
	} 

	public void setStatus(int status){
		this.status = status; 
	}

	public String getMessage(){
		return message; 
	} 

	public void setMessage(String message){
		this.message = message; 
	}

	public Date getCreatedOn(){
		return createdOn; 
	} 

	public void setCreatedOn(Date createdOn){
		this.createdOn = createdOn; 
	}

	public int getCreatedBy(){
		return createdBy; 
	} 

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy; 
	}

	public Date getUpdatedOn(){
		return updatedOn; 
	} 

	public void setUpdatedOn(Date updatedOn){
		this.updatedOn = updatedOn; 
	}

	public int getUpdatedBy(){
		return updatedBy; 
	} 

	public void setUpdatedBy(int updatedBy){
		this.updatedBy = updatedBy; 
	}

	public int getGroupMsisdnId() {
		return groupMsisdnId;
	}

	public void setGroupMsisdnId(int groupMsisdnId) {
		this.groupMsisdnId = groupMsisdnId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	
}