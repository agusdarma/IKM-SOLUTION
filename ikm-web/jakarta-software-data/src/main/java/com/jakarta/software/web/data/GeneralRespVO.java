package com.jakarta.software.web.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GeneralRespVO  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String resultCode;
	private String errorMessage;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
