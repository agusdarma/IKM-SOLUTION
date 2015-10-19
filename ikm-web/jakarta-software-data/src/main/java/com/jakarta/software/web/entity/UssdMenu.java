package com.jakarta.software.web.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UssdMenu implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int TRX_CLIENT_MBANK		= 1;
//	public static final int TRX_CLIENT_EMONEY		= 2;
//	public static final int TRX_CLIENT_BRANCHLESS	= 3;
	
	private int id;
	private int parentId;
	private String note;
	private int showOrder;
	private int command;
	private int operator;
	private String data;
	
	private String trxCode;
	
	private int createdBy;
	private Date createdOn;
	private int updatedBy;
	private Date updatedOn;
	
	// for display
	private String operatorDisplay;
	private String commandDisplay;
	private String trxTypeDisplay;
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getTrxCode() {
		return trxCode;
	}
	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getOperatorDisplay() {
		return operatorDisplay;
	}
	public void setOperatorDisplay(String operatorDisplay) {
		this.operatorDisplay = operatorDisplay;
	}
	public String getCommandDisplay() {
		return commandDisplay;
	}
	public void setCommandDisplay(String commandDisplay) {
		this.commandDisplay = commandDisplay;
	}
	public String getTrxTypeDisplay() {
		return trxTypeDisplay;
	}
	public void setTrxTypeDisplay(String trxTypeDisplay) {
		this.trxTypeDisplay = trxTypeDisplay;
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
}
