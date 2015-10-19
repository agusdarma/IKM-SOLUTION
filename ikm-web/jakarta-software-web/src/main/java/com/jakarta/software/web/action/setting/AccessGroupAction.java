package com.jakarta.software.web.action.setting;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.AccessGroupHeaderVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.AccessGroupParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.entity.AccessGroupHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.AccessGroupService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.utils.CommonUtils;

public class AccessGroupAction extends BaseListAction implements ModuleCheckable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AccessGroupAction.class);
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private AccessGroupService accessGroupService;
	
	private AccessGroupHeader accessGroup;
	private List<AccessGroupHeaderVO> listAccessGroup;
	private List<String> modulesSelected;
	
	private int accessId;
	private WebResultVO wrv;
	private String message;
	private String json;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}
	
	// INITIATION AND PROCESS AREA
	public String execute() {
		session.remove(WEB_CONTENT_KEY);
		setMessage(getFlashMessage());
		return SEARCH;
	}

	public String gotoInput() {
		session.remove(WEB_CONTENT_KEY);
		return INPUT;
	}
	
	public String gotoSearch() {
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
	}
	
	public String processSearch() {
		makeTableContent();
		return "searchJson";
	}
	
	public String detail() {
		getLogger().debug("Processing -> detail()");
		// called when user needs to edit, to display input form
		try {
			accessGroup = accessGroupService.findAccessById(accessId);
			session.put(WEB_CONTENT_KEY, accessGroup);
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}
	
	public String processList(){
		getLogger().debug("Processing -> processList()");
		try {
			UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
			Locale language=(Locale) session.get(WEB_LOCALE_KEY);
			accessGroupService.saveAccessData(accessGroup, modulesSelected, loginVO, language);
			session.put(WEB_CONTENT_KEY, accessGroup);
			this.addActionMessage(getText("pages.acecssgroupeditor.successupdate"));
			accessGroup = new AccessGroupHeader();
			return SEARCH;
		} catch (MmbsWebException use) {
			handleException(use);
		} catch (Exception e) {
			handleException(e);
		}
		json = gson.toJson(wrv);
		return "inputJson";
	}
	
	public String processInput(){
		getLogger().debug("Processing -> processInput()");
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		try {
			wrv = accessGroupService.saveAccessData(accessGroup, modulesSelected, loginVO, language);
			if (wrv.getType() == WebConstants.TYPE_UPDATE) {
				setFlashMessage(wrv.getMessage());
			}
		} catch (MmbsWebException mwe) {
			wrv = handleJsonException(mwe);
		} catch (Exception e) {
			wrv = handleJsonException(e);
		}
		json = gson.toJson(wrv);
		return "inputJson";
	}
	
	public String finish(){
		addActionMessage(message);
		accessGroup = new AccessGroupHeader();
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
	}

	// SPAWN LEVEL AND CHECKBOX
	public boolean isModulesSelected(String trxCode) {
		if (modulesSelected != null && modulesSelected.size() > 0) {
			for (String s : modulesSelected)
				if (CommonUtils.compareEqual(s, trxCode))
					return true;
		} else {
			accessGroup = getAccessGroup();
			if (accessGroup == null)
				return false;
			for (Lookup l : accessGroup.getListDetail()) {
				if (CommonUtils.compareEqual(l.getLookupValue(), trxCode))
					return true;
			}
		}
		return false;
	}
	
	public List<Lookup> getListTransactions() {
		return lookupService.findLookupByCat(LookupService.CAT_SERVICE_TYPE);
	}

	@Override
	public int getMenuId() {
		return WebModules.MENU_SETTING_ACCESS_GROUP;
	}

	@Override
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new AccessGroupParamVO();
		return paramVO;
	}
	
	public AccessGroupHeader getAccessGroup() {
		if (accessGroup == null) {
			accessGroup = new AccessGroupHeader();
		}
		return accessGroup;
	}

	public void setAccessGroup(AccessGroupHeader accessGroup) {
		this.accessGroup = accessGroup;
	}

	public int getAccessId() {
		return accessId;
	}

	public void setAccessId(int accessId) {
		this.accessId = accessId;
	}

	public List<AccessGroupHeaderVO> getListAccessGroup() {
		return listAccessGroup;
	}

	public void setListAccessGroup(List<AccessGroupHeaderVO> listAccessGroup) {
		this.listAccessGroup = listAccessGroup;
	}

	public List<String> getModulesSelected() {
		return modulesSelected;
	}

	public void setModulesSelected(List<String> modulesSelected) {
		this.modulesSelected = modulesSelected;
	}

	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{	
		prepareParamVO(new AccessGroupParamVO(),  WEB_PARAM_KEY + WebModules.MENU_SETTING_ACCESS_GROUP, 
				"ah.id", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.accessName"),
				getText("l.status")};
		String[] arrayBody={"accessName", "accessStatusDisplay"};
		String[] arrayDbVariable={"ah.access_name", "ah.access_status"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("AccessGroup!detail.web", "accessName", new String[]{"accessId"}, new String[]{"id"}));
		
		try {
			AccessGroupParamVO accessParamVO = (AccessGroupParamVO) paramVO;
			int totalRow = accessGroupService.countByParam(accessParamVO);
			listAccessGroup = accessGroupService.findListAccessByParam(accessParamVO);		
			Locale language = (Locale) session.get(WEB_LOCALE_KEY);
			resultSearchJson = webSearchResultService.composeSearchResult(getText("l.listAccessGroup"), arrayHeader, arrayBody, 
					arrayDbVariable, gson.toJson(listAccessGroup), getCurrentPage(), 
					totalRow, listLinkTable, language, listAccessGroup.size(), accessParamVO);
		} catch (MmbsWebException we) {
			LOG.error("Error while make table content: ", we);
		}
		
	}

}
