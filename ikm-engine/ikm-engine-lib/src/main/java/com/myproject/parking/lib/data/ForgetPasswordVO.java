package com.myproject.parking.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ForgetPasswordVO extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
