package com.parking.data;

public class InqLoginRequest implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private String email;
	private String password;
	private String sessionKey;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	
	
}
