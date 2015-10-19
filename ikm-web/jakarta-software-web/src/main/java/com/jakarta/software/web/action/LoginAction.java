package com.jakarta.software.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.data.LoginData;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.mapper.UserDataMapper;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.SecurityService;
import com.jakarta.software.web.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionContext;


public class LoginAction extends BaseAction implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);
	
	private LoginData loginData;
	private HttpServletRequest httpRequest;
	private String message;
	private WebResultVO wrv;
	private String json;
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private SecurityService securityService;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	public LoginData getLoginData() {
		return loginData;
	}
	public void setLoginData(LoginData loginData) {
		this.loginData = loginData;
	}
	
	public String execute() {
		return INPUT;
	}
	
	public String process() {
		if(loginData==null) return INPUT;			
		LOG.debug("Login: " + loginData.toString());
		try {
			session.clear();
			String sessionId = httpRequest.getSession().getId();
			UserDataLoginVO loginVO = securityService.validateUserLogin(loginData, sessionId);
			Locale localeID = StringUtils.localeFinder(loginVO.getUserPreference().getLanguage());
			session.put(LOGIN_KEY, loginVO);
			session.put(WEB_LOCALE_KEY, localeID);
			return SUCCESS;
		} catch (MmbsWebException mwe) {
			WebResultVO wrv = handleJsonException(mwe);
			setMessage(wrv.getMessage());
		} catch (Exception e) {
			WebResultVO wrv = handleJsonException(e);
			setMessage(wrv.getMessage());
		}
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

	public String processLoginSupervisor()
	{
		String sessionId = httpRequest.getSession().getId();
		try {
			UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
			if(loginVO.getUserCode().equalsIgnoreCase(loginData.getUserCode()))
			{
				throw new MmbsWebException(MmbsWebException.NE_SUPERVISOR_LOGIN_SAME);
			}
			UserDataLoginVO loginSpv = securityService.validateForcedAuthLogin(loginData, sessionId, 
					WebModules.MENU_SUPERVISOR_AUTH_CIF, loginVO.getBranchId());
			session.put(LOGIN_KEY_SUPERVISOR, loginSpv);
			wrv = new WebResultVO();
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setType(WebConstants.TYPE_INSERT);			
		} catch (MmbsWebException e) {
			wrv = handleJsonException(e);
		}
		json = gson.toJson(wrv);
		return "spvLogin";
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
		
}
