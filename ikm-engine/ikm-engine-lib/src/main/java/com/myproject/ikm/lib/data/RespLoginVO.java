package com.myproject.ikm.lib.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RespLoginVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String kodeSekolah;
	private String noInduk;
	private String nama;
	private int userType;
	private int statusUser;
	private String password;
	private String originRequest;

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

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getStatusUser() {
		return statusUser;
	}

	public void setStatusUser(int statusUser) {
		this.statusUser = statusUser;
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
}
