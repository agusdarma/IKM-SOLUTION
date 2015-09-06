package com.parking.entity;

import java.util.Date;


public class UserData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String password;
	private String phoneNo;
	private String email;
	private String licenseNo;
	private String activateKey;
	private String sessionKey;
	private int status;
	private Date timeGenSessionKey;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getTimeGenSessionKey() {
		return timeGenSessionKey;
	}
	public void setTimeGenSessionKey(Date timeGenSessionKey) {
		this.timeGenSessionKey = timeGenSessionKey;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getActivateKey() {
		return activateKey;
	}

	public void setActivateKey(String activateKey) {
		this.activateKey = activateKey;
	}
}
