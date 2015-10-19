// PACKAGE

package com.jakarta.software.web.action.security;


// IMPORT

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.action.BaseAction;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.entity.UserPreference;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.UserDataService;
import com.jakarta.software.web.utils.StringUtils;


// LOGIC

public class UserPreferenceAction extends BaseAction
{
	// INITIALIZATION
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(UserPreferenceAction.class);
	
	@Override
	protected Logger getLogger() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private UserDataService userDataService;
	
	// FROM JSP LAYOUT			
	private UserPreference userPreference;
	private String message;
	
	// CONDITION
	public String execute() 
	{
		UserDataLoginVO userDataLoginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		setUserPreference(userDataLoginVO.getUserPreference());
		return EDIT;
	}
	
	public String update() 
	{
		LOG.debug("User Preference - Update process");
		try 
		{
			UserDataLoginVO userDataLoginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
			userDataService.updateUserPreferenceByID(userDataLoginVO.getId(), userPreference, userDataLoginVO.getUserPreference());
			LOG.debug("User Preference - Update process : User ID " + userDataLoginVO.getId());
			LOG.debug("User Preference - Update process : User Preference " + userPreference);
			userDataLoginVO.setUserPreference(userPreference);
			session.put(LOGIN_KEY, userDataLoginVO);
			Locale localeID = StringUtils.localeFinder(userDataLoginVO.getUserPreference().getLanguage());
			session.put(WEB_LOCALE_KEY, localeID);
		} 
		catch(MmbsWebException mmbswe) 
		{
			handleException(mmbswe);
			LOG.debug("User Preference - Update failed : Error message " + mmbswe.getMessage());
			LOG.debug("User Preference - Update failed : Error code" + mmbswe.getErrorCode());
		} 
		catch (Exception mmbse) 
		{
			handleException(mmbse);
			LOG.debug("User Preference - Update failed : Error message" + mmbse.getMessage());
		}
		return SUCCESS;
	}

	public String finish()
	{
		addActionMessage(getText("rm.generalMessage", new String[]{getText("t.userPreference"), getText("l.updated")}));
		return FINISH;
	}
	
	// SETTER
	public void setUserPreference(UserPreference userPreference) 
	{
		this.userPreference = userPreference;
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}
	
	// GETTER
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}
	
	public static Logger getLog() 
	{
		return LOG;
	}
	
	public UserPreference getUserPreference() 
	{
		return userPreference;
	}

	public String getMessage() 
	{
		return message;
	}
}