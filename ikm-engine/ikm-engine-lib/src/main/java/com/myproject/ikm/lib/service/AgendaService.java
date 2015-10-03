package com.myproject.ikm.lib.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ikm.lib.data.AgendaHeaderVO;
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
//		List<AgendaVO> listAgendaVo = agendaMapper.findAgendaByUser(reqListAgendaData.getKodeSekolah(), reqListAgendaData.getNoInduk(), reqListAgendaData.getUserType(), reqListAgendaData.getAgendaType());
		List<AgendaHeaderVO> agendaHeaderVO = agendaMapper.findAgendaByUserNew(reqListAgendaData.getKodeSekolah()
				, reqListAgendaData.getNoInduk(), reqListAgendaData.getUserType(), reqListAgendaData.getAgendaType(),user.getId());

		if(agendaHeaderVO.size()>0){
			respListAgendaVO.setListAgendaHeaderVO(agendaHeaderVO);
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
			
		Calendar calendarNow = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendarNow.setTime(timeService.getCurrentTime());   // assigns calendar to given date 
		
		Agenda agenda = new Agenda();
		agenda.setCreatedOn(calendarNow.getTime());
		agenda.setUpdatedOn(calendarNow.getTime());
		agenda.setCreatedBy(Integer.toString(user.getId()));
		agenda.setUpdatedBy(Integer.toString(user.getId()));
		agenda.setKodeKelas(reqAddAgendaData.getKodeKelas());
		agenda.setNamaKelas(reqAddAgendaData.getNamaKelas());
		agenda.setKodeSekolah(user.getKodeSekolah());
		agenda.setNamaSekolah(reqAddAgendaData.getNamaSekolah());
		agenda.setAgendaType(reqAddAgendaData.getAgendaType());
		agenda.setSubject(reqAddAgendaData.getSubject());
		Calendar calendarTanggalAgenda = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendarTanggalAgenda.setTime(reqAddAgendaData.getTanggalAgenda());   // assigns calendar to given date 
		calendarTanggalAgenda.set(Calendar.HOUR_OF_DAY, calendarNow.get(Calendar.HOUR_OF_DAY));
		calendarTanggalAgenda.set(Calendar.MINUTE, calendarNow.get(Calendar.MINUTE));
		calendarTanggalAgenda.set(Calendar.SECOND, calendarNow.get(Calendar.SECOND));
		agenda.setTanggalAgenda(calendarTanggalAgenda.getTime());
		agenda.setIsiAgenda(reqAddAgendaData.getIsiAgenda());
		LOG.info("add agenda with param : " + agenda);	
		
		agendaMapper.addAgenda(agenda);
		
		LOG.info("addAgenda done with param : " + " reqAddAgendaData: " + reqAddAgendaData);	
	}
	
}
