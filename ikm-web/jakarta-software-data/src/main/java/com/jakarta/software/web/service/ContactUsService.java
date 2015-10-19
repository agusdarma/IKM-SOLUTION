package com.jakarta.software.web.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jakarta.software.web.data.ContactUsData;

@Service
public class ContactUsService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ContactUsService.class);
	
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private BizMessageService messageService;
	
	private String defaultTo = "sales@jakartasoftware.com";
	
	public void sendingEmail(ContactUsData contactUsData) throws MmbsWebException{	
		if(StringUtils.isEmpty(contactUsData.getName())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.name")});
		}
		if(StringUtils.isEmpty(contactUsData.getSubject())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.subject")});
		}
		if(StringUtils.isEmpty(contactUsData.getMessage())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.message")});
		}
		if (!StringUtils.isEmpty(contactUsData.getEmail())) 
		{
			if (!contactUsData.getEmail().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) 
			{
				throw new MmbsWebException(MmbsWebException.NE_INVALID_EMAIL_FORMAT, 
						new String[] { messageService.getMessageFor("l.email") });
			}
		}
			if(!emailSenderService.sendSimpleMail(contactUsData.getEmail(), defaultTo, contactUsData.getSubject(), contactUsData.getMessage())){
				LOGGER.warn("Sending email failed: [{}]", new String[] {contactUsData.getEmail()});
				throw new MmbsWebException(MmbsWebException.NE_SENDING_EMAIL_FAILED);	
			}
	}

	public String getDefaultTo() {
		return defaultTo;
	}

	public void setDefaultTo(String defaultTo) {
		this.defaultTo = defaultTo;
	}

	
}
