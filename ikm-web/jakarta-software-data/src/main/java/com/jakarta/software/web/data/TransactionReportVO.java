package com.jakarta.software.web.data;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TransactionReportVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String syslogno;
	private String deviceCode;
	private String srac;
	private String resultCode;
	private String btiRefNo;
	private String productCode;
	private String lastRc;
	private String clientAccNo;
	private String productName;
	private String statusDisplay;
	private String status;
	private String message;
	private String groupName;
	private String createdBy;
	private Date createdOn;
	private Date receivedTime;
	private Date sendTime;
	private int groupMsisdnId;
	private int id;
	private Float trxValue;
	private int cifId;
	private String bankCode;
	private String dsac;
	private String merchantName;
	private String merchantCode;
	
	private String trxCode;
	private String channelType;
	private String operatorCode;
	private int totalTransaction;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getSyslogno() {
		return syslogno;
	}

	public void setSyslogno(String syslogno) {
		this.syslogno = syslogno;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getSrac() {
		return srac;
	}

	public void setSrac(String srac) {
		this.srac = srac;
	}

	public Date getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getBtiRefNo() {
		return btiRefNo;
	}

	public void setBtiRefNo(String btiRefNo) {
		this.btiRefNo = btiRefNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getLastRc() {
		return lastRc;
	}

	public void setLastRc(String lastRc) {
		this.lastRc = lastRc;
	}

	public String getClientAccNo() {
		return clientAccNo;
	}

	public void setClientAccNo(String clientAccNo) {
		this.clientAccNo = clientAccNo;
	}

	public Float getTrxValue() {
		return trxValue;
	}

	public void setTrxValue(Float trxValue) {
		this.trxValue = trxValue;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getGroupMsisdnId() {
		return groupMsisdnId;
	}

	public void setGroupMsisdnId(int groupMsisdnId) {
		this.groupMsisdnId = groupMsisdnId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getCifId() {
		return cifId;
	}

	public void setCifId(int cifId) {
		this.cifId = cifId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getDsac() {
		return dsac;
	}

	public void setDsac(String dsac) {
		this.dsac = dsac;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public int getTotalTransaction() {
		return totalTransaction;
	}

	public void setTotalTransaction(int totalTransaction) {
		this.totalTransaction = totalTransaction;
	}

}
