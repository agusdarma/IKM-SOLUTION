package com.jakarta.software.web.data;

import java.io.Serializable;

public class CifEngineRequestVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String trxCode;
	private int state;
	private int cifHistoryId;
	private int userId;
	private String deviceCode;
	
	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCifHistoryId() {
		return cifHistoryId;
	}

	public void setCifHistoryId(int cifHistoryId) {
		this.cifHistoryId = cifHistoryId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

}
