package com.ikm.myagenda.data;

public class InqChangePasswordRequest extends LoginData implements
		java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String oldPassword;
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
