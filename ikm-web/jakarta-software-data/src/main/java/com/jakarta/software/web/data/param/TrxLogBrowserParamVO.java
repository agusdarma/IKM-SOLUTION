package com.jakarta.software.web.data.param;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TrxLogBrowserParamVO extends ParamPagingVO {
	private static final long serialVersionUID = 1L;
	
	private Date startDate;
	private Date endDate;
	private String syslogno;
	private String phoneNo;
	private String sourceAccount;
	private String cardNo;
	private String trxCode;
	private String bankCode;
	private String productCode;
	private String billerCode;
	private String accountType;
	private String status;
	private String trxType;
	private String channelType;
	
	@Override
	protected String getPrimaryKey() {
		return "";
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSyslogno() {
		return syslogno;
	}

	public void setSyslogno(String syslogno) {
		this.syslogno = syslogno;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
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

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

}
