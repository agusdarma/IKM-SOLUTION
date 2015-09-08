package com.myproject.parking.lib.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MallSlots implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int mallId;
	private String slotsName;
	private Long slotsPrice;
	private int slotsStatus;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;

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

	public int getMallId() {
		return mallId;
	}

	public void setMallId(int mallId) {
		this.mallId = mallId;
	}

	public String getSlotsName() {
		return slotsName;
	}

	public void setSlotsName(String slotsName) {
		this.slotsName = slotsName;
	}

	public Long getSlotsPrice() {
		return slotsPrice;
	}

	public void setSlotsPrice(Long slotsPrice) {
		this.slotsPrice = slotsPrice;
	}

	public int getSlotsStatus() {
		return slotsStatus;
	}

	public void setSlotsStatus(int slotsStatus) {
		this.slotsStatus = slotsStatus;
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

}
