package com.myproject.ikm.lib.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RespListAgendaVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private List<AgendaVO> listAgendaVo;
	private int jumlahMessageUnread;

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

	public List<AgendaVO> getListAgendaVo() {
		return listAgendaVo;
	}

	public void setListAgendaVo(List<AgendaVO> listAgendaVo) {
		this.listAgendaVo = listAgendaVo;
	}

}
