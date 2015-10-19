package com.jakarta.software.web.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.VersionData;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.CifHistoryParamVO;
import com.jakarta.software.web.data.param.CifParamVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.service.CifHistoryService;
import com.jakarta.software.web.service.CifService;
import com.jakarta.software.web.service.MerchantService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.UserDataService;
import com.jakarta.software.web.utils.Constants;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements SessionAware  {
	private static final long serialVersionUID = 1L;
	
	public static final String LOGIN_KEY			= "LOGIN_KEY";
	public static final String LOGIN_KEY_SUPERVISOR = "LOGIN_KEY_SUPERVISOR";
	public static final String WEB_CONTENT_KEY		= "WEB_CONTENT_KEY";
	public static final String WEB_CONTENT_KEY_2	= "WEB_CONTENT_KEY_2";
	public static final String WEB_CONTENT_CONTACT	= "WEB_CONTENT_CONTACT";
	public static final String WEB_PARAM_KEY		= "WEB_PARAM_KEY";
	public static final String WEB_LOCALE_KEY		= "WW_TRANS_I18N_LOCALE";
	public static final String WEB_FLASH_MESSAGE	= "WEB_FLASH_MESSAGE";
	
	public static final String LIST			= "list";
	public static final String EDIT			= "edit";
	public static final String FINISH		= "finish";
	public static final String SEARCH		= "search";
	public static final String CONFIRM		= "confirm";
	public static final String DETAIL		= "detail";
	public static final String POP_UP		= "pop_up";
	
	//dealer
	public static final String DEALER_BILLPAYMENT = "billPayment";
	public static final String DISTRIBUTOR = "distributor";
	public static final String RETAILER ="retailer";
	
	// result from interceptor
	public static final String SESSION_EXPIRED			= "sessionExpired";
	public static final String MODULE_ACCESS_DENIED		= "moduleAccessDenied";
	public static final String FORCE_CHANGE_PASSWORD	= "forceChangePassword";
			
	//export report
	public static final String PDF = "PDF";
	public static final String XLS = "XLS";
	public static final String CSV = "CSV";
	public static final String HTML = "HTML";	
	
	protected static final Gson gson = new Gson();
	
	protected Map<String, Object> session;
	
	@Autowired
	private VersionData versionData;
	
	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private CifHistoryService cifHistoryService;
	
	protected abstract Logger getLogger();
	private int notifUser;
	private int notifMerchant;
	private int notifCif;
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public void checkNotif(UserDataLoginVO loginVO)
	{
		if(loginVO.getLevelVO().isMenuAllowed(WebModules.MENU_SUPERVISOR_AUTH_USER_DATA))
		{
			UserDataParamVO udParamVO = new UserDataParamVO();
			udParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			udParamVO.setBranchId(loginVO.getBranchId());
			udParamVO.setUpdatedBy(loginVO.getId());
			notifUser = userDataService.countUserHistoryByParam(udParamVO);
		}
		if(loginVO.getLevelVO().isMenuAllowed(WebModules.MENU_SUPERVISOR_AUTH_MERCHANT))
		{
			MerchantParamVO mctParamVO = new MerchantParamVO();
			mctParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			mctParamVO.setBranchId(loginVO.getBranchId());
			mctParamVO.setUpdatedBy(loginVO.getId());
			notifMerchant = merchantService.countMerchantHistoryByParam(mctParamVO);
		}
		if(loginVO.getLevelVO().isMenuAllowed(WebModules.MENU_SUPERVISOR_AUTH_CIF))
		{
			CifHistoryParamVO chParamVO = new CifHistoryParamVO();
			chParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			chParamVO.setBranchId(loginVO.getBranchId());
			chParamVO.setUpdatedBy(loginVO.getId());
			notifCif = cifHistoryService.countCifHistoryByParam(chParamVO);
		}
	}
	
	public void handleException(Exception e) {
		if (e instanceof MmbsWebException) {
			MmbsWebException nwe = (MmbsWebException) e;
			if (nwe.hasInfo())
				this.addActionError(getText("rc." + nwe.getErrorCode(), nwe.getInfo()));
			else
				this.addActionError(getText("rc." + nwe.getErrorCode()));
		} else {
			LOG.warn("Unknown exception: " + e.getMessage(), e);
			this.addActionError(getText("rc." + MmbsWebException.NE_UNKNOWN_ERROR));
		}
	}
	
	protected WebResultVO handleJsonException(Exception e)
	{
		WebResultVO wrv = new WebResultVO();
		if (e instanceof MmbsWebException)
		{
			MmbsWebException nwe = (MmbsWebException) e;
			if (nwe.hasInfo())
			{
				wrv.setMessage(getText("rc." + nwe.getErrorCode(), nwe.getInfo()));
				wrv.setRc(nwe.getErrorCode());
				return wrv;
			}
			else
			{
				wrv.setMessage(getText("rc." + nwe.getErrorCode()));
				wrv.setRc(nwe.getErrorCode());
				return wrv;
			}
		}
		else
		{
			LOG.warn("Unknown exception: " + e.getMessage(), e);
			wrv.setMessage(getText("rc." + MmbsWebException.NE_UNKNOWN_ERROR));
			wrv.setRc(MmbsWebException.NE_UNKNOWN_ERROR);
			return wrv;
		}
	}
	
	protected void setFlashMessage(String message) {
		session.put(WEB_FLASH_MESSAGE, message);
	}
	protected String getFlashMessage() {
		Object o = session.remove(WEB_FLASH_MESSAGE);
		if (o instanceof String)
			return (String) o;
		else
			return null;
	}
	
	public String getVersionInfo() {
		return getText("label.versionInfo", new String[] { versionData.getAppsName(), 
				versionData.getVersion(), versionData.getBuildDate() } );
	}
	
	public String getFormatDate() {
		return Constants.NE_FORMAT_DATE_JSP;  
	}
	
	public String getFormatTime() {
		return Constants.NE_FORMAT_TIME_JSP;  
	}
	
	public int getUserDataNotif()
	{
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		UserDataParamVO udParamVO =new UserDataParamVO();
		udParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
		udParamVO.setBranchId(loginVO.getBranchId());
		udParamVO.setUpdatedBy(loginVO.getId());
		int count = userDataService.countUserHistoryByParam(udParamVO);
		return count; 
	}

	public int getNotifUser() {
		return notifUser;
	}

	public void setNotifUser(int notifUser) {
		this.notifUser = notifUser;
	}

	public int getNotifMerchant() {
		return notifMerchant;
	}

	public void setNotifMerchant(int notifMerchant) {
		this.notifMerchant = notifMerchant;
	}

	public int getNotifCif() {
		return notifCif;
	}

	public void setNotifCif(int notifCif) {
		this.notifCif = notifCif;
	}
	
}
