package com.myproject.ikm.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ChangePasswordVO extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String oldPassword;
	private String newPassword;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
