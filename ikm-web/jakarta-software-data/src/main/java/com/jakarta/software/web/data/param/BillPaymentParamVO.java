package com.jakarta.software.web.data.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BillPaymentParamVO extends ParamPagingVO {
	private static final long serialVersionUID = 1L;
	
	private String billerNo;
	private String billerName;
	
	@Override
	protected String getPrimaryKey() {
		return "";
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
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

}
