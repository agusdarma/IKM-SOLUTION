package com.ikm.myagenda.data;

import java.io.Serializable;

public class ReqSendMessageData extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isiMessage;

	

	public String getIsiMessage() {
		return isiMessage;
	}

	public void setIsiMessage(String isiMessage) {
		this.isiMessage = isiMessage;
	}

}
