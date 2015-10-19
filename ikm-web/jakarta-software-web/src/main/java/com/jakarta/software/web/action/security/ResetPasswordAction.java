package com.jakarta.software.web.action.security;

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
import com.jakarta.software.web.data.CifHistoryVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.UserData;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.PasswordService;
import com.jakarta.software.web.service.SettingService;
import com.jakarta.software.web.service.UserDataService;

public class ResetPasswordAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordAction.class);
	public static final String SESSION_RESET_PASSWORD			= "ses_reset_password";
	
	private UserDataParamVO udParamVO;
	private List<UserDataVO> listUserData;
	private UserDataVO userDataVO;
	private UserData userData;
	private int userId;
	private String confirmPassword;
	
	private WebResultVO wrv;
	private String json;
	private String message;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UserDataService userDataService;
	
	public String execute(){
		setMessage(getFlashMessage());
		return SEARCH;
	}
	
	public String processSearch(){
		getLogger().debug("Processing -> processSearch()");
		makeTableContent();
		return "searchJson";
	}
	
	public String gotoSearch(){
		return SEARCH;
	}
	
	public String gotoInput(){
		return INPUT;
	}
	
	public int getUsePasswordMode()
	{
		if(settingService.getSettingAsInt(SettingService.SETTING_USER_PASSWORD_MODE)==WebConstants.USE_RANDOMIZE_PASS_YES)
		return WebConstants.USE_RANDOMIZE_PASS_YES;
		else
		return WebConstants.USE_RANDOMIZE_PASS_NO;
	}

	public String detail(){
		getLogger().debug("Processing -> detail()");
		try {
			userData = passwordService.findResetPasswordUserByUserId(userId);
			session.put(SESSION_RESET_PASSWORD, userData);
			return INPUT;
		} catch (Exception e) {
			handleException(e);
		}
		return SEARCH;
	}
	
	public String processInput(){
		getLogger().debug("Processing -> processInput()");
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		userData = (UserData) session.get(SESSION_RESET_PASSWORD);
		try {
			/*wrv = passwordService.resetPassword(loginVO, udParamVO, language);*/
			userData.setActivityType(WebConstants.ACT_TYPE_RESET_PASSWORD);
			wrv = userDataService.insertOrUpdateUserDataHistory(userData, loginVO, confirmPassword, language);
			if(wrv.getType()==WebConstants.TYPE_UPDATE)
			{
				setFlashMessage(wrv.getMessage());
			}
		} catch (MmbsWebException mwe) {
			wrv = handleJsonException(mwe);
		} 
		catch (Exception e) {
			wrv = handleJsonException(e);
		}
		Gson gson = new Gson();
		json = gson.toJson(wrv);
		return "json";
	}

	@Override
	public int getMenuId() {
		return WebModules.MENU_SECURITY_RESET_PASSWORD;
	}

	@Override
	protected Logger getLogger() {
		return LOG;
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

	public List<UserDataVO> getListUserData() {
		return listUserData;
	}

	public void setListUserData(List<UserDataVO> listUserData) {
		this.listUserData = listUserData;
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

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public UserDataParamVO getUdParamVO() {
		return udParamVO;
	}

	public void setUdParamVO(UserDataParamVO udParamVO) {
		this.udParamVO = udParamVO;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new UserDataParamVO(), WEB_PARAM_KEY + WebModules.MENU_SECURITY_RESET_PASSWORD, 
				"ud.user_code", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.userCode"), getText("l.name"), getText("l.branch"), 
				getText("l.department"), getText("l.userLevel"), getText("l.userStatus"), getText("l.lastLoginDate"),
				getText("l.createdOn"), getText("l.createdBy"), getText("l.updatedOn"), getText("l.updatedBy")};
		String[] arrayBody={"userCode", "userName", "branchDisplay", "department",
				"userLevelDisplay", "userStatusDisplay", "lastLoginOn", "createdOn", "createdByDisplay",
				"updatedOn", "updatedByDisplay"};		
		String[] arrayDbVariable={"ud.user_code", "ud.user_name", "bd.branch_name", "ud.department",
				"ul.level_name", "l.lookup_desc", "ud.last_login_on", "ud.created_on", "ud1.user_name",
				"ud.updated_on", "ud2.user_name"};

		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("ResetPassword!detail.web", "userCode", new String[]{"userId"}, new String[]{"id"}));
		
		UserDataParamVO userDataParamVO = (UserDataParamVO) paramVO;
		int totalRow=0;
		try {
			totalRow = passwordService.countResetPasswordUserByParam(userDataParamVO);
			listUserData = passwordService.findResetPasswordUserByParam(userDataParamVO);
		} catch (MmbsWebException e) {
		}	
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listUser"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listUserData), getCurrentPage(), 
				totalRow, listLinkTable, language, listUserData.size(), paramVO);
		
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
