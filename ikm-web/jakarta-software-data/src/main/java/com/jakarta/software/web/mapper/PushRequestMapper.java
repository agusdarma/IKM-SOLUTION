package com.jakarta.software.web.mapper; 

import java.util.List;

import com.jakarta.software.web.data.PushRequestVO;
import com.jakarta.software.web.data.param.PushRequestParamVO;
import com.jakarta.software.web.entity.PushRequestDetail;
import com.jakarta.software.web.entity.PushRequestHeader;

public interface PushRequestMapper {

	public void insertPushRequestHeader(PushRequestHeader pushRequestHeader);
	
	public void insertPushRequestDetail(PushRequestDetail pushRequestDetail);
	
	public PushRequestVO selectPushRequestHeaderById(int id);

	public List<PushRequestHeader> selectPushRequestHeader();

	public List<PushRequestVO> selectPushRequestHeaderByParam(PushRequestParamVO pushRequestParamVO);

	public int countPushRequestHeaderByParam(PushRequestParamVO pushRequestParamVO);

	public void updatePushRequestHeader(PushRequestHeader pushRequestHeader);
	
	public void deletePushRequestDetailByHeaderId(int headerId);
	
	public List<String> selectListMsisdnByHeaderId(int id);

	public List<PushRequestVO> selectListPushRequestDetailByHeaderId(int id);
}