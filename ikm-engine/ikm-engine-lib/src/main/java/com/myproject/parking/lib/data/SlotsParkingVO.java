package com.myproject.parking.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.myproject.parking.lib.entity.Mall;
import com.myproject.parking.lib.entity.MallSlots;

public class SlotsParkingVO extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * MALL
	 */
	private String mallCode;
	private String mallName;
	private String mallAddress;
	private String mallPhone;
	private int status;
	
	/*
	 * SLOTS MALL
	 */
	private int idSlot;
	private String slotsName;
	private Long slotsPrice;
	private int slotsStatus;
	private String bookingId;
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getIdSlot() {
		return idSlot;
	}

	public void setIdSlot(int idSlot) {
		this.idSlot = idSlot;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	

	

	
}
