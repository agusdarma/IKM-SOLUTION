package com.myproject.parking.lib.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class EmailSender {

	private static final Logger LOG = LoggerFactory
			.getLogger(EmailSender.class);

	private String smtpHost;
	private int smtpPort;
	private String smtpUserName;
	private String smtpPassword;
	private boolean useTLS;
	public boolean useSSL = false;

	private String defaultFrom;
	private String htmlTemplate;


	// TODO: needs to refactor n merge with IVAS EmailService
	// private MailMessage mailMessage;

	public boolean sendSimpleMail(String from, String to, String subject,
			String message) {
		return sendSimpleMail(from, new String[] { to }, subject, message);
	}

	public boolean sendSimpleMail(String from, String[] to, String subject,
			String message) {
		if (useSSL) {
			return sendSSLMail(from, to, subject, message);
		}
		if (StringUtils.isEmpty(from))
			from = defaultFrom;
		LOG.debug("SendSimpleMail, From: " + from + ", To: "
				+ Arrays.toString(to) + ", Subject: " + subject + ", Message: "
				+ message);

		try {
			Email email = new SimpleEmail();
			email.setHostName(smtpHost);
			email.setSmtpPort(smtpPort);
			if (!StringUtils.isEmpty(smtpUserName)) {
				email.setAuthenticator(new DefaultAuthenticator(smtpUserName,
						smtpPassword));
				email.setTLS(useTLS);
			}
			// check if from contains name in format name <email>
			if (from.indexOf("<") > 0 && from.indexOf(">") > from.indexOf("<")) {
				int idxStart = from.indexOf("<");
				int idxEnd = from.indexOf(">");
				String fromName = from.substring(0, idxStart);
				String fromEmail = from.substring(idxStart + 1, idxEnd);
				LOG.debug("Name: [" + fromName + "], Email: [" + fromEmail
						+ "]");
				email.setFrom(fromEmail, fromName);
			} else {
				email.setFrom(from);
			}
			email.setSubject(subject);
			email.setMsg(message);
			for (String sTo : to)
				email.addTo(sTo);
			email.send();

			LOG.debug("Sending email to " + Arrays.toString(to)
					+ " is successfull");
			return true;
		} catch (Exception e) {
			LOG.warn("Unable to sendSimpleMail: " + e, e);
			return false;
		}
	}

	public boolean sendHtmlMail(String from, String to, String subject,
			String htmlMessage) {
		return sendHtmlMail(from, new String[] { to }, subject, htmlMessage);
	}

	public boolean sendHtmlMail(String from, String[] to, String subject,
			String htmlMessage) {
		if (StringUtils.isEmpty(from))
			from = defaultFrom;
		LOG.debug("sendHtmlMail, From: " + from + ", To: "
				+ Arrays.toString(to) + ", Subject: " + subject + ", Message: "
				+ htmlMessage);
		try {
			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpHost);
			email.setSmtpPort(smtpPort);
			if (!StringUtils.isEmpty(smtpUserName)) {
				email.setAuthenticator(new DefaultAuthenticator(smtpUserName,
						smtpPassword));
				email.setTLS(useTLS);
			}
			for (String sTo : to)
				email.addTo(sTo);
			email.setFrom(from);
			email.setSubject(subject);

			// set the html message
			if (htmlTemplate == null)
				htmlTemplate = "{0}";
			String msg = MessageFormat.format(htmlTemplate, htmlMessage);
			email.setHtmlMsg(msg);

			// set the alternative message
			String plainMsg = msg.replaceAll("</?\\w+ ?/?>", "");
			LOG.debug("Plain: " + plainMsg);
			email.setTextMsg(plainMsg);

			// send the email
			email.send();
			LOG.debug("Sending email to " + Arrays.toString(to)
					+ " is successfull");
			return true;
		} catch (Exception e) {
			LOG.warn("Unable to sendHtmlMail: " + e, e);
			return false;
		}
	}

	public void sendHTMLMailNew(String fromEmail, String fromName,String[] toEmail, String[] toName, String subject, String html,
			String alternateText, Resource[] resources) throws EmailException, IOException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(smtpHost);
		if(StringUtils.isEmpty(fromEmail)){
			fromEmail = defaultFrom;
		}
		if(StringUtils.isEmpty(fromName)){
			fromName = "Account "+Constants.APP_NAME;
		}
		email.setFrom(fromEmail, fromName);
		for (int i = 0; i < toEmail.length; i++) {
			email.addTo(toEmail[i], toName[i]);
		}
		if (smtpUserName != null) {
			email.setAuthentication(smtpUserName, smtpPassword);
		}
		if (resources != null) {
			int i = 0;
			for (Resource resource : resources) {
				String id = email.embed(resource.getURL(), resource.getFilename());
				html = html.replaceAll("\\$\\{" + i++ + "\\}", "cid:" + id);
			}
		}
		email.setTLS(useTLS);
		email.setSubject(subject);
		email.setHtmlMsg(html);
		email.setTextMsg(alternateText);
		email.send();
	}

	public boolean sendSSLMail(String from, String[] to, String subject,
			String content) {
		if (StringUtils.isEmpty(from))
			from = defaultFrom;
		LOG.debug("SendSSLMail, From: " + from + ", To: " + Arrays.toString(to)
				+ ", Subject: " + subject + ", Message: " + content);
		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtps");
			props.put("mail.smtps.host", smtpHost);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtp.ssl.trust", "*");
			props.put("mail.smtp.ssl.checkserveridentity", "false");

			Session mailSession = Session.getDefaultInstance(props);
			mailSession.setDebug(true);
			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);
			String msg = MessageFormat.format(htmlTemplate, content);
			LOG.info("msg before sending :" + msg);
			message.setContent(msg, "text/html");

			for (String sTo : to)
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(sTo));

			transport.connect(smtpHost, smtpPort, smtpUserName, smtpPassword);

			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
			transport.close();
			LOG.debug("Sending email to " + Arrays.toString(to)
					+ " is successfull");
			return true;
		} catch (Exception e) {
			LOG.warn("Unable to sendSSLMail: " + e, e);
			return false;
		}
	}

	public void setDefaultFrom(String defaultFrom) {
		this.defaultFrom = defaultFrom;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public void setUseTLS(boolean useTLS) {
		this.useTLS = useTLS;
	}

	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}

	public void setHtmlTemplate(String htmlTemplate) {
		this.htmlTemplate = htmlTemplate;
	}


}
