package com.jakarta.software.web.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GroupLimitDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L; 

	private int id;
	private int groupLimitId;
	private String trxCode;
	private String trxCodeDisplay;
	private float limitPerDay;
	private float limitPerTrx;
	private float feeTrx;
	private int createdBy;
	private Date createdOn;
	private int updatedBy;
	private Date updatedOn;
	
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
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getGroupLimitId() {
		return groupLimitId;
	}

	public void setGroupLimitId(int groupLimitId) {
		this.groupLimitId = groupLimitId;
	}

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public float getLimitPerDay() {
		return limitPerDay;
	}

	public void setLimitPerDay(float limitPerDay) {
		this.limitPerDay = limitPerDay;
	}

	public float getLimitPerTrx() {
		return limitPerTrx;
	}

	public void setLimitPerTrx(float limitPerTrx) {
		this.limitPerTrx = limitPerTrx;
	}

	public float getFeeTrx() {
		return feeTrx;
	}

	public void setFeeTrx(float feeTrx) {
		this.feeTrx = feeTrx;
	}

	public String getTrxCodeDisplay() {
		return trxCodeDisplay;
	}

	public void setTrxCodeDisplay(String trxCodeDisplay) {
		this.trxCodeDisplay = trxCodeDisplay;
	}
	
}
