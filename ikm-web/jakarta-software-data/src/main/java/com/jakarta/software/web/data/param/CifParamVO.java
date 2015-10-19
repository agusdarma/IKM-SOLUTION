package com.jakarta.software.web.data.param;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CifParamVO extends ParamPagingVO {

	private int id;
	private String mobilePhone;
	private String name;
	private String email;
	private String group;
	private Date createdOnStart;
	private Date createdOnEnd;
	private Date createdOn;
	private int createdBy;
	private Date updatedOn;
	private int updatedBy;
	private String status;
	private String authStatus;
	private int flagParam; // can be used for anything
	private int retailerId; 
	private String accountNo;
	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	@Override
	protected String getPrimaryKey() {
		return "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public Date getCreatedOnStart() {
		return createdOnStart;
	}

	public void setCreatedOnStart(Date createdOnStart) {
		this.createdOnStart = createdOnStart;
	}

	public Date getCreatedOnEnd() {
		return createdOnEnd;
	}

	public void setCreatedOnEnd(Date createdOnEnd) {
		this.createdOnEnd = createdOnEnd;
	}

	public int getFlagParam() {
		return flagParam;
	}

	public void setFlagParam(int flagParam) {
		this.flagParam = flagParam;
	}

	public int getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(int retailerId) {
		this.retailerId = retailerId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
}
