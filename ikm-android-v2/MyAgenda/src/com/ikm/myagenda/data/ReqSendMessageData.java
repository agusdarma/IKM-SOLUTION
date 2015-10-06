package com.ikm.myagenda.data;

import java.io.Serializable;

public class ReqSendMessageData extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isiMessage;
	private int recepientId;
	

	public String getIsiMessage() {
		return isiMessage;
	}

	public void setIsiMessage(String isiMessage) {
		this.isiMessage = isiMessage;
	}

	public int getRecepientId() {
		return recepientId;
	}

	public void setRecepientId(int recepientId) {
		this.recepientId = recepientId;
	}

}
