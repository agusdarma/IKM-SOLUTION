package com.ikm.data;

public class LoginData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String kodeSekolah;
	private String noInduk;
	private String password;
	private String originRequest;
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

	public String getOriginRequest() {
		return originRequest;
	}

	public void setOriginRequest(String originRequest) {
		this.originRequest = originRequest;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

}
