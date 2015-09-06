package com.parking.data;

public class InqGetMallRequest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String sessionKey;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
