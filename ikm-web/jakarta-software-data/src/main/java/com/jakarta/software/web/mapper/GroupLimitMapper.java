package com.jakarta.software.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jakarta.software.web.data.param.GroupLimitParamVO;
import com.jakarta.software.web.entity.GroupLimitDetail;
import com.jakarta.software.web.entity.GroupLimitHeader;

public interface GroupLimitMapper {
	
	public List<GroupLimitHeader> findAllGroupLimit();
	
	public void insertGroupLimitHeader(GroupLimitHeader groupLimitHeader);
	
	public void updateGroupLimitHeader(GroupLimitHeader groupLimitHeader);
	
	public void insertGroupLimitDetail(GroupLimitDetail groupLimitDetail);
	
	public void updateGroupLimitDetail(GroupLimitDetail groupLimitDetail);
	
	public void deleteGroupLimitDetail(int detailId);
	
	public List<GroupLimitHeader> findListGroupLimitHeaderByParam(GroupLimitParamVO groupLimitParamVO);
	
	public int countListGroupLimitHeaderByParam(GroupLimitParamVO groupLimitParamVO);
	
	public List<GroupLimitDetail> findListGroupLimitDetailByHeaderId(int headerId);
	
	public GroupLimitHeader findGroupLimitHeaderById(int headerId);
	
	public GroupLimitDetail findGroupLimitDetailById(int detailId);
	
	public int findDuplicateTrxCode(@Param("trxCode") String trxCode, @Param("groupLimitId") int groupLimitId);
	
	public int findDuplicateName(String headerName);
	
	
}
