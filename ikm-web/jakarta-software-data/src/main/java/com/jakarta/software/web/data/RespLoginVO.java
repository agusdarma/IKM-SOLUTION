package com.jakarta.software.web.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.entity.UserPreference;

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
	private int jumlahMessageUnread;
	private boolean isWaliKelas;
	private List<ListRecepientMessageVO> recepientsMessage;
	private UserPreference userPreference;

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

	public int getJumlahMessageUnread() {
		return jumlahMessageUnread;
	}

	public void setJumlahMessageUnread(int jumlahMessageUnread) {
		this.jumlahMessageUnread = jumlahMessageUnread;
	}

	public boolean isWaliKelas() {
		return isWaliKelas;
	}

	public void setWaliKelas(boolean isWaliKelas) {
		this.isWaliKelas = isWaliKelas;
	}

	public List<ListRecepientMessageVO> getRecepientsMessage() {
		return recepientsMessage;
	}

	public void setRecepientsMessage(List<ListRecepientMessageVO> recepientsMessage) {
		this.recepientsMessage = recepientsMessage;
	}

	public UserPreference getUserPreference() {
		return userPreference;
	}

	public void setUserPreference(UserPreference userPreference) {
		this.userPreference = userPreference;
	}
}
