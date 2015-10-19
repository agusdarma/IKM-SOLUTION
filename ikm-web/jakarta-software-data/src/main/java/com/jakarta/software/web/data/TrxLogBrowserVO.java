package com.jakarta.software.web.data;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TrxLogBrowserVO {
	
	private String sysLogNo;
	private Date receivedTime;
	private String trxCode;
	private String sourceAccount;
	private String destAccount;
	private String sourceCardNo;
	private String destCardNo;
	private int lastState;
	private String lastRc;
	private double amount;
	private String systraceNo;
	private String rcBti;
	private String deviceCode;
	private String channelType;
	private String hostRef;
	
	private List<TrxLogBrowserDetailVO> listTrxDetail;
	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	public String getSysLogNo() {
		return sysLogNo;
	}
	public void setSysLogNo(String sysLogNo) {
		this.sysLogNo = sysLogNo;
	}
	public Date getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}
	public String getTrxCode() {
		return trxCode;
	}
	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	public String getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public String getDestAccount() {
		return destAccount;
	}
	public void setDestAccount(String destAccount) {
		this.destAccount = destAccount;
	}
	public int getLastState() {
		return lastState;
	}
	public void setLastState(int lastState) {
		this.lastState = lastState;
	}
	public String getLastRc() {
		return lastRc;
	}
	public void setLastRc(String lastRc) {
		this.lastRc = lastRc;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getSystraceNo() {
		return systraceNo;
	}
	public void setSystraceNo(String systraceNo) {
		this.systraceNo = systraceNo;
	}
	public String getRcBti() {
		return rcBti;
	}
	public void setRcBti(String rcBti) {
		this.rcBti = rcBti;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getSourceCardNo() {
		return sourceCardNo;
	}

	public void setSourceCardNo(String sourceCardNo) {
		this.sourceCardNo = sourceCardNo;
	}

	public String getDestCardNo() {
		return destCardNo;
	}

	public void setDestCardNo(String destCardNo) {
		this.destCardNo = destCardNo;
	}

	public List<TrxLogBrowserDetailVO> getListTrxDetail() {
		return listTrxDetail;
	}

	public void setListTrxDetail(List<TrxLogBrowserDetailVO> listTrxDetail) {
		this.listTrxDetail = listTrxDetail;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getHostRef() {
		return hostRef;
	}

	public void setHostRef(String hostRef) {
		this.hostRef = hostRef;
	}

}
