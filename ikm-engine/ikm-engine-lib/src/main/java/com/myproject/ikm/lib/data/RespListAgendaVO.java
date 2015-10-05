package com.myproject.ikm.lib.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RespListAgendaVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private List<AgendaHeaderVO> listAgendaHeaderVO;
	private int jumlahMessageUnread;
	private List<ListRecepientMessageVO> recepientsMessage;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getJumlahMessageUnread() {
		return jumlahMessageUnread;
	}

	public void setJumlahMessageUnread(int jumlahMessageUnread) {
		this.jumlahMessageUnread = jumlahMessageUnread;
	}

	public List<AgendaHeaderVO> getListAgendaHeaderVO() {
		return listAgendaHeaderVO;
	}

	public void setListAgendaHeaderVO(List<AgendaHeaderVO> listAgendaHeaderVO) {
		this.listAgendaHeaderVO = listAgendaHeaderVO;
	}

	public List<ListRecepientMessageVO> getRecepientsMessage() {
		return recepientsMessage;
	}

	public void setRecepientsMessage(List<ListRecepientMessageVO> recepientsMessage) {
		this.recepientsMessage = recepientsMessage;
	}

}
