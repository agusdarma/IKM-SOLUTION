package com.parking.data;


public class MessageVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String messageRc;
	private String otherMessage;
	private int rc;
	public String getMessageRc() {
		return messageRc;
	}
	public void setMessageRc(String messageRc) {
		this.messageRc = messageRc;
	}
	public String getOtherMessage() {
		return otherMessage;
	}
	public void setOtherMessage(String otherMessage) {
		this.otherMessage = otherMessage;
	}
	public int getRc() {
		return rc;
	}
	public void setRc(int rc) {
		this.rc = rc;
	}
	
	
}
