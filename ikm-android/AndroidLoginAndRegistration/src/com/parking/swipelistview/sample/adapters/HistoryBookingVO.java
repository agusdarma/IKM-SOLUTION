package com.parking.swipelistview.sample.adapters;

import java.io.Serializable;
import java.util.Date;

import com.parking.data.LoginData;

public class HistoryBookingVO extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String mallName;
	private Date bookingDate;
	private String bookingDateValue;
	private String bookingId;
	private String bookingCode;
	private String hargaParkir;
	private String slotName;
	private int status;
	

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public String getBookingDateValue() {
		return bookingDateValue;
	}

	public void setBookingDateValue(String bookingDateValue) {
		this.bookingDateValue = bookingDateValue;
	}

	public String getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}

	

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHargaParkir() {
		return hargaParkir;
	}

	public void setHargaParkir(String hargaParkir) {
		this.hargaParkir = hargaParkir;
	}

	public String getSlotName() {
		return slotName;
	}

	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}

	

}
