package com.jakarta.software.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.data.MessageVO;
import com.jakarta.software.web.data.ReqAddAgendaData;
import com.jakarta.software.web.data.RespListKelasVO;
import com.jakarta.software.web.data.RespLoginVO;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.entity.Kelas;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.Subject;
import com.jakarta.software.web.service.AgendaService;
import com.jakarta.software.web.service.KelasService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MainMenuService;
import com.jakarta.software.web.service.MmbsWebException;


public class AddAgendaAction extends BaseAction implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AddAgendaAction.class);
	
	private HttpServletRequest httpRequest;
	
	@Autowired
	private MainMenuService mainMenuService;
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private KelasService kelasService;
	
	@Autowired
	private AgendaService agendaService;
	
	private List<Subject> listSubjects;
	private String message;
	private ReqAddAgendaData reqAddAgendaData;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	public String execute() {	
		message = "";
		return INPUT;		
	}
	
	public String add() {
		if(reqAddAgendaData==null) return INPUT;			
		LOG.debug("Add: " + reqAddAgendaData);
		try {
			RespLoginVO webLoginData = (RespLoginVO) session.get(LOGIN_KEY);
			MessageVO messageVO = agendaService.addAgenda(reqAddAgendaData, webLoginData);
			message = messageVO.getMessageRc();
			return SUCCESS;
		} catch (MmbsWebException mwe) {
			WebResultVO wrv = handleJsonException(mwe);
			setMessage(wrv.getMessage());
		} catch (JsonGenerationException e) {
			setMessage("JsonGenerationException : " + e);
		} catch (JsonMappingException e) {
			setMessage("JsonMappingException : " + e);
		} catch (IOException e) {
			setMessage("IOException : " + e);
		} catch (Exception e) {
			WebResultVO wrv = handleJsonException(e);
			setMessage(wrv.getMessage());
		}		
		addActionError(message);
		return INPUT;
	}
	
	public String finish(){
		addActionMessage(message);
		return SEARCH;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)  {
		this.httpRequest = request;
	}

	public RespLoginVO getLoginData() {
		RespLoginVO loginData = (RespLoginVO) session.get(LOGIN_KEY);
		return loginData;
	}
	
	public List<Kelas> getListKelas() {
		RespLoginVO loginData = (RespLoginVO) session.get(LOGIN_KEY);		
		List<Kelas> listKelas = new ArrayList<Kelas>();
		List<Subject> listSubjects = new ArrayList<Subject>();
		setListSubjects(listSubjects);
		try{
			RespListKelasVO respListKelasVO = new RespListKelasVO();
			respListKelasVO = kelasService.findAllKelasTeacherEngine(loginData);
			listKelas.addAll(respListKelasVO.getListKelas());
			setListSubjects(respListKelasVO.getListSubjects());
		} catch (MmbsWebException mwe) {
			LOG.error("MmbsWebException : " + mwe);
		} catch (JsonGenerationException e) {
			LOG.error("JsonGenerationException : " + e);
		} catch (JsonMappingException e) {
			LOG.error("JsonMappingException : " + e);
		} catch (IOException e) {
			LOG.error("IOException : " + e);
		} catch (Exception e) {
			LOG.error("Exception : " + e);
		}
		return listKelas;
	}

	public List<Subject> getListSubjects() {
		return listSubjects;
	}

	public void setListSubjects(List<Subject> listSubjects) {
		this.listSubjects = listSubjects;
	}
	
	public List<Lookup> getListAgendaType() {
		List<Lookup> listAgendaType = lookupService.findLookupByCat(LookupService.CAT_AGENDA_TYPE);
		return listAgendaType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ReqAddAgendaData getReqAddAgendaData() {
		return reqAddAgendaData;
	}

	public void setReqAddAgendaData(ReqAddAgendaData reqAddAgendaData) {
		this.reqAddAgendaData = reqAddAgendaData;
	}
		
}
