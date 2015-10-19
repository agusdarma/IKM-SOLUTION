package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.CifVO;
import com.jakarta.software.web.data.GeneralStatusVO;
import com.jakarta.software.web.data.param.CifAuthParamVO;
import com.jakarta.software.web.data.param.CifParamVO;
import com.jakarta.software.web.entity.Cif;

public interface CifMapper {
	public Cif findCifById(int id);
	
	public Cif findCifByMobilePhone(String mobilePhone);
	
	public Cif findCifByAccountNo(String accountNo);
	
	public void createCif(Cif cif);
	
	public void updateCif(Cif cif);
	
	public void authorizeCif(Cif cif);
	
	public void fillRetailerIdToCif(Cif cif);
	
	public List<CifVO> findListCifByParam(CifParamVO cifParamVO);
	
	public int countByParam(CifParamVO cifParamVO);
	
	public int countAuthCifByParam(CifAuthParamVO cifAuthParamVO);
	
	public List<CifVO> findListAuthCifByParam(CifAuthParamVO cifAuthParamVO);
	
	public List<CifVO> findListCifNoPaging(CifParamVO cifParamVO);
	
	public int countListCifNoPaging(CifParamVO cifParamVO);
	
	public List<Cif> findCifByRetailerId(int retailerId);
	
	public List<Cif> findListCifReplacementByParam(CifParamVO cifParamVO);
	
	public List<Integer> findListAccountIdByCifId(List<Integer> listCifId);
	
	public void updateCifStatusByListCifId(GeneralStatusVO generalStatusVO);
	
	public List<Integer> findCifForEditRetailerByRetailerId(int retailerId);
	
	public List<CifVO> findCifForResetPin(CifParamVO cifParamVO);
	
	public int countCifForResetPin(CifParamVO cifParamVO);
	
	public void updateCifAuthStatus(Cif cif);
	
	public void deleteCif(int cifId);
}
