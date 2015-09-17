package com.myproject.ikm.lib.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ikm.lib.data.ReqListKelasData;
import com.myproject.ikm.lib.data.RespListKelasVO;
import com.myproject.ikm.lib.entity.Kelas;
import com.myproject.ikm.lib.entity.User;
import com.myproject.ikm.lib.mapper.InboxMapper;
import com.myproject.ikm.lib.mapper.UserDataMapper;
import com.myproject.ikm.lib.utils.CipherUtil;
import com.myproject.ikm.lib.utils.Constants;

@Service
public class KelasService {
	private static final Logger LOG = LoggerFactory.getLogger(KelasService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Autowired
	private InboxMapper inboxMapper;
	
	public RespListKelasVO findAllKelasTeacher(ReqListKelasData reqListKelasData) throws IkmEngineException {
		LOG.debug("findAllKelasTeacher with param : " + " reqListKelasData: " + reqListKelasData);	
		User user = userDataMapper.findUserByKodeSekolahAndNoIndukAndUserType(reqListKelasData.getKodeSekolah(), reqListKelasData.getNoInduk(),reqListKelasData.getUserType());
		if(user == null){
			LOG.error("Can't find User with parameter: " + reqListKelasData);
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
		if(Constants.TEACHER == reqListKelasData.getUserType()){
			String passwordDB = user.getPassword();
			String passwordInput = CipherUtil.passwordDigest(reqListKelasData.getKodeSekolah(), reqListKelasData.getPassword());
			if(!passwordDB.equals(passwordInput)){
				throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
			}
		}
		RespListKelasVO respListKelasVO = new RespListKelasVO();
		/**
		 * Base Data Login
		 */
		respListKelasVO.setKodeSekolah(reqListKelasData.getKodeSekolah());
		respListKelasVO.setNoInduk(reqListKelasData.getNoInduk());
		respListKelasVO.setOriginRequest(reqListKelasData.getOriginRequest());
		respListKelasVO.setUserType(reqListKelasData.getUserType());
		
		/**
		 * Get data kelas
		 */
		List<Kelas> listKelas = userDataMapper.findAllKelasTeacher(reqListKelasData.getKodeSekolah(), reqListKelasData.getNoInduk(),reqListKelasData.getUserType());
//		/**
//		 * add pengumuman lain
//		 */
//		Kelas pengumumanLain = new Kelas();
//		pengumumanLain.setId(0);
//		pengumumanLain.setKodeKelas("Pengumuman Lain");
//		pengumumanLain.setNamaKelas("Pengumuman Lain");
//		pengumumanLain.setWaliKelasId(0);
//		listKelas.add(pengumumanLain);
		
		if(listKelas.size()>0){
			respListKelasVO.setListKelas(listKelas);
		}
		
		/**
		 * Get data message yang belum dibaca, count saja
		 */
		respListKelasVO.setJumlahMessageUnread(inboxMapper.countResponInboxUnReadByUser(user.getId()));
		
		LOG.info("findAllKelasTeacher done with param : " + " respListKelasVO: " + respListKelasVO);
		return respListKelasVO;		
	}
	
}
