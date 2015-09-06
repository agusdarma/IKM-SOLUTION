package com.parking.entity;

import java.io.Serializable;
import java.util.Date;

public class Mall implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String mallCode;
	private String mallName;
	private String mallAddress;
	private String mallPhone;
	private String mallImage;
	private int status;
	private int slotAvailable;
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

	public String getMallCode() {
		return mallCode;
	}

	public void setMallCode(String mallCode) {
		this.mallCode = mallCode;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public String getMallAddress() {
		return mallAddress;
	}

	public void setMallAddress(String mallAddress) {
		this.mallAddress = mallAddress;
	}

	public String getMallPhone() {
		return mallPhone;
	}

	public void setMallPhone(String mallPhone) {
		this.mallPhone = mallPhone;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMallImage() {
		return mallImage;
	}

	public void setMallImage(String mallImage) {
		this.mallImage = mallImage;
	}

	public int getSlotAvailable() {
		return slotAvailable;
	}

	public void setSlotAvailable(int slotAvailable) {
		this.slotAvailable = slotAvailable;
	}

}
