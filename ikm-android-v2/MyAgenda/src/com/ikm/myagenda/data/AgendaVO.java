package com.ikm.myagenda.data;

import java.util.Date;

public class AgendaVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String kodeKelas;
	private String namaKelas;
	private String subject;
	private String kodeSekolah;
	private String namaSekolah;
	private Date tanggalAgenda;
	private String tanggalAgendaVal;
	private String isiAgenda;
	private int agendaType;
	private Date createdOn;
	private String createdOnVal;
	
	

	public String getKodeKelas() {
		return kodeKelas;
	}

	public void setKodeKelas(String kodeKelas) {
		this.kodeKelas = kodeKelas;
	}

	public String getNamaKelas() {
		return namaKelas;
	}

	public void setNamaKelas(String namaKelas) {
		this.namaKelas = namaKelas;
	}

	public String getKodeSekolah() {
		return kodeSekolah;
	}

	public void setKodeSekolah(String kodeSekolah) {
		this.kodeSekolah = kodeSekolah;
	}

	public String getNamaSekolah() {
		return namaSekolah;
	}

	public void setNamaSekolah(String namaSekolah) {
		this.namaSekolah = namaSekolah;
	}

	public Date getTanggalAgenda() {
		return tanggalAgenda;
	}

	public void setTanggalAgenda(Date tanggalAgenda) {
		this.tanggalAgenda = tanggalAgenda;
	}

	public String getIsiAgenda() {
		return isiAgenda;
	}

	public void setIsiAgenda(String isiAgenda) {
		this.isiAgenda = isiAgenda;
	}

	public int getAgendaType() {
		return agendaType;
	}

	public void setAgendaType(int agendaType) {
		this.agendaType = agendaType;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getTanggalAgendaVal() {
		return tanggalAgendaVal;
	}

	public void setTanggalAgendaVal(String tanggalAgendaVal) {
		this.tanggalAgendaVal = tanggalAgendaVal;
	}

	public String getCreatedOnVal() {
		return createdOnVal;
	}

	public void setCreatedOnVal(String createdOnVal) {
		this.createdOnVal = createdOnVal;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
