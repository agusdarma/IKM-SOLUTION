package com.myproject.ikm.lib.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.myproject.ikm.lib.entity.Kelas;

public class RespListKelasVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private List<Kelas> listKelas;
	private int jumlahMessageUnread;

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

	

	
	
}
