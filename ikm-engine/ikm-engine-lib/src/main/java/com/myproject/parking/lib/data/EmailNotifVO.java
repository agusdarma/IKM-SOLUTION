package com.myproject.parking.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class EmailNotifVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int isSentEmail;
	private String bookingCode;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getIsSentEmail() {
		return isSentEmail;
	}

	public void setIsSentEmail(int isSentEmail) {
		this.isSentEmail = isSentEmail;
	}

	public String getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}

	

}
