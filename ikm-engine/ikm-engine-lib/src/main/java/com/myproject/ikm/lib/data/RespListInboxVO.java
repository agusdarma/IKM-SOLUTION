package com.myproject.ikm.lib.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RespListInboxVO extends LoginData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private List<InboxVO> listInboxVO;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public List<InboxVO> getListInboxVO() {
		return listInboxVO;
	}

	public void setListInboxVO(List<InboxVO> listInboxVO) {
		this.listInboxVO = listInboxVO;
	}

	
	
}
