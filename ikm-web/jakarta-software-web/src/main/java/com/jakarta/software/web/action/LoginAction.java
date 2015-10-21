package com.jakarta.software.web.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.data.RespLoginVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebLoginData;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.mapper.UserDataMapper;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.SecurityService;
import com.jakarta.software.web.utils.StringUtils;


public class LoginAction extends BaseAction implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);
	
	private WebLoginData webLoginData;
	private HttpServletRequest httpRequest;
	private String message;
	private WebResultVO wrv;
	private String json;
	
	@Autowired
	private LookupService lookupService;
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private SecurityService securityService;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	public String execute() {
		message = "";
		session.clear();
		return INPUT;
	}
	
	public String go() {
		if(webLoginData==null) return INPUT;			
		LOG.debug("Login: " + webLoginData);
		try {
			RespLoginVO respLoginVO = securityService.validateUserToEngine(webLoginData);
			Locale localeID = StringUtils.localeFinder(respLoginVO.getUserPreference().getLanguage());
			session.put(LOGIN_KEY, respLoginVO);
			session.put(WEB_LOCALE_KEY, localeID);
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
	
	public String mainMenu() {
		return SUCCESS;
	}
	
	public String logoff() {
		UserDataLoginVO loginVO = (UserDataLoginVO) session.remove(LOGIN_KEY);
		session.clear();
		LOG.debug("Logoff: " + (loginVO == null? "N/A" : loginVO.getUserCode()) );
		session.clear();
		securityService.logoutUser(loginVO);		
		return SUCCESS;
	}
	
	public String expired() {
		this.addActionError(getText("rc." +  MmbsWebException.NE_SESSION_EXPIRED));
		setMessage(getText("rc." +  MmbsWebException.NE_SESSION_EXPIRED));
		return INPUT;
	}
	
	public String invalidModule()
	{
		addActionError(getText("err.invalidUserRole"));
		return SUCCESS;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)  {
		this.httpRequest = request;
	}

	public String goToContact()
	{
		return SUCCESS;  
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public List<Lookup> getListLoginType() {
		List<Lookup> listLoginType = lookupService.findLookupByCat(LookupService.CAT_LOGIN_USER);
		return listLoginType;
	}

	public WebLoginData getWebLoginData() {
		return webLoginData;
	}

	public void setWebLoginData(WebLoginData webLoginData) {
		this.webLoginData = webLoginData;
	}

	
		
}
