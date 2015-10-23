package com.jakarta.software.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.data.ReqListAgendaData;
import com.jakarta.software.web.data.RespListAgendaVO;
import com.jakarta.software.web.data.RespLoginVO;
import com.jakarta.software.web.data.WebLoginData;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MainMenuService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.SecurityService;
import com.jakarta.software.web.utils.Constants;


public class MainMenuAction extends BaseAction implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MainMenuAction.class);
	
	private HttpServletRequest httpRequest;
	private ReqListAgendaData reqListAgendaData;
	private RespListAgendaVO respListAgendaVO;
	private int agendaType;
	
	@Autowired
	private MainMenuService mainMenuService;
	
	@Autowired
	private LookupService lookupService;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	public String execute() {	
		agendaType = 1;
		try{
			RespLoginVO loginData = getLoginData();	
			if(reqListAgendaData == null){
				reqListAgendaData = new ReqListAgendaData();
			}
			reqListAgendaData.setPassword(loginData.getPassword());
			reqListAgendaData.setKodeSekolah(loginData.getKodeSekolah());
			reqListAgendaData.setNoInduk(loginData.getNoInduk());
			reqListAgendaData.setUserType(loginData.getUserType());
			reqListAgendaData.setAgendaType(Constants.GENERAL_AGENDA);
			respListAgendaVO =  mainMenuService.getListAgenda(reqListAgendaData);
		} catch (MmbsWebException mwe) {
			WebResultVO wrv = handleJsonException(mwe);
	//		setMessage(wrv.getMessage());
		} catch (JsonGenerationException e) {
	//		setMessage("JsonGenerationException : " + e);
		} catch (JsonMappingException e) {
	//		setMessage("JsonMappingException : " + e);
		} catch (IOException e) {
	//		setMessage("IOException : " + e);
		} catch (Exception e) {
			WebResultVO wrv = handleJsonException(e);
	//		setMessage(wrv.getMessage());
		}		
	//		addActionError(message);
		return INPUT;		
	}
	
	public String refresh() {
		try{
			RespLoginVO loginData = getLoginData();	
			if(reqListAgendaData == null){
				reqListAgendaData = new ReqListAgendaData();
			}
			reqListAgendaData.setPassword(loginData.getPassword());
			reqListAgendaData.setKodeSekolah(loginData.getKodeSekolah());
			reqListAgendaData.setNoInduk(loginData.getNoInduk());
			reqListAgendaData.setUserType(loginData.getUserType());
			reqListAgendaData.setAgendaType(agendaType);
			respListAgendaVO =  mainMenuService.getListAgenda(reqListAgendaData);
		} catch (MmbsWebException mwe) {
			WebResultVO wrv = handleJsonException(mwe);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		} catch (Exception e) {
			WebResultVO wrv = handleJsonException(e);
		}		
		return INPUT;
	}
	
	public List<Lookup> getListAgendaType() {
		List<Lookup> listAgendaType = lookupService.findLookupByCat(LookupService.CAT_AGENDA_TYPE);
		return listAgendaType;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)  {
		this.httpRequest = request;
	}

	public RespLoginVO getLoginData() {
		RespLoginVO loginData = (RespLoginVO) session.get(LOGIN_KEY);
		return loginData;
	}

	public ReqListAgendaData getReqListAgendaData() {		
		return reqListAgendaData;
	}

	public void setReqListAgendaData(ReqListAgendaData reqListAgendaData) {
		this.reqListAgendaData = reqListAgendaData;
	}

	public RespListAgendaVO getRespListAgendaVO() {
		return respListAgendaVO;
	}

	public void setRespListAgendaVO(RespListAgendaVO respListAgendaVO) {
		this.respListAgendaVO = respListAgendaVO;
	}

	public int getAgendaType() {
		return agendaType;
	}

	public void setAgendaType(int agendaType) {
		this.agendaType = agendaType;
	}
		
}
