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

import com.jakarta.software.web.data.ReqListKelasData;
import com.jakarta.software.web.data.RespListKelasVO;
import com.jakarta.software.web.data.RespLoginVO;
import com.jakarta.software.web.entity.Kelas;
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
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	public String execute() {	
		
		return INPUT;		
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
		ReqListKelasData reqListKelasData = new ReqListKelasData();
		reqListKelasData.setKodeSekolah(loginData.getKodeSekolah());
		reqListKelasData.setNoInduk(loginData.getNoInduk());
		reqListKelasData.setUserType(loginData.getUserType());
		reqListKelasData.setPassword(loginData.getPassword());
		List<Kelas> listClass = new ArrayList<Kelas>();
		try{
			listClass = kelasService.findAllKelasTeacherEngine(reqListKelasData);
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
		return listClass;
	}
		
}
