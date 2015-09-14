package com.ikm.data;

import java.io.Serializable;

public class ReqListAgendaData extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;

	private int agendaType;

	public int getAgendaType() {
		return agendaType;
	}

	public void setAgendaType(int agendaType) {
		this.agendaType = agendaType;
	}

	

}
