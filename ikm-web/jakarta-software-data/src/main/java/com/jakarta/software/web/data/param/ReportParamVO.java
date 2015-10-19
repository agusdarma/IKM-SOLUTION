package com.jakarta.software.web.data.param;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.utils.StringUtils;

public class ReportParamVO extends ParamPagingVO{
	private static final long serialVersionUID = 1L;
	
	private Date inputReceivedStart;
	private Date inputReceivedEnd;
	private String initiatorCode;
	private String channelInput;
	private String messageInput;
	
	private Date outputReceivedStart;
	private Date outputReceivedEnd;
	private String channelOutput;
	
	private String deviceCode;
	private String transactionType;
	private String mobileNumber;
	private Date startDate;
	private Date endDate;
	private int accessType;
	private String trxType;
	private String accountNo;
	
	private String bankCode;
	private String category;
	private String operatorCode;
	
	private String status;
	private String authStatus;
	private int groupMsisdnId;
	
	private int cifGroup;
	private int accessGroup;
	
	private String merchantCode;
	private String merchantName;
	
	private int month;
	private int year;

	private String channelType;
	private String trxCode;	
	
	public String getDeviceCode() {
		if(!org.apache.commons.lang3.StringUtils.isEmpty(deviceCode))
		{
			deviceCode=StringUtils.formatPhone(deviceCode);
		}
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
	public String getMessageInput() {
		return messageInput;
	}

	public void setMessageInput(String messageInput) {
		this.messageInput = messageInput;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	@Override
	protected String getPrimaryKey() {
		return null;
	}
	
	public Date getInputReceivedStart() {
		return inputReceivedStart;
	}
	public void setInputReceivedStart(Date inputReceivedStart) {
		this.inputReceivedStart = inputReceivedStart;
	}
	public Date getInputReceivedEnd() {
		return inputReceivedEnd;
	}
	public void setInputReceivedEnd(Date inputReceivedEnd) {
		this.inputReceivedEnd = inputReceivedEnd;
	}
	
	public String getInitiatorCode() {
		return initiatorCode;
	}

	public void setInitiatorCode(String initiatorCode) {
		this.initiatorCode = initiatorCode;
	}

	public String getChannelInput() {
		return channelInput;
	}
	public void setChannelInput(String channelInput) {
		this.channelInput = channelInput;
	}

	public Date getOutputReceivedStart() {
		return outputReceivedStart;
	}

	public void setOutputReceivedStart(Date outputReceivedStart) {
		this.outputReceivedStart = outputReceivedStart;
	}

	public Date getOutputReceivedEnd() {
		return outputReceivedEnd;
	}

	public void setOutputReceivedEnd(Date outputReceivedEnd) {
		this.outputReceivedEnd = outputReceivedEnd;
	}

	public String getChannelOutput() {
		return channelOutput;
	}

	public void setChannelOutput(String channelOutput) {
		this.channelOutput = channelOutput;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getMobileNumber() {
		if(mobileNumber!=null)
		{
			if(mobileNumber.startsWith("0")||mobileNumber.startsWith("62")||mobileNumber.startsWith("+0"))
			{
				if(mobileNumber.startsWith("0"))
				{
					mobileNumber=mobileNumber.substring(1, mobileNumber.length());
				}
				else
				{	
					mobileNumber=mobileNumber.substring(2, mobileNumber.length());
				}
				mobileNumber="+62"+mobileNumber;
			}
		}
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if (startDate == null) 
		{
			this.startDate = null;
		}
		else 
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			this.startDate = cal.getTime();
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if (endDate == null)
		{
			this.endDate = null;
		}
		else
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);		
			this.endDate = cal.getTime();
		}
	}

	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getGroupMsisdnId() {
		return groupMsisdnId;
	}

	public void setGroupMsisdnId(int groupMsisdnId) {
		this.groupMsisdnId = groupMsisdnId;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public int getAccessGroup() {
		return accessGroup;
	}

	public void setAccessGroup(int accessGroup) {
		this.accessGroup = accessGroup;
	}

	public int getCifGroup() {
		return cifGroup;
	}

	public void setCifGroup(int cifGroup) {
		this.cifGroup = cifGroup;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}	
	
}
