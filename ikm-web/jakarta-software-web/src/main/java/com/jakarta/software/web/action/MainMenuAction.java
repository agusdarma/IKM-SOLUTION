package com.jakarta.software.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jakarta.software.web.data.RespLoginVO;


public class MainMenuAction extends BaseAction implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MainMenuAction.class);
	
	private HttpServletRequest httpRequest;
	
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
		
}
