package com.jakarta.software.web.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ReqAddAgendaData extends WebLoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String isiAgenda;
	private String kodeKelas;
	private String subject;
	private String namaKelas;
	private String namaSekolah;
	private Date tanggalAgenda;
	private String tanggalAgendaInput;
	private int agendaType;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTanggalAgendaInput() {
		return tanggalAgendaInput;
	}

	public void setTanggalAgendaInput(String tanggalAgendaInput) {
		this.tanggalAgendaInput = tanggalAgendaInput;
	}

}
