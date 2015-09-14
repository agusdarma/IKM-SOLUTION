package com.myproject.ikm.lib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myproject.ikm.lib.data.RespInboxVO;

public interface InboxMapper {

	public List<RespInboxVO> findResponInboxUnReadByUser(@Param("userId") int userId);

	public List<RespInboxVO> findRequestInboxUnReadByUser(@Param("userId") int userId);
	
	public int countResponInboxUnReadByUser(@Param("userId") int userId);
}
