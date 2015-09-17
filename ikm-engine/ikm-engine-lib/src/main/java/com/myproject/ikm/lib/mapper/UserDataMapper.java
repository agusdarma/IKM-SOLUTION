package com.myproject.ikm.lib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myproject.ikm.lib.entity.Kelas;
import com.myproject.ikm.lib.entity.Subject;
import com.myproject.ikm.lib.entity.User;

public interface UserDataMapper {

	public User findUserByKodeSekolahAndNoIndukAndUserType(@Param("kodeSekolah") String kodeSekolah,
			@Param("noInduk") String noInduk,@Param("userType") int userType);

	public List<Kelas> findKelasByUser(@Param("kodeSekolah") String kodeSekolah,
			@Param("userIdLogin") int userIdLogin);
	
	public List<User> findAllRecepientMessageFromTeacher(@Param("kodeSekolah") String kodeSekolah,
			@Param("userIdLogin") int userIdLogin);
	
	public List<Kelas> findAllKelasTeacher(@Param("kodeSekolah") String kodeSekolah,
			@Param("noInduk") String noInduk,@Param("userType") int userType);
	
	public List<Kelas> checkIsWaliKelas(@Param("userId") int userId);
	
	public List<Subject> findAllSubjectTeacher(@Param("kodeSekolah") String kodeSekolah,
			@Param("noInduk") String noInduk,@Param("userType") int userType);
}
