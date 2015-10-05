package com.ikm.myagenda.data;

import java.util.List;

public class RespListKelasVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private List<Kelas> listKelas;
	private List<Subject> listSubjects;
	private int jumlahMessageUnread;
	private boolean isWaliKelas;
	private List<ListRecepientMessageVO> recepientsMessage;
	

	public List<Kelas> getListKelas() {
		return listKelas;
	}

	public void setListKelas(List<Kelas> listKelas) {
		this.listKelas = listKelas;
	}

	public int getJumlahMessageUnread() {
		return jumlahMessageUnread;
	}

	public void setJumlahMessageUnread(int jumlahMessageUnread) {
		this.jumlahMessageUnread = jumlahMessageUnread;
	}

	public List<Subject> getListSubjects() {
		return listSubjects;
	}

	public void setListSubjects(List<Subject> listSubjects) {
		this.listSubjects = listSubjects;
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

	

	
	
}
