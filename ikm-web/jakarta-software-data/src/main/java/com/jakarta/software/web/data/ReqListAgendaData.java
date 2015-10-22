package com.jakarta.software.web.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ReqListAgendaData extends WebLoginData implements Serializable {
	private static final long serialVersionUID = 1L;

	private int agendaType;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getAgendaType() {
		return agendaType;
	}

	public void setAgendaType(int agendaType) {
		this.agendaType = agendaType;
	}

	

}
