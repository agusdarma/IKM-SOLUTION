package com.jakarta.software.web.action.supervisor;

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
import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.UserLevel;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.UserDataService;
import com.jakarta.software.web.service.UserLevelService;

public class AuthUserDataAction extends BaseListAction implements ModuleCheckable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AuthUserDataAction.class);

	@Autowired
	private UserLevelService userLevelService;

	@Autowired
	private UserDataService userDataService;

	private UserDataVO userDataVO;
	private UserDataVO userDataCurrent;
	private List<UserDataVO> listUser;
	private WebResultVO wrv;

	@SuppressWarnings("unused")
	private String tableId; // table id, used in JMesa
	private boolean isExport;
	private int userId;
	private String confirmPassword;
	private String message;
	private String json;
	private String mode;
	private String resultMessage;

	@Override
	protected Logger getLogger() {
		return LOG;
	}

	// SAVE & INITIALIZATION
	public String execute() {
		setMessage(getFlashMessage());
		return SEARCH;
	}

	public String processAuthorize() {
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		userDataVO =(UserDataVO) session.get(WEB_CONTENT_KEY);
		LOG.debug("mode = " + mode + ", userData : " + userDataVO);// mode	// 1.approve // 2.reject
		userDataVO.setAuthStatus(mode);
		try {
				wrv = userDataService.authorizeUserData(userDataVO, loginVO, language);
				if(wrv.getType()==WebConstants.TYPE_UPDATE)
				{
					setFlashMessage(wrv.getMessage());
				}
			} catch (Exception e) {
				wrv = handleJsonException(e);
			}
		
		Gson gson = new Gson();
		json = gson.toJson(wrv);
		return "json";
	}

	public String gotoInput() {
		return INPUT;
	}

	public String processSearch() {
		makeTableContent();
		return "searchJson";
	}
	
	public String gotoSearch(){
		return SEARCH;
	}

	public String detail() {
		getLogger().debug("Processing -> detail()");
		// called when user needs to edit, to display input form
		try {
			userDataVO = userDataService.findUserByIdHistory(userId);
			userDataCurrent = userDataService.findCurrentUserById(userDataVO.getUserId());
			session.put(WEB_CONTENT_KEY, userDataVO);
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}

	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}


	// GET DROPDOWNLIST CONTENT
	public List<UserLevel> getListUserLevel() {
		List<UserLevel> listUserLevel = userLevelService.getAllUserLevel();
		return listUserLevel;
	}

	@Override
	public int getMenuId() {
		return WebModules.MENU_SUPERVISOR_AUTH_USER_DATA;
	}

	// SETTER GETTER AREA
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public boolean isExport() {
		return isExport;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserDataVO getUserDataVO() {
		return userDataVO;
	}

	public void setUserDataVO(UserDataVO userDataVO) {
		this.userDataVO = userDataVO;
	}

	public List<UserDataVO> getListUser() {
		if (listUser == null)
			listUser = new ArrayList<UserDataVO>();
		return listUser;
	}

	public void setListUser(List<UserDataVO> listUser) {
		this.listUser = listUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	
	public UserDataVO getUserDataCurrent() {
		return userDataCurrent;
	}

	public void setUserDataCurrent(UserDataVO userDataCurrent) {
		this.userDataCurrent = userDataCurrent;
	}


	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		prepareParamVO(new UserDataParamVO(), WEB_PARAM_KEY + WebModules.MENU_SUPERVISOR_AUTH_USER_DATA, 
				"udh.id", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.userCode"), getText("l.userName"), getText("l.userLevel"), getText("l.requestType")};
		String[] arrayBody={"userCode", "userName", "userLevelDisplay", "activityType"};
		String[] arrayDbVariable={"udh.user_code", "udh.user_name", "ul.level_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("AuthUserData!detail.web", "userCode", new String[]{"userId"}, new String[]{"id"}));//jadiin constructor entity
		
		UserDataParamVO userDataParamVO = (UserDataParamVO) paramVO;
		userDataParamVO.setUpdatedBy(loginVO.getId());
		userDataParamVO.setBranchId(loginVO.getBranchId());
		int totalRow =  userDataService.countUserHistoryByParam(userDataParamVO);
		listUser = userDataService.findUserHistoryByParam(userDataParamVO);		

		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listUser"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listUser), getCurrentPage(), 
				totalRow, listLinkTable, language, listUser.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new UserDataParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/
	
}
