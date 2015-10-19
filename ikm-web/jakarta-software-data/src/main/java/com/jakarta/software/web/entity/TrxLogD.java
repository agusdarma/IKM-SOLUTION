package com.jakarta.software.web.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TrxLogD implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int id;		//int auto_increment not null,
	private int trxLogHId;	//int,
	private String gatewayRef;	//char(14),
	private String msgLogNo;		//char(14),
	private String channelInputId;	//varchar(16),
	private String inputPhoneNo;    //varchar(30),
	private String messageInput;	//VARCHAR(255),
	private Date inputReceived;	//datetime,
	private Date inputTransferred;	//datetime,
	private String channelOutputId;  //varchar(16),
	private String outputPhoneNo;    //varchar(30),
	private String messageOutput;	//VARCHAR(255),
	private Date outputReceived; //datetime,
	private Date outputTransferred;	//datetime,
	private int hostState;	 //int,
	private String hostRc;     //varchar(8),
	private String operatorSid;	//operator_sid VARCHAR(50),
	private String operatorRef; //VARCHAR(50),
	private String drCode;	//dr_code      VARCHAR(6),
	private String drDesc;	//dr_desc      VARCHAR(255),
	private String sentStatus; //sent_status  VARCHAR(6),
	private String sentDesc; //sent_desc    VARCHAR(255),
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
	public int getTrxLogHId() {
		return trxLogHId;
	}
	public void setTrxLogHId(int trxLogHId) {
		this.trxLogHId = trxLogHId;
	}
	public String getGatewayRef() {
		return gatewayRef;
	}
	public void setGatewayRef(String gatewayRef) {
		this.gatewayRef = gatewayRef;
	}
	public String getMsgLogNo() {
		return msgLogNo;
	}
	public void setMsgLogNo(String msgLogNo) {
		this.msgLogNo = msgLogNo;
	}
	public String getChannelInputId() {
		return channelInputId;
	}
	public void setChannelInputId(String channelInputId) {
		this.channelInputId = channelInputId;
	}
	public String getInputPhoneNo() {
		return inputPhoneNo;
	}
	public void setInputPhoneNo(String inputPhoneNo) {
		this.inputPhoneNo = inputPhoneNo;
	}
	public String getMessageInput() {
		return messageInput;
	}
	public void setMessageInput(String messageInput) {
		this.messageInput = messageInput;
	}
	public Date getInputReceived() {
		return inputReceived;
	}
	public void setInputReceived(Date inputReceived) {
		this.inputReceived = inputReceived;
	}
	public Date getInputTransferred() {
		return inputTransferred;
	}
	public void setInputTransferred(Date inputTransferred) {
		this.inputTransferred = inputTransferred;
	}
	public String getChannelOutputId() {
		return channelOutputId;
	}
	public void setChannelOutputId(String channelOutputId) {
		this.channelOutputId = channelOutputId;
	}
	public String getOutputPhoneNo() {
		return outputPhoneNo;
	}
	public void setOutputPhoneNo(String outputPhoneNo) {
		this.outputPhoneNo = outputPhoneNo;
	}
	public String getMessageOutput() {
		return messageOutput;
	}
	public void setMessageOutput(String messageOutput) {
		this.messageOutput = messageOutput;
	}
	public Date getOutputReceived() {
		return outputReceived;
	}
	public void setOutputReceived(Date outputReceived) {
		this.outputReceived = outputReceived;
	}
	public Date getOutputTransferred() {
		return outputTransferred;
	}
	public void setOutputTransferred(Date outputTransferred) {
		this.outputTransferred = outputTransferred;
	}
	public int getHostState() {
		return hostState;
	}
	public void setHostState(int hostState) {
		this.hostState = hostState;
	}
	public String getHostRc() {
		return hostRc;
	}
	public void setHostRc(String hostRc) {
		this.hostRc = hostRc;
	}
	public String getOperatorSid() {
		return operatorSid;
	}
	public void setOperatorSid(String operatorSid) {
		this.operatorSid = operatorSid;
	}
	public String getOperatorRef() {
		return operatorRef;
	}
	public void setOperatorRef(String operatorRef) {
		this.operatorRef = operatorRef;
	}
	public String getDrCode() {
		return drCode;
	}
	public void setDrCode(String drCode) {
		this.drCode = drCode;
	}
	public String getDrDesc() {
		return drDesc;
	}
	public void setDrDesc(String drDesc) {
		this.drDesc = drDesc;
	}
	public String getSentStatus() {
		return sentStatus;
	}
	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}
	public String getSentDesc() {
		return sentDesc;
	}
	public void setSentDesc(String sentDesc) {
		this.sentDesc = sentDesc;
	}
	
	
}
