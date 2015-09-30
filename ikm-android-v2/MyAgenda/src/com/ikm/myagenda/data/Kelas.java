package com.ikm.myagenda.data;

import java.util.Date;

public class Kelas implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String kodeKelas;
	private String namaKelas;
	private int waliKelasId;
	private Date createdOn;
	private Date updatedOn;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getWaliKelasId() {
		return waliKelasId;
	}

	public void setWaliKelasId(int waliKelasId) {
		this.waliKelasId = waliKelasId;
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

	
	

}
