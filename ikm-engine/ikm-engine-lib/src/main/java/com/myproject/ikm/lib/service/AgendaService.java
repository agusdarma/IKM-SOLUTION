package com.myproject.ikm.lib.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ikm.lib.data.AgendaVO;
import com.myproject.ikm.lib.data.ReqAddAgendaData;
import com.myproject.ikm.lib.data.ReqListAgendaData;
import com.myproject.ikm.lib.data.RespListAgendaVO;
import com.myproject.ikm.lib.entity.Agenda;
import com.myproject.ikm.lib.entity.User;
import com.myproject.ikm.lib.mapper.AgendaMapper;
import com.myproject.ikm.lib.mapper.InboxMapper;
import com.myproject.ikm.lib.mapper.UserDataMapper;
import com.myproject.ikm.lib.utils.CipherUtil;
import com.myproject.ikm.lib.utils.CommonUtil;
import com.myproject.ikm.lib.utils.Constants;

@Service
public class AgendaService {
	private static final Logger LOG = LoggerFactory.getLogger(AgendaService.class);
	
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
		for (AgendaVO agendaVO : listAgendaVo) {
			agendaVO.setTanggalAgendaVal(CommonUtil.displayDateTime(agendaVO.getTanggalAgenda()));
			agendaVO.setCreatedOnVal(CommonUtil.displayDateTime(agendaVO.getCreatedOn()));
		}
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
	
	public void addAgenda(ReqAddAgendaData reqAddAgendaData) throws IkmEngineException {
		LOG.debug("addAgenda with param : " + " reqAddAgendaData: " + reqAddAgendaData);	
		User user = userDataMapper.findUserByKodeSekolahAndNoIndukAndUserType(reqAddAgendaData.getKodeSekolah(), reqAddAgendaData.getNoInduk(),reqAddAgendaData.getUserType());
		if(user == null){
			LOG.error("Can't find User with parameter: " + reqAddAgendaData);
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
		if(Constants.TEACHER == reqAddAgendaData.getUserType()){
			String passwordDB = user.getPassword();
			String passwordInput = CipherUtil.passwordDigest(reqAddAgendaData.getKodeSekolah(), reqAddAgendaData.getPassword());
			if(!passwordDB.equals(passwordInput)){
				throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
			}
		}
		
		
		Date now = timeService.getCurrentTime();
		Agenda agenda = new Agenda();
		agenda.setCreatedOn(now);
		agenda.setUpdatedOn(now);
		agenda.setCreatedBy("SYS");
		agenda.setUpdatedBy("SYS");
		agenda.setKodeKelas(reqAddAgendaData.getKodeKelas());
		agenda.setNamaKelas(reqAddAgendaData.getNamaKelas());
		agenda.setKodeSekolah(user.getKodeSekolah());
		agenda.setNamaSekolah(reqAddAgendaData.getNamaSekolah());
		agenda.setAgendaType(reqAddAgendaData.getAgendaType());
		agenda.setTanggalAgenda(reqAddAgendaData.getTanggalAgenda());
		agenda.setIsiAgenda(reqAddAgendaData.getIsiAgenda());
		LOG.info("add agenda with param : " + agenda);	
		
		agendaMapper.addAgenda(agenda);
		
		LOG.info("addAgenda done with param : " + " reqAddAgendaData: " + reqAddAgendaData);	
	}
	
}
