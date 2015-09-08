package com.myproject.ikm.lib.mapper;

import org.apache.ibatis.annotations.Param;

import com.myproject.ikm.lib.entity.User;

public interface UserDataMapper {

	public User findUserByKodeSekolahAndNoIndukAndUserType(@Param("kodeSekolah") String kodeSekolah,
			@Param("noInduk") String noInduk,@Param("userType") int userType);

	
}
