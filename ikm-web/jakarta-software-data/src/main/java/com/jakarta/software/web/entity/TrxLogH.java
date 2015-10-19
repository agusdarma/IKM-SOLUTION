package com.jakarta.software.web.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TrxLogH implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;		//int auto_increment not null,
	private String phoneNo;	//varchar(30),
	private String channelType; //varchar(6),
	private String sysLogNo;	//char(14),
	private int operator; 	//int,
	private String trxCode; 	//varchar(30),
	private int hostState;	//int,
	private String hostRc; //host_rc varchar(8),
	private String systemRc; // varchar(8),
	private String systemDesc; //system_desc varchar(255),
	private int useFavourite;  //use_favorite  int,
	private Date createdOn;  //datetime,
	private Date updatedOn;  //datetime,
	private String operatorCode;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getSysLogNo() {
		return sysLogNo;
	}

	public void setSysLogNo(String sysLogNo) {
		this.sysLogNo = sysLogNo;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public int getHostState() {
		return hostState;
	}

	public void setHostState(int hostState) {
		this.hostState = hostState;
	}

	public String getHostRc() {
		return hostRc;
	}

	public void setHostRc(String hostRc) {
		this.hostRc = hostRc;
	}

	public String getSystemRc() {
		return systemRc;
	}

	public void setSystemRc(String systemRc) {
		this.systemRc = systemRc;
	}

	public String getSystemDesc() {
		return systemDesc;
	}

	public void setSystemDesc(String systemDesc) {
		this.systemDesc = systemDesc;
	}

	public int getUseFavourite() {
		return useFavourite;
	}

	public void setUseFavourite(int useFavourite) {
		this.useFavourite = useFavourite;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	
}
