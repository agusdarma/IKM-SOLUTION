package com.jakarta.software.web.action.security;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jakarta.software.web.action.BaseAction;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.AppsTimeService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.PasswordService;

public class ChangePasswordAction extends BaseAction implements ModuleCheckable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ChangePasswordAction.class);
	
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	private WebResultVO wrv;
	private String json;
	private String message;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private AppsTimeService timeService;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	public String execute() {
		setMessage(getFlashMessage());
		return INPUT;
	}
	
	public String forceChgPassword(){
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		LOG.debug("Processing -> Force change password for {}", loginVO.getUserCode());
		return INPUT;
	}
	
	public String process() {
		LOG.debug("Processing -> Change Password..");
		try {
			Locale language=(Locale) session.get(WEB_LOCALE_KEY);
			UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
			// validate password
			if (StringUtils.isEmpty(oldPassword)) {
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {getText("l.oldPassword")});
			}
			if (StringUtils.isEmpty(newPassword)) {
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {getText("l.newPassword")});
			}
			if (!newPassword.equals(confirmPassword)) {
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_DIFFERENT);
			}
			if (oldPassword.equals(newPassword)) {
				throw new MmbsWebException(MmbsWebException.NE_PASSWORD_NEW_OLD_SAME);
			}
			
			wrv = passwordService.changePassword(loginVO.getId(), oldPassword, newPassword, language);
			loginVO.setPassChangedOn(timeService.getCurrentTime());
			loginVO.setNeedChangePass(WebConstants.NO_NEED_CHANGE_PASS);
			session.put(LOGIN_KEY, loginVO);
			setFlashMessage(wrv.getMessage());
		} catch(MmbsWebException mwe) {
			wrv = handleJsonException(mwe);
		} catch (Exception e) {
			wrv = handleJsonException(e);
		}
		Gson gson = new Gson();
		json = gson.toJson(wrv);
		return "json";
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_SECURITY_CHANGE_PASSWORD;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
