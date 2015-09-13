package com.myproject.ikm.lib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ikm.lib.data.LoginData;
import com.myproject.ikm.lib.data.RespLoginVO;
import com.myproject.ikm.lib.entity.User;
import com.myproject.ikm.lib.mapper.UserDataMapper;
import com.myproject.ikm.lib.utils.CipherUtil;
import com.myproject.ikm.lib.utils.Constants;

@Service
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	
	
	public RespLoginVO login(LoginData loginData) throws IkmEngineException {
		LOG.debug("login with param : " + " loginData: " + loginData );	
		User user = userDataMapper.findUserByKodeSekolahAndNoIndukAndUserType(loginData.getKodeSekolah(), loginData.getNoInduk(),loginData.getUserType());
		if(user == null){
			LOG.error("Can't find User with parameter: " + loginData);
			throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
		}
		if(Constants.BLOCKED == user.getStatusUser()){
			LOG.error("User already blocked");
			throw new IkmEngineException(IkmEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatusUser()){
			LOG.error("User not active");
			throw new IkmEngineException(IkmEngineException.ENGINE_USER_NOT_ACTIVE);
		}	
		if(Constants.TEACHER == loginData.getUserType()){
			String passwordDB = user.getPassword();
			String passwordInput = CipherUtil.passwordDigest(loginData.getKodeSekolah(), loginData.getPassword());
			if(!passwordDB.equals(passwordInput)){
				throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
			}
		}
		RespLoginVO respLoginVO = new RespLoginVO();
		respLoginVO.setId(user.getId());
		respLoginVO.setKodeSekolah(user.getKodeSekolah());
		respLoginVO.setNama(user.getNama());
		respLoginVO.setNoInduk(user.getNoInduk());
		respLoginVO.setStatusUser(user.getStatusUser());
		respLoginVO.setUserType(user.getUserType());	
		LOG.info("login done with param : " + " respLoginVO: " + respLoginVO);
		return respLoginVO;		
	}
	
}
