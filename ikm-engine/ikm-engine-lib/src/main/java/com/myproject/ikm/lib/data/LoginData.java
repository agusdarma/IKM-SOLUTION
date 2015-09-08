package com.myproject.ikm.lib.data;

import java.io.Serializable;

public class LoginData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String kodeSekolah;
	private String noInduk;
	private String password;
	private int userType;

	public String getKodeSekolah() {
		return kodeSekolah;
	}

	public void setKodeSekolah(String kodeSekolah) {
		this.kodeSekolah = kodeSekolah;
	}

	public String getNoInduk() {
		return noInduk;
	}

	public void setNoInduk(String noInduk) {
		this.noInduk = noInduk;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

}
