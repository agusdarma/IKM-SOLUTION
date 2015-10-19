package com.jakarta.software.web.data.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ResetPinParamVO {
	private String mobilePhone;
	private String pin;
	private String confirmPin;
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public String getConfirmPin() {
		return confirmPin;
	}
	public void setConfirmPin(String confirmPin) {
		this.confirmPin = confirmPin;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
}
