package com.myproject.ikm.lib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myproject.ikm.lib.data.InboxVO;
import com.myproject.ikm.lib.data.ListRecepientMessageVO;
import com.myproject.ikm.lib.entity.Message;

public interface InboxMapper {
	
	public void insertMessage(Message message);
	
	public void updateMessageToRead(@Param("userId") int userId);
	
	public List<InboxVO> findListInboxByUser(@Param("userId") int userId);
	
	public int countResponInboxUnReadByUser(@Param("userId") int userId);
	
	public List<ListRecepientMessageVO> findRecepientMessageByUser(@Param("userId") int userId);
}
