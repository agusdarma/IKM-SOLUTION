package com.jakarta.software.web.data;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TrxLogBrowserDetailVO {
	
	private String sysLogNo;
	private Date receivedTime;
	private String msgLogNo;
	private String custIn;
	private String custOut;
	private int lastState;
	private String lastRc;
	private String messageFromUser;
	private String messageForUser;
	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	public String getSysLogNo() {
		return sysLogNo;
	}
	public void setSysLogNo(String sysLogNo) {
		this.sysLogNo = sysLogNo;
	}
	public Date getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}
	public String getMsgLogNo() {
		return msgLogNo;
	}
	public void setMsgLogNo(String msgLogNo) {
		this.msgLogNo = msgLogNo;
	}
	public String getCustIn() {
		return custIn;
	}
	public void setCustIn(String custIn) {
		this.custIn = custIn;
	}
	public String getCustOut() {
		return custOut;
	}
	public void setCustOut(String custOut) {
		this.custOut = custOut;
	}
	public int getLastState() {
		return lastState;
	}
	public void setLastState(int lastState) {
		this.lastState = lastState;
	}
	public String getLastRc() {
		return lastRc;
	}
	public void setLastRc(String lastRc) {
		this.lastRc = lastRc;
	}
	public String getMessageFromUser() {
		return messageFromUser;
	}
	public void setMessageFromUser(String messageFromUser) {
		this.messageFromUser = messageFromUser;
	}
	public String getMessageForUser() {
		return messageForUser;
	}
	public void setMessageForUser(String messageForUser) {
		this.messageForUser = messageForUser;
	}
	
}
