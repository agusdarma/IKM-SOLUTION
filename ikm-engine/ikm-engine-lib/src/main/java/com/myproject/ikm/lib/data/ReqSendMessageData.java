package com.myproject.ikm.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ReqSendMessageData extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isiMessage;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getIsiMessage() {
		return isiMessage;
	}

	public void setIsiMessage(String isiMessage) {
		this.isiMessage = isiMessage;
	}

}
