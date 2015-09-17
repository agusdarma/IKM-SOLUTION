package com.ikm.data;

import java.io.Serializable;
import java.util.Date;

public class ReqAddAgendaData extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isiAgenda;
	private String kodeKelas;
	private String namaKelas;
	private String namaSekolah;
	private Date tanggalAgenda;
	private int agendaType;

	

	public String getIsiAgenda() {
		return isiAgenda;
	}

	public void setIsiAgenda(String isiAgenda) {
		this.isiAgenda = isiAgenda;
	}

	public Date getTanggalAgenda() {
		return tanggalAgenda;
	}

	public void setTanggalAgenda(Date tanggalAgenda) {
		this.tanggalAgenda = tanggalAgenda;
	}

	public int getAgendaType() {
		return agendaType;
	}

	public void setAgendaType(int agendaType) {
		this.agendaType = agendaType;
	}

	public String getNamaKelas() {
		return namaKelas;
	}

	public void setNamaKelas(String namaKelas) {
		this.namaKelas = namaKelas;
	}

	public String getNamaSekolah() {
		return namaSekolah;
	}

	public void setNamaSekolah(String namaSekolah) {
		this.namaSekolah = namaSekolah;
	}

	public String getKodeKelas() {
		return kodeKelas;
	}

	public void setKodeKelas(String kodeKelas) {
		this.kodeKelas = kodeKelas;
	}

}
