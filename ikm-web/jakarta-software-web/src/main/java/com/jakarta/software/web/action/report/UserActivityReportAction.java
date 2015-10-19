package com.jakarta.software.web.action.report; 

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.MapVO;
import com.jakarta.software.web.data.UserActivityVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.UserActivityParamVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.entity.UserData;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.UserActivityService;
import com.jakarta.software.web.service.UserDataService;

public class UserActivityReportAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(UserActivityReportAction.class);

	private int key;
	private String message;
	private UserActivity userActivity;
	private List<UserActivity> listUserActivity;
	private List<UserActivityVO> changedAttribute;
	private String json;
	private WebResultVO wrv;
	
	//for exporting
	private String exportType;
	private HashMap<String, String> reportParameters;
	
	@Autowired
	private UserActivityService userActivityService;

	@Autowired
	private UserDataService userDataService;
	
	@Autowired
	private LookupService lookupService;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

	public String execute(){
		setMessage(getFlashMessage());
		return SEARCH;
	}

	public String processSearch(){
		if(StringUtils.isEmpty(exportType))
		{
			makeTableContent();
			return "searchJson";
		}
		else
		{			
			prepareParamVO(new UserDataParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_USER_ACTIVITY, 
					"ua.id", WebConstants.SORT_ORDER_ASC);
			UserActivityParamVO userActivityParamVO = (UserActivityParamVO) paramVO;
			listUserActivity = userActivityService.selectUserActivityByParamNoPaging(userActivityParamVO);
			reportParameters=new HashMap<String, String>();
			generateExportParameter(userActivityParamVO, reportParameters);
			if(exportType.equals(PDF)) return PDF;
			else if(exportType.equals(XLS)) return XLS;
			else return CSV;
		}
	}
	
	private void generateExportParameter(UserActivityParamVO paramVO,HashMap<String, String> reportParam)
	{
		if(paramVO.getUserDataId()==0)
		{
			reportParam.put("userCode", "all");
		}
		else
		{
			UserData userData = userDataService.findUserById(paramVO.getUserDataId());
			reportParam.put("userCode", userData.getUserCode());
		}
		
		if(paramVO.getAction().equals("-1"))
		{
			reportParam.put("activity", "all");
		}
		else
		{
			reportParam.put("activity", paramVO.getAction());
		}
		
		if(paramVO.getModuleName().equals("-1"))
		{
			reportParam.put("moduleName", "all");
		}
		else
		{
			reportParam.put("moduleName", paramVO.getModuleName());
		}
	}
	
	public String gotoSearch(){
		return SEARCH;
	}

	public String gotoInput(){
		return INPUT;
	}

	public String detail(){
		userActivity=userActivityService.selectUserActivityById(key);
		changedAttribute = userActivityService.convertStringToUserActivityVO(userActivity.getChangedAttribute());
		return DETAIL;
	}

	public String finish(){
		addActionMessage(message);
		return SEARCH;
	}

	public int getKey(){
		return key;
	}

	public void setKey(int key){
		this.key=key;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message){
		this.message=message;
	}


	// list status
	public List<Lookup> getListStatus() {
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_LOGIN_USER);
		return listStatus;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_REPORT_USER_ACTIVITY;
	}
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}

	public List<MapVO> getListAction() {
		List<MapVO> listAction = userActivityService.getViewActivityAction();
		return listAction;
	}
	
	public List<MapVO> getListModuleName() {
		List<MapVO> listModuleName = userActivityService.getViewActivityModuleName();
		return listModuleName;
	}
	
	public List<MapVO> getListTargetTable() {
		List<MapVO> listTargetTable = userActivityService.getViewActivityTargetTable();
		return listTargetTable;
	}
	
	public List<UserData> getListUser() {
		List<UserData> listUser = userActivityService.getViewActivityUser();
		return listUser;
	}
	
	public List<UserActivityVO> getChangedAttribute() {
		return changedAttribute;
	}

	public void setChangedAttribute(List<UserActivityVO> changedAttribute) {
		this.changedAttribute = changedAttribute;
	}

	public UserActivity getUserActivity() {
		return userActivity;
	}

	public void setUserActivity(UserActivity userActivity) {
		this.userActivity = userActivity;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public List<UserActivity> getListUserActivity() {
		return listUserActivity;
	}

	public void setListUserActivity(List<UserActivity> listUserActivity) {
		this.listUserActivity = listUserActivity;
	}

	public HashMap<String, String> getReportParameters() {
		return reportParameters;
	}

	public void setReportParameters(HashMap<String, String> reportParameters) {
		this.reportParameters = reportParameters;
	}


	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new UserDataParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_USER_ACTIVITY, 
				"ua.id", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.userCode"), getText("l.activity"), getText("l.moduleName"), 
				getText("l.targetTable"), getText("l.targetId")};
		String[] arrayBody={"userCode", "action", "moduleName", "targetTable", "targetId"};
		String[] arrayDbVariable={"ud.user_code", "ua.action", "ua.module_name","ua.target_table", "ua.target_id"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("UserActivityReport!detail.web", "userCode", new String[]{"key"}, new String[]{"id"}));
		
		UserActivityParamVO userActivityParamVO = (UserActivityParamVO) paramVO;
		int totalRow = userActivityService.countUserActivityByParam(userActivityParamVO);
		listUserActivity = userActivityService.selectUserActivityByParam(userActivityParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.listUserActivity"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listUserActivity), getCurrentPage(), 
				totalRow, listLinkTable, language, listUserActivity.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new UserActivityParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/

}