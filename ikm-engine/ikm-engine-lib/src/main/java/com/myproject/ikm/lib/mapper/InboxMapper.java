package com.myproject.ikm.lib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myproject.ikm.lib.data.InboxVO;

public interface InboxMapper {
	
	public List<InboxVO> findListInboxByUser(@Param("userId") int userId);
	
	public int countResponInboxUnReadByUser(@Param("userId") int userId);
}
