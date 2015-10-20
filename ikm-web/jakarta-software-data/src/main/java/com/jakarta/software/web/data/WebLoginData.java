package com.jakarta.software.web.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class WebLoginData {	
	private String kodeSekolah;
	private String noInduk;
	private String password;
	private String originRequest;
	private int userType;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

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
