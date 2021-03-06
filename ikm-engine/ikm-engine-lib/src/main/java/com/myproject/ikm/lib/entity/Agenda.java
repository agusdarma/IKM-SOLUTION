package com.myproject.ikm.lib.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Agenda implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String kodeKelas;
	private String namaKelas;
	private String subject;
	private String kodeSekolah;
	private String namaSekolah;
	private Date tanggalAgenda;
	private String isiAgenda;
	private int agendaType;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
