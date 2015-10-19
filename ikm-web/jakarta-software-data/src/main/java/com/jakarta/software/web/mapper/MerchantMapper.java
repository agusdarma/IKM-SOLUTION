package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.MerchantVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.entity.Merchant;

public interface MerchantMapper {
	public int countMerchantByParam(MerchantParamVO paramVO);
	
	public List<MerchantVO> findMerchantByParam(MerchantParamVO paramVO);
	
	public Merchant findMerchantById(int id);
	
	public Merchant findMerchantHistoryByMerchantCode(String merchantCode);
	
	public Merchant findMerchantByMerchantCode(String merchantCode);
	
	public void insertMerchantHistory(Merchant merchant);
	
	public void updateMerchantHistory(Merchant merchant);
	
	public void insertMerchant(Merchant merchant);
	
	public void updateMerchant(Merchant merchant);
	
	public int countMerchantHistoryByParam(MerchantParamVO paramVO);
	
	public List<MerchantVO> findMerchantHistoryByParam(MerchantParamVO paramVO);
	
	public MerchantVO findMerchantHistoryByIdHistory(int id);
	
	public void changeMerchantAuthStatus(MerchantVO merchantVO);
	
	public void changeMerchantHistoryAuthStatus(MerchantVO merchantVO);
}
