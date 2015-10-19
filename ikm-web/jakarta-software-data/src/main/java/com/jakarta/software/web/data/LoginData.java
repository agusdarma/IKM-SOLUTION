package com.jakarta.software.web.data;

import java.io.Serializable;

public class LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userCode;
	private String password;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		return "[userCode=" + userCode + "," + 
			"password=***]";
	}
}
