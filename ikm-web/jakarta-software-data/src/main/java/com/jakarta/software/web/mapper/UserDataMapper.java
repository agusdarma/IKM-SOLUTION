package com.jakarta.software.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.UserData;

public interface UserDataMapper {

	public void insertUserData(UserData userData);

	public void updateUserData(UserData userData);

	public void insertUserDataHistory(UserData userData);

	public void changeUserDataAuthStatus(UserData userData);

	public void changeUserDataHistoryAuthStatus(UserDataVO userDataVO);

	public UserDataVO findUserDataByUserCode(String userCode);

	public int checkSessionLogin(@Param("userId") int userId, @Param("sessionId") String sessionId);

	public void clearSessionLogin(int userId);

	public int countLevelUsedByName(int LevelId);

	public int countUserValidate(String userCode);

	public int countUserValidateHistory(String userCode);

	public List<UserDataVO> findUserByParam(UserDataParamVO addNewUserParamVO);

	public int countUserByParam(UserDataParamVO addNewUserParamVO);

	public List<UserDataVO> findUserHistoryByParam(UserDataParamVO addNewUserParamVO);

	public int countUserHistoryByParam(UserDataParamVO addNewUserParamVO);

	public UserDataVO findUserById(int userId);

	public UserDataVO findUserByIdHistory(int userId);
	
	public UserDataVO findCurrentUserById(int userId);
}
