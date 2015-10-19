package com.jakarta.software.web.action.setting;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.SystemSettingParamVO;
import com.jakarta.software.web.entity.SystemSetting;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.SettingService;

public class SystemSettingAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SystemSettingAction.class);
	
	private int settingId;
	private String message;
	private SystemSetting systemSetting;
	private List<SystemSetting> listSystemSetting;
	
	private WebResultVO wrv;
	private String json;
	
	@Autowired
	private SettingService settingService;

	public String execute(){
		setMessage(getFlashMessage());
		session.remove(WEB_CONTENT_KEY);
		return LIST;
	}

	public String gotoSearch(){
		return LIST;
	}
	
	public String list(){
		getLogger().debug("Processing -> list()");
		setMessage(getFlashMessage());
		return LIST;
	}
	
	public String edit() {
		getLogger().debug("Processing -> edit()");
		try {
			systemSetting = settingService.getSettingById(settingId);
			session.put(WEB_CONTENT_KEY, systemSetting);
			return EDIT;
		} catch (Exception e) {
			handleException(e);
		}
		return execute();
	}
	
	public String processInput(){
		getLogger().debug("Processing -> processInput()");
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		try {
			wrv = settingService.update(systemSetting, loginVO, language);
			if(wrv.getType() == WebConstants.TYPE_UPDATE)
			{
				setFlashMessage(wrv.getMessage());
			}
		} catch (MmbsWebException mwe) {
			wrv = handleJsonException(mwe);
		} catch (Exception e) {
			wrv = handleJsonException(e);
		}
		Gson gson = new Gson();
		json = gson.toJson(wrv);
		return "inputJson";
	}
	
	public String processList(){
		makeTableContent();
		return "searchJson";
	}
	
	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<SystemSetting> getListSystemSetting() {
		return listSystemSetting;
	}

	public void setListSystemSetting(List<SystemSetting> listSystemSetting) {
		this.listSystemSetting = listSystemSetting;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_SETTING_SYSTEM_SETTING;
	}

	//paging start
	public int getSettingId() {
		return settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
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

	public SystemSetting getSystemSetting() {
		if (systemSetting == null) {
			Object obj = session.get(WEB_CONTENT_KEY);
			if (obj != null && obj instanceof SystemSetting) {
				systemSetting = (SystemSetting) obj;
			}
		}
		return systemSetting;
	}

	public void setSystemSetting(SystemSetting systemSetting) {
		this.systemSetting = systemSetting;
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new SystemSettingParamVO(), WEB_PARAM_KEY + WebModules.MENU_SETTING_SYSTEM_SETTING, 
				"setting_name", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.settingName"), getText("l.settingDesc"), getText("l.valueType"), 
				getText("l.settingValue"), getText("l.updatedOn")};
		String[] arrayBody={"settingName", "settingDesc", "valueType", "settingValue", "updatedOn"};		
		String[] arrayDbVariable={"setting_name", "setting_desc", "setting_value", "value_type",
				"updated_on", "updated_by"};		
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("SystemSetting!edit.web", "settingName", new String[]{"settingId"}, new String[]{"id"}));
		
		SystemSettingParamVO systemSettingParamVO = (SystemSettingParamVO) paramVO;
		int totalRow = settingService.countByParam(systemSettingParamVO);
		try
		{
			listSystemSetting = settingService.findByParam(systemSettingParamVO);
		} catch (Exception e) {
			
		}
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listSystemSetting"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listSystemSetting), getCurrentPage(), 
				totalRow, listLinkTable, language, listSystemSetting.size(), paramVO);
		
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new SystemSettingParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/
	
}
