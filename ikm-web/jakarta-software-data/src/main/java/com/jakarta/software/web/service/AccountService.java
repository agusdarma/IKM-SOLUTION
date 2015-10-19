package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jakarta.software.web.data.param.AccountHistoryParamVO;
import com.jakarta.software.web.entity.Account;
import com.jakarta.software.web.entity.AccountHistory;
import com.jakarta.software.web.mapper.AccountHistoryMapper;
import com.jakarta.software.web.mapper.AccountMapper;

@Service
public class AccountService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountHistoryMapper accountHistoryMapper;
	
	public List<Account> getListAccount(int cifId) throws MmbsWebException{
		try {
			List<Account> listAccount = accountMapper.findListAccById(cifId);
			return listAccount;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<Account>();
		}
	}
	
	public List<Account> findListAccByCifId(int cifId) throws MmbsWebException{
		try {
			List<Account> listAccountHist = accountMapper.findListAccByCifId(cifId);
			return listAccountHist;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<Account>();
		}
	}
	
	public List<AccountHistory> getListAccountHist(int cifIdHistory) throws MmbsWebException{
		try {
			LOGGER.debug("cifHistoryId " + cifIdHistory);
			List<AccountHistory> listAccountHistory = accountMapper.findListAccHistById(cifIdHistory);
			return listAccountHistory;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<AccountHistory>();
		}
	}
	
	public List<AccountHistory> findListAccountHistoryByCifHistoryId(int cifId)
	{
		try {
			LOGGER.debug("findListAccountHistoryByCifHistoryId cifId: " + cifId);
			List<AccountHistory> listAccountHist = accountHistoryMapper.findListAccountHistoryByCifHistoryId(cifId);
			return listAccountHist;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<AccountHistory>();
		}
	}
	
	public int checkAccountHistoryByAccNo(String accountNo)
	{
		int counter = accountMapper.checkAccountHistoryByAccNo(accountNo);
		return counter;
	}
	
	public int checkAccountByAccNo(String accountNo)
	{
		int counter = accountMapper.checkAccountByAccNo(accountNo);
		return counter;		
	}
}
