package com.ikm.data;

import java.util.Date;

public class InboxVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fromName;
	private String toName;
	private String isiMessage;
	private int isRead;
	private Date createdOn;
	private String createdOnVal;
	private boolean self;

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
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

	public boolean isSelf() {
		return self;
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public String getCreatedOnVal() {
		return createdOnVal;
	}

	public void setCreatedOnVal(String createdOnVal) {
		this.createdOnVal = createdOnVal;
	}

	
}
