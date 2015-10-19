package com.jakarta.software.web.mapper;

import com.jakarta.software.web.entity.PasswordHistory;

public interface PassHistoryMapper {
	
	public PasswordHistory  findPassHistoryByUserId(String userId);
	
	public void createPassHistory (PasswordHistory passwordHistory);
	
	public void updatePassHistory (PasswordHistory passwordHistory);
}
