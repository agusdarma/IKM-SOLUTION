package com.jakarta.software.web.data;

import java.util.List;

import javax.security.auth.Subject;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.entity.Kelas;

public class RespListKelasVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private List<Kelas> listKelas;
	private List<Subject> listSubjects;
	private int jumlahMessageUnread;
	private boolean isWaliKelas;
	private List<ListRecepientMessageVO> recepientsMessage;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

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
