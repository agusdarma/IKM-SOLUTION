package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.entity.AccountHistory;

public interface AccountHistoryMapper {

	public void createAccountHistory(AccountHistory accountHistory);
	
	public AccountHistory findByAccountNo(String accountNo);

	public void updateAccountHistory(AccountHistory accountHistory);
	
	public List<AccountHistory> findListAccountHistoryByCifHistoryId(int cifHistId);
}
