package com.myproject.ikm.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ListRecepientMessageVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
