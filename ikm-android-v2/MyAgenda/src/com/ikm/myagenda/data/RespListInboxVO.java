package com.ikm.myagenda.data;

import java.util.List;

public class RespListInboxVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private List<InboxVO> listInboxVO;
	private int jumlahMessageUnread;
	

	public List<InboxVO> getListInboxVO() {
		return listInboxVO;
	}

	public void setListInboxVO(List<InboxVO> listInboxVO) {
		this.listInboxVO = listInboxVO;
	}

	public int getJumlahMessageUnread() {
		return jumlahMessageUnread;
	}

	public void setJumlahMessageUnread(int jumlahMessageUnread) {
		this.jumlahMessageUnread = jumlahMessageUnread;
	}

	
	
}
