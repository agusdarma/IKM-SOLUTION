package com.ikm.data;

import java.util.List;

public class RespListInboxVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private List<InboxVO> listInboxVO;

	

	public List<InboxVO> getListInboxVO() {
		return listInboxVO;
	}

	public void setListInboxVO(List<InboxVO> listInboxVO) {
		this.listInboxVO = listInboxVO;
	}

	
	
}
