package com.jakarta.software.web.mapper; 

import java.util.List;

import com.jakarta.software.web.data.param.BranchDataParamVO;
import com.jakarta.software.web.entity.BranchData;

public interface BranchDataMapper {

	public void insertBranchData(BranchData branchdata);
	
	public void updateBranchData(BranchData branchData);
	
	public List<BranchData> selectBranchData();

	public List<BranchData> selectBranchDataByParam(BranchDataParamVO branchdataParamVO);

	public int countBranchDataByParam(BranchDataParamVO branchdataParamVOs);
	
	public BranchData selectBranchDataById(int id);
	
	public int checkDuplicateBranchByCode(String branchCode);
	
}