package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.PasswordHistory;
import com.jakarta.software.web.entity.UserData;

public interface PasswordMapper {

	public PasswordHistory findPassHistoryByUserId(int userId);
	
	public void createPassHistory (PasswordHistory passwordHistory);
	
	public void updatePassHistory (PasswordHistory passwordHistory);
	
	public int countResetPasswordUserByParam (UserDataParamVO paramVO);
	
	public List<UserDataVO> findResetPasswordUserByParam (UserDataParamVO paramVO);
	
	public void resetPassword(UserData userData);
	
	public void changePassword(UserData userData);
	
}
