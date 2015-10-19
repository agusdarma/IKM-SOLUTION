package com.jakarta.software.web.data.param; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class PushRequestParamVO extends ParamPagingVO {

	private int id; 
	private Date sentTime; 
	private int status;
	private int groupMsisdnId;
	private String message; 
	private Date createdOn; 
	private int createdBy; 
	private Date updatedOn; 
	private int updatedBy; 

	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	@Override
	protected String getPrimaryKey() {
		return "";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getSentTime() {
		return sentTime;
	}
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public int getGroupMsisdnId() {
		return groupMsisdnId;
	}
	public void setGroupMsisdnId(int groupMsisdnId) {
		this.groupMsisdnId = groupMsisdnId;
	}

}