package com.jakarta.software.web.action.security;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.UserLevelMenuVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.data.param.UserLevelParamVO;
import com.jakarta.software.web.entity.UserLevel;
import com.jakarta.software.web.entity.UserMenu;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.UserDataService;
import com.jakarta.software.web.service.UserLevelService;

public class UserLevelAction extends BaseListAction implements ModuleCheckable 
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(UserLevelAction.class);

	@Autowired
	private UserLevelService userLevelService;

	@Autowired
	private UserDataService userDataService;
	
	private UserDataParamVO addNewUserParamVO;
	private UserLevel userLevel;
	private List<UserLevel> listUserLevel;
	private List<Integer> modulesSelected;

	@SuppressWarnings("unused")
	private String tableId; // table id, used in JMesa
	private boolean isExport;
	private String levelName;
	private int levelId;
	private int tableFlag;
	private WebResultVO wrv;
	private String json;
	private String message;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

	// INITIATION AND PROCESS AREA
	public String execute() {
		setMessage(getFlashMessage());
		return SEARCH;
	}
	
	public String gotoSearch()
	{
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
	}
	
	public String gotoInput() {
		session.remove(WEB_CONTENT_KEY);
		return INPUT;
	}
	
	public String processSearch() {
		makeTableContent();
		return "searchJson";
	}
	
	public String detail() {
		getLogger().debug("Processing -> detail()");
		// called when user needs to edit, to display input form
		try {
			userLevel = userLevelService.findLevelById(levelId);
			session.put(WEB_CONTENT_KEY, userLevel);
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
			userLevelService.saveLevelData(userLevel, modulesSelected, loginVO, language);
			session.put(WEB_CONTENT_KEY, userLevel);
			this.addActionMessage(getText("pages.userleveleditor.successupdate"));
			userLevel= new UserLevel();
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
			wrv=userLevelService.saveLevelData(userLevel,modulesSelected, loginVO, language);
			if(wrv.getType()==WebConstants.TYPE_UPDATE)
			{
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
	
	public String processDelete(){
		getLogger().debug("Processing -> processDelete()");
		int countUsedLevel = userDataService.countLevelUsedByName(userLevel.getId());
		if(countUsedLevel==0){
			userLevelService.deleteByLevelId(userLevel.getId());
			return SUCCESS;
		} else {
			addActionError(getText("err.userleveleditor.deletereject"));
			return LIST;
		}
	}
	
	public String finish(){
		this.addActionMessage(getText("pages.userleveleditor.successdelete"));
		userLevel=new UserLevel();
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
	}

	// END INITIATION AND PROCESS AREA

	// SPAWN LEVEL AND CHECKBOX
	public boolean isModulesSelected(int moduleId) {
		if (modulesSelected != null && modulesSelected.size() > 0) {
			for (int i : modulesSelected)
				if (i == moduleId)
					return true;
		} else {
			userLevel = getUserLevel();
			if (userLevel == null)
				return false;
			for (UserMenu m : userLevel.getListMenu()) {
				if (m.getMenuId() == moduleId)
					return true;
			}
		}
		return false;
	}

	public List<UserLevelMenuVO> getListModule() {
		return userLevelService.findModules();
	}
	// END SPAWN LEVEL AND CHECK BOX

	// JMESA AREA

	
	@Override
	public int getMenuId() {
		return WebModules.MENU_SECURITY_USER_LEVEL;
	}

	// SETTER GETTER AREA
	public UserDataParamVO getAddNewUserParamVO() {
		return addNewUserParamVO;
	}
	public void setAddNewUserParamVO(UserDataParamVO addNewUserParamVO) {
		this.addNewUserParamVO = addNewUserParamVO;
	}
	public UserLevel getUserLevel() {
		if (userLevel == null) {
			userLevel = new UserLevel();
		}
		return userLevel;
	}
	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}
	public List<UserLevel> getListUserLevel() {
		return listUserLevel;
	}
	public void setListUserLevel(List<UserLevel> listUserLevel) {
		this.listUserLevel = listUserLevel;
	}
	public List<Integer> getModulesSelected() {
		return modulesSelected;
	}

	public void setModulesSelected(List<Integer> modulesSelected) {
		this.modulesSelected = modulesSelected;
	}

	public boolean isExport() {
		return isExport;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public int getTableFlag() {
		return tableFlag;
	}
	public void setTableFlag(int tableFlag) {
		this.tableFlag = tableFlag;
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
		
	// END SETTER GETTER AREA
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new UserDataParamVO(), WEB_PARAM_KEY + WebModules.MENU_SECURITY_USER_DATA, 
				"l.id", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.levelName"), getText("l.levelDescription")};
		String[] arrayBody={"levelName", "levelDesc"};
		String[] arrayDbVariable={"l.level_name", "l.level_desc"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("UserLevel!detail.web", "levelName", new String[]{"levelId"}, new String[]{"id"}));
		
		UserLevelParamVO userLevelParamVO = (UserLevelParamVO) paramVO;
		int totalRow = userLevelService.countUserLevelByParam(userLevelParamVO);
		listUserLevel = userLevelService.findListUserLevelByParam(userLevelParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listUserLevel"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listUserLevel), getCurrentPage(), 
				totalRow, listLinkTable, language, listUserLevel.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new UserLevelParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/
}
