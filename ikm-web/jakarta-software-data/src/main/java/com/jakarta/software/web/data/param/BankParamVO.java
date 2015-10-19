package com.jakarta.software.web.data.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BankParamVO extends ParamPagingVO {
	private static final long serialVersionUID = 1L;
	
	private String bankCode;
	private String bankName;
	private String switching;
	
	@Override
	protected String getPrimaryKey() {
		return "";
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSwitching() {
		return switching;
	}

	public void setSwitching(String switching) {
		this.switching = switching;
	} 

}
