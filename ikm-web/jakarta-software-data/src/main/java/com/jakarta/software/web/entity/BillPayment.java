package com.jakarta.software.web.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BillPayment implements java.io.Serializable {
	private static final long serialVersionUID = 1L; 

	private int id; 
	private String billerNo; 
	private String billerName; 
	private String billerDesc; 
	private String bankRef1; 
	private String bankRef2; 
	private String isoType; 
	private String billRef1; 
	private String billRef2; 
	private String billRef3; 
	private String billType1;
	private String billType2;
	private String billType3;
	private String billMinLength1;
	private String billMinLength2;
	private String billMinLength3;
	private String billMaxLength1;
	private String billMaxLength2;
	private String billMaxLength3;
	private String category; 
	private int billAccNoKey; 
	private int billAmountKey; 
	private String msgCodeSuffix; 
	private String bpAccount; 
	private int showOrder; 
	private String status; 
	private Date createdOn; 
	private int createdBy; 
	private Date updatedOn; 
	private int updatedBy; 
	private String statusDisplay;
	 
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

	public String getBillerNo() {
		return billerNo;
	}

	public void setBillerNo(String billerNo) {
		this.billerNo = billerNo;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getBillerDesc() {
		return billerDesc;
	}

	public void setBillerDesc(String billerDesc) {
		this.billerDesc = billerDesc;
	}

	public String getBankRef1() {
		return bankRef1;
	}

	public void setBankRef1(String bankRef1) {
		this.bankRef1 = bankRef1;
	}

	public String getBankRef2() {
		return bankRef2;
	}

	public void setBankRef2(String bankRef2) {
		this.bankRef2 = bankRef2;
	}

	public String getIsoType() {
		return isoType;
	}

	public void setIsoType(String isoType) {
		this.isoType = isoType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBillAccNoKey() {
		return billAccNoKey;
	}

	public void setBillAccNoKey(int billAccNoKey) {
		this.billAccNoKey = billAccNoKey;
	}

	public int getBillAmountKey() {
		return billAmountKey;
	}

	public void setBillAmountKey(int billAmountKey) {
		this.billAmountKey = billAmountKey;
	}

	public String getMsgCodeSuffix() {
		return msgCodeSuffix;
	}

	public void setMsgCodeSuffix(String msgCodeSuffix) {
		this.msgCodeSuffix = msgCodeSuffix;
	}

	public String getBpAccount() {
		return bpAccount;
	}

	public void setBpAccount(String bpAccount) {
		this.bpAccount = bpAccount;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getBillType1() {
		return billType1;
	}

	public void setBillType1(String billType1) {
		this.billType1 = billType1;
	}

	public String getBillType2() {
		return billType2;
	}

	public void setBillType2(String billType2) {
		this.billType2 = billType2;
	}

	public String getBillType3() {
		return billType3;
	}

	public void setBillType3(String billType3) {
		this.billType3 = billType3;
	}

	public String getBillMinLength1() {
		return billMinLength1;
	}

	public void setBillMinLength1(String billMinLength1) {
		this.billMinLength1 = billMinLength1;
	}

	public String getBillMinLength2() {
		return billMinLength2;
	}

	public void setBillMinLength2(String billMinLength2) {
		this.billMinLength2 = billMinLength2;
	}

	public String getBillMinLength3() {
		return billMinLength3;
	}

	public void setBillMinLength3(String billMinLength3) {
		this.billMinLength3 = billMinLength3;
	}

	public String getBillMaxLength1() {
		return billMaxLength1;
	}

	public void setBillMaxLength1(String billMaxLength1) {
		this.billMaxLength1 = billMaxLength1;
	}

	public String getBillMaxLength2() {
		return billMaxLength2;
	}

	public void setBillMaxLength2(String billMaxLength2) {
		this.billMaxLength2 = billMaxLength2;
	}

	public String getBillMaxLength3() {
		return billMaxLength3;
	}

	public void setBillMaxLength3(String billMaxLength3) {
		this.billMaxLength3 = billMaxLength3;
	}

	public String getBillRef1() {
		return billRef1;
	}

	public void setBillRef1(String billRef1) {
		this.billRef1 = billRef1;
	}

	public String getBillRef2() {
		return billRef2;
	}

	public void setBillRef2(String billRef2) {
		this.billRef2 = billRef2;
	}

	public String getBillRef3() {
		return billRef3;
	}

	public void setBillRef3(String billRef3) {
		this.billRef3 = billRef3;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}	
}
