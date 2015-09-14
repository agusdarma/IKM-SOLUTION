package com.myproject.ikm.lib.data;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RespInboxVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String fromUser;
	private String toUser;
	private String isiMessage;
	private int isRead;
	private Date createdOn;
	private Date updatedOn;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getIsiMessage() {
		return isiMessage;
	}

	public void setIsiMessage(String isiMessage) {
		this.isiMessage = isiMessage;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	

	
}
