package com.parking.data;

public class LoginData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String password;
	private String sessionKey;
	private String phoneNo;
	private String groupUser;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getGroupUser() {
		return groupUser;
	}
	public void setGroupUser(String groupUser) {
		this.groupUser = groupUser;
	}
}
