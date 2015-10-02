package com.myproject.ikm.lib.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class AgendaDetailVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String subject;
	private String isiAgenda;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIsiAgenda() {
		return isiAgenda;
	}

	public void setIsiAgenda(String isiAgenda) {
		this.isiAgenda = isiAgenda;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
