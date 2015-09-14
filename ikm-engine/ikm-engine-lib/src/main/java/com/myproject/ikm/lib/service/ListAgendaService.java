package com.myproject.ikm.lib.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ikm.lib.data.AgendaVO;
import com.myproject.ikm.lib.data.ReqListAgendaData;
import com.myproject.ikm.lib.data.RespListAgendaVO;
import com.myproject.ikm.lib.entity.User;
import com.myproject.ikm.lib.mapper.AgendaMapper;
import com.myproject.ikm.lib.mapper.InboxMapper;
import com.myproject.ikm.lib.mapper.UserDataMapper;
import com.myproject.ikm.lib.utils.CipherUtil;
import com.myproject.ikm.lib.utils.Constants;

@Service
public class ListAgendaService {
	private static final Logger LOG = LoggerFactory.getLogger(ListAgendaService.class);
	
	@Autowired
	private AgendaMapper agendaMapper;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private InboxMapper inboxMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	
	
	public RespListAgendaVO findAgendaByUser(ReqListAgendaData reqListAgendaData) throws IkmEngineException {
		LOG.debug("findAgendaByUser with param : " + " reqListAgendaData: " + reqListAgendaData);	
		User user = userDataMapper.findUserByKodeSekolahAndNoIndukAndUserType(reqListAgendaData.getKodeSekolah(), reqListAgendaData.getNoInduk(),reqListAgendaData.getUserType());
		if(user == null){
			LOG.error("Can't find User with parameter: " + reqListAgendaData);
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
		if(Constants.TEACHER == reqListAgendaData.getUserType()){
			String passwordDB = user.getPassword();
			String passwordInput = CipherUtil.passwordDigest(reqListAgendaData.getKodeSekolah(), reqListAgendaData.getPassword());
			if(!passwordDB.equals(passwordInput)){
				throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
			}
		}
		RespListAgendaVO respListAgendaVO = new RespListAgendaVO();
		/**
		 * Base Data Login
		 */
		respListAgendaVO.setKodeSekolah(reqListAgendaData.getKodeSekolah());
		respListAgendaVO.setNoInduk(reqListAgendaData.getNoInduk());
		respListAgendaVO.setOriginRequest(reqListAgendaData.getOriginRequest());
		respListAgendaVO.setUserType(reqListAgendaData.getUserType());
		
		/**
		 * Get data agenda
		 */
		List<AgendaVO> listAgendaVo = agendaMapper.findAgendaByUser(reqListAgendaData.getKodeSekolah(), reqListAgendaData.getNoInduk(), reqListAgendaData.getUserType(), reqListAgendaData.getAgendaType());
		if(listAgendaVo.size()>0){
			respListAgendaVO.setListAgendaVo(listAgendaVo);
		}
		
		/**
		 * Get data message yang belum dibaca, count saja
		 */
		respListAgendaVO.setJumlahMessageUnread(inboxMapper.countResponInboxUnReadByUser(user.getId()));
		
		LOG.info("findAgendaByUser done with param : " + " respListAgendaVO: " + respListAgendaVO);
		return respListAgendaVO;		
	}
	
}
