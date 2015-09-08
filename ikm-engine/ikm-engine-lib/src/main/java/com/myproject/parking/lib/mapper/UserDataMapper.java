package com.myproject.parking.lib.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.entity.UserData;

public interface UserDataMapper {
	public void createUserData(UserData userData);

	public UserData findUserDataByEmailAndPhoneNo(@Param("email") String email,
			@Param("phoneNo") String phoneNo);

	public UserData findUserDataByEmail(@Param("email") String email);

	public UserData findUserByEmailAndPhoneNoAndActKey(
			@Param("email") String email, @Param("phoneNo") String phoneNo,
			@Param("actKey") String actKey);

	public void updateStatusUser(@Param("email") String email,
			@Param("status") int status, @Param("updatedOn") Date updatedOn);

	public void updatePasswordUser(@Param("email") String email,
			@Param("password") String password,
			@Param("updatedOn") Date updatedOn);

	public void updateLoginSessionKey(@Param("id") int id,
			@Param("sessionKey") String sessionKey,
			@Param("timeGenSessionKey") Date timeGenSessionKey,
			@Param("updatedOn") Date updatedOn);

	public void removeSessionKey(@Param("id") int id,
			@Param("sessionKey") String sessionKey,			
			@Param("updatedOn") Date updatedOn);

	public void updateSessionKeyUser(
			@Param("timeSessionKey") Date timeSessionKey,
			@Param("email") String email);
}
