package com.ikm.data;

public class LoginData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String kodeSekolah;
	private String noInduk;

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

}
