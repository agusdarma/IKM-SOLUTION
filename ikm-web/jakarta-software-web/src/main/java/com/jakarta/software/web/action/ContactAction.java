package com.jakarta.software.web.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.data.ContactUsData;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.service.BizMessageService;
import com.jakarta.software.web.service.ContactUsService;
import com.jakarta.software.web.service.MmbsWebException;


public class ContactAction extends BaseAction implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ContactAction.class);
	
	private HttpServletRequest httpRequest;
	private ContactUsData contactUsData;
	private String message;
	@Autowired
	private ContactUsService contactUsService;
	@Autowired
	private BizMessageService messageService;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	public String execute() {
		clearActionErrors();
		clearMessages();
		message = "";
		return INPUT;
	}
	
	public String process() {
		LOG.debug("ContactUsData: " + contactUsData.toString());
		try {
			contactUsService.sendingEmail(contactUsData);
			setMessage(messageService.getMessageFor("rm.sendEmailSuccess",
					new String[] { contactUsData.getEmail()}, new Locale("en", "US")));
			addActionMessage(message);
			contactUsData = null;
			return SUCCESS;
		} catch (MmbsWebException mwe) {
			WebResultVO wrv = handleJsonException(mwe);
			setMessage(wrv.getMessage());
			addActionError(message);
		} catch (Exception e) {
			WebResultVO wrv = handleJsonException(e);
			setMessage(wrv.getMessage());
			addActionError(message);
		}
		return INPUT;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)  {
		this.httpRequest = request;
	}

	public ContactUsData getContactUsData() {
		if(contactUsData == null){
			contactUsData = new ContactUsData();
		}
		return contactUsData;
	}

	public void setContactUsData(ContactUsData contactUsData) {
		this.contactUsData = contactUsData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
		
}
