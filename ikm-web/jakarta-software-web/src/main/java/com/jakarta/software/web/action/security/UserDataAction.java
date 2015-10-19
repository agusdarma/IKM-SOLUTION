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
import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.BranchData;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.UserData;
import com.jakarta.software.web.entity.UserLevel;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.BranchDataService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.SettingService;
import com.jakarta.software.web.service.UserDataService;
import com.jakarta.software.web.service.UserLevelService;

public class UserDataAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(UserDataAction.class);
	
	@Autowired
	private UserLevelService userLevelService;
	
	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private BranchDataService branchDataService;
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private SettingService settingService;
	
	private UserData userData;
	private List<UserDataVO> listUser;
	private WebResultVO wrv;
	
	@SuppressWarnings("unused")
	private String tableId; // table id, used in JMesa
	private int userId;
	private String confirmPassword;
	private String message;
	private String json;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

//SAVE & INITIALIZATION
	public String execute() {
		setMessage(getFlashMessage());
		return SEARCH;
	}

	public String gotoSearch(){
		return SEARCH;
	}
	
	public String processInput(){
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		if(userData.getId() > 0 )
		{
			UserData sessionUserData = (UserData) session.get(WEB_CONTENT_KEY);
			sessionUserData.setUserName(userData.getUserName());
			sessionUserData.setDepartment(userData.getDepartment());
			sessionUserData.setBranchId(userData.getBranchId());
			sessionUserData.setUserStatus(userData.getUserStatus());
			sessionUserData.setLevelId(userData.getLevelId());
			sessionUserData.setEmail(userData.getEmail());
			userData=sessionUserData;
			userData.setActivityType(WebConstants.ACT_TYPE_UPDATE);
		}
		else
		{
			userData.setActivityType(WebConstants.ACT_TYPE_REGISTER);
		}
		try {
			wrv = userDataService.insertOrUpdateUserDataHistory(userData, loginVO, confirmPassword, language);
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

	public String finish(){
		addActionMessage(message);
		return SEARCH;
	}
	
	public String gotoInput() {
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
			userData = userDataService.findUserById(userId);
			session.put(WEB_CONTENT_KEY, userData);
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}
	
	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}

//GET DROPDOWNLIST CONTENT
	public List<UserLevel> getListUserLevel() {
		List<UserLevel> listUserLevel = userLevelService.getAllUserLevel();
		return listUserLevel;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_SECURITY_USER_DATA;
	}

// SETTER GETTER AREA
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserData getUserData() {
		if(userData==null)
			userData=new UserData();
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public List<UserDataVO> getListUser() {
		if(listUser==null)
			listUser = new ArrayList<UserDataVO>();
		return listUser;
	}

	public void setListUser(List<UserDataVO> listUser) {
		this.listUser = listUser;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public String getResultSearchJson() {
		return resultSearchJson;
	}

	public void setResultSearchJson(String resultSearchJson) {
		this.resultSearchJson = resultSearchJson;
	}

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}
	
	// list status
	public List<Lookup> getListUserStatus() {
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_LOGIN_USER);
		return listStatus;
	}
	
	// list department
	public List<Lookup> getListDepartment() {
		List<Lookup> listDepartment = lookupService.findLookupByCat(LookupService.CAT_DEPARTMENT);
		return listDepartment;
	}
	
	//list branch
	public List<BranchData> getListBranch(){
		List<BranchData> listBranch=branchDataService.selectBranchData();
		if(listBranch==null)
			listBranch=new ArrayList<BranchData>();
		return listBranch;
	}	
	
	public int getUsePasswordMode()
	{
		if(settingService.getSettingAsInt(SettingService.SETTING_USER_PASSWORD_MODE)==WebConstants.USE_RANDOMIZE_PASS_YES)
		return WebConstants.USE_RANDOMIZE_PASS_YES;
		else
		return WebConstants.USE_RANDOMIZE_PASS_NO;
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new UserDataParamVO(), WEB_PARAM_KEY + WebModules.MENU_SECURITY_USER_DATA, 
				"ud.id", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.userCode"), getText("l.userName"), getText("l.userLevel")};
		String[] arrayBody={"userCode", "userName", "userLevelDisplay"};
		String[] arrayDbVariable={"ud.user_code", "ud.user_name", "ul.level_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("UserData!detail.web", "userCode", new String[]{"userId"}, new String[]{"id"}));
		
		UserDataParamVO userDataParamVO = (UserDataParamVO) paramVO;
		int totalRow = userDataService.countUserByParam(userDataParamVO);
		listUser = userDataService.findUserByParam(userDataParamVO);
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
