package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.BankVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.BankParamVO;
import com.jakarta.software.web.entity.Bank;
import com.jakarta.software.web.entity.BillPayment;
import com.jakarta.software.web.mapper.BankMapper;

@Service
public class BankService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);
	
	@Autowired
	private BankMapper bankMapper;
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private SettingService settingService;
	
	public int countBankByParam(BankParamVO paramVO){
		try {
			int record = bankMapper.countBankByParam(paramVO);
			return record;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return 0;
		}
	}
	
	public List<BankVO> findBankByParam(BankParamVO paramVO) throws MmbsWebException{
		try {
			List<BankVO> listBank = bankMapper.findBankByParam(paramVO);
			return listBank;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<BankVO>();
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO insertOrUpdateBank(Bank bank, UserDataLoginVO loginVO, Locale language) throws MmbsWebException{
		WebResultVO wrv = new WebResultVO();
		Date current = new Date();
		
		bank.setUpdatedBy(loginVO.getId());
		bank.setUpdatedOn(current);
		
		/*if (bank.isShow() == true) {
			bank.setShowValue(WebConstants.YES);
		}else{
			bank.setShowValue(WebConstants.NO);
		}*/
		
		try {
			// validate input
			validateInput(bank, language);
			bank.setShowValue(WebConstants.YES);
			
			//insert
			if(bank.getId() == 0){
				checkDuplicateBankCode(bank.getId(), bank.getBankCode(), language);
				bank.setCreatedBy(loginVO.getId());
				bank.setCreatedOn(current);
				
				LOGGER.info("Processing -> Insert Bank: {}, user: {}", bank, loginVO.getUserName());			
				
				bankMapper.insertBank(bank);				
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.bankSetting", language), messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);		
				
				/** HISTORY ACTIVITY **/
				try {
					Bank bankOri=new Bank();
					Collection<String> excludes=new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					excludes.add("show");
					
					userActivityService.generateHistoryActivity(excludes, bankOri, bank, loginVO.getId(), "Bank", 
							WebConstants.ACT_TYPE_INSERT , "bank", loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
					throw e;
				}
				/** HISTORY ACTIVITY **/
			}
			else{ //update
				Bank bankOri = findBankById(bank.getId());
				
				checkDuplicateBankCode(bank.getId(), bank.getBankCode(), language);
				
				LOGGER.info("Processing -> Update Bank: {}, user: {}", bank, loginVO.getUserName());
				
				bankMapper.updateBank(bank);
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.bankSetting", language), messageService.getMessageFor("l.updated", language)}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);
				wrv.setPath(WebConstants.PATH_UPDATE_BANK);
				
				/** HISTORY ACTIVITY **/
				try {
					Collection<String> excludes=new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					excludes.add("show");
					
					userActivityService.generateHistoryActivity(excludes, bankOri, bank, loginVO.getId(), "Bank", 
							WebConstants.ACT_TYPE_UPDATE , "bank", loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
					throw e;
				}
				/** HISTORY ACTIVITY **/
			}
			return wrv;
		}catch (MmbsWebException e){
			throw e;
		}catch (Exception e) {
			LOGGER.error("exception: " +e, e);
			throw new MmbsWebException(e);
		}		
	}
	
	public void validateInput(Bank bank, Locale language) throws MmbsWebException{
		if (StringUtils.isEmpty(bank.getBankCode())) {
			LOGGER.warn("Missing Input Bank Code..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.bankCode", language)});
		}
		else
		{
			if(bank.getBankCode().length() < settingService.getSettingAsInt(SettingService.SETTING_MINIMUM_LENGTH_BANK_CODE))
			{
				LOGGER.warn("Bank Code Length below minimum length ..");
				throw new MmbsWebException(MmbsWebException.NE_INVALID_MINIMUM_CHAR_LENGTH,new String[] {
						messageService.getMessageFor("l.bankCode", language), 
						Integer.toString(settingService.getSettingAsInt(SettingService.SETTING_MINIMUM_LENGTH_BANK_CODE))});				
			}
		}
		if (StringUtils.isEmpty(bank.getBankName())) {
			LOGGER.warn("Missing Input Bank Name..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.bankName", language)});
		}
	}
	
	public void checkDuplicateBankCode(int id, String bankCode, Locale language) throws MmbsWebException {
		LOGGER.debug("Processing - > checkDuplicateBankCode: [{}]", bankCode);
		Bank bank = bankMapper.findBankByBankCode(bankCode);
		if (bank != null) {
			if (id != 0) {
				if (bank.getId() != id)
					throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.bankCode", language)});
			} else
				throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.bankCode", language)});
		}
	}
	
	public Bank findBankById(int id) throws MmbsWebException{
		try {
			Bank bank = bankMapper.findBankById(id);
			return bank;
		} catch (Exception e) {
			LOGGER.error("exception: " +e, e);
		}
		return null;
	}
	
	public Bank findBankByCode(String code) {
		Bank bank = bankMapper.findBankByCode(code);
		return bank;
	}
	
	public List<Bank> selectAllBank() throws MmbsWebException{
		try {
			List<Bank> listBank = bankMapper.selectAllBank();
			return listBank;
		} catch (Exception e) {
			LOGGER.error("exception: " +e, e);
			return new ArrayList<Bank>();
		}
	}
}
