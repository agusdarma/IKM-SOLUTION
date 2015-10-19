package com.jakarta.software.web.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import ch.local.common.util.Diffable;

public class AccountHistory implements Serializable, Diffable{
	private static final long serialVersionUID = 1L;

	private int id;
	private int cifIdHistory;
	private String accountNo;
	private String cardNo;
	private String accountType;
	private String status;
	private String modifyStatus;
	private String remarks;
	private int accIndex;
	private Date createdOn;
	private int createdBy;
	private Date updatedOn;
	private int updatedBy;
	
	/* for display */
	private String accountTypeDisplay;
	private String cardTypeDisplay;
	private String statusDisplay;
	private String createdByDisplay;
	private String updatedByDisplay;
	
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

	public int getCifIdHistory() {
		return cifIdHistory;
	}

	public void setCifIdHistory(int cifIdHistory) {
		this.cifIdHistory = cifIdHistory;
	}


	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCardNo() {
		if(cardNo==null)
		{
			cardNo="";
		}
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getAccIndex() {
		return accIndex;
	}

	public void setAccIndex(int accIndex) {
		this.accIndex = accIndex;
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

	public String getAccountTypeDisplay() {
		return accountTypeDisplay;
	}

	public void setAccountTypeDisplay(String accountTypeDisplay) {
		this.accountTypeDisplay = accountTypeDisplay;
	}

	public String getCardTypeDisplay() {
		return cardTypeDisplay;
	}

	public void setCardTypeDisplay(String cardTypeDisplay) {
		this.cardTypeDisplay = cardTypeDisplay;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

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

	public String getModifyStatus() {
		return modifyStatus;
	}

	public void setModifyStatus(String modifyStatus) {
		this.modifyStatus = modifyStatus;
	}
	
}
