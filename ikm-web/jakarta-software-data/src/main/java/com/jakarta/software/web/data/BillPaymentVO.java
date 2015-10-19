package com.jakarta.software.web.data;

import com.jakarta.software.web.entity.BillPayment;

public class BillPaymentVO extends BillPayment{
	private static final long serialVersionUID = 1L;
	
	private String statusDisplay;
	private String createdByDisplay;
	private String updatedByDisplay;
	private String keyType1;
	private String billMinLength1;
	private String billMaxLength1;
	private String keyType2;
	private String billMinLength2;
	private String billMaxLength2;
	private String keyType3;
	private String billMinLength3;
	private String billMaxLength3;
	
	public String getCreatedByDisplay() {
		return createdByDisplay;
	}
	public void setCreatedByDisplay(String createdByDisplay) {
		this.createdByDisplay = createdByDisplay;
	}
	public String getUpdatedByDisplay() {
		return updatedByDisplay;
	}
	public void setUpdatedByDisplay(String updatedByDisplay) {
		this.updatedByDisplay = updatedByDisplay;
	}
	public String getKeyType1() {
		return keyType1;
	}
	public void setKeyType1(String keyType1) {
		this.keyType1 = keyType1;
	}
	public String getBillMinLength1() {
		return billMinLength1;
	}
	public void setBillMinLength1(String billMinLength1) {
		this.billMinLength1 = billMinLength1;
	}
	public String getBillMaxLength1() {
		return billMaxLength1;
	}
	public void setBillMaxLength1(String billMaxLength1) {
		this.billMaxLength1 = billMaxLength1;
	}
	public String getKeyType2() {
		return keyType2;
	}
	public void setKeyType2(String keyType2) {
		this.keyType2 = keyType2;
	}
	public String getBillMinLength2() {
		return billMinLength2;
	}
	public void setBillMinLength2(String billMinLength2) {
		this.billMinLength2 = billMinLength2;
	}
	public String getBillMaxLength2() {
		return billMaxLength2;
	}
	public void setBillMaxLength2(String billMaxLength2) {
		this.billMaxLength2 = billMaxLength2;
	}
	public String getKeyType3() {
		return keyType3;
	}
	public void setKeyType3(String keyType3) {
		this.keyType3 = keyType3;
	}
	public String getBillMinLength3() {
		return billMinLength3;
	}
	public void setBillMinLength3(String billMinLength3) {
		this.billMinLength3 = billMinLength3;
	}
	public String getBillMaxLength3() {
		return billMaxLength3;
	}
	public void setBillMaxLength3(String billMaxLength3) {
		this.billMaxLength3 = billMaxLength3;
	}
	public String getStatusDisplay() {
		return statusDisplay;
	}
	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}
	
}
