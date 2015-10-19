package com.jakarta.software.web.data.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UssdMenuParamVO extends ParamPagingVO {
	private static final long serialVersionUID = 1L;
	
	private String note;
	private int trxClient;
	
	@Override
	protected String getPrimaryKey() {
		return "id";
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public int getTrxClient() {
		return trxClient;
	}
	public void setTrxClient(int trxClient) {
		this.trxClient = trxClient;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	
}
