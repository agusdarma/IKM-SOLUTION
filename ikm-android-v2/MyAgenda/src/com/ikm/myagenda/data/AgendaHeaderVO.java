package com.ikm.myagenda.data;

import java.util.List;

public class AgendaHeaderVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String tanggalAgendaVal;
	private String kodeKelas;
	private String namaKelas;
	private String kodeSekolah;
	private String namaSekolah;
	private int agendaType;
	private List<AgendaDetailVO> agendaDetail;
	
	public String getTanggalAgendaVal() {
		return tanggalAgendaVal;
	}

	public void setTanggalAgendaVal(String tanggalAgendaVal) {
		this.tanggalAgendaVal = tanggalAgendaVal;
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

	public int getAgendaType() {
		return agendaType;
	}

	public void setAgendaType(int agendaType) {
		this.agendaType = agendaType;
	}

	public List<AgendaDetailVO> getAgendaDetail() {
		return agendaDetail;
	}

	public void setAgendaDetail(List<AgendaDetailVO> agendaDetail) {
		this.agendaDetail = agendaDetail;
	}
}
