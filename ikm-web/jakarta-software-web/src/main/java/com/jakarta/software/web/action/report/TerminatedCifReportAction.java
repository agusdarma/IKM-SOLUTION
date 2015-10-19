package com.jakarta.software.web.action.report; 

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jakarta.software.DateConverter;
import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.CifReportVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.TerminatedCifReportVO;
import com.jakarta.software.web.data.TransactionReportVO;
import com.jakarta.software.web.data.UserActivityVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ReportParamVO;
import com.jakarta.software.web.entity.AccessGroupHeader;
import com.jakarta.software.web.entity.AccountHistory;
import com.jakarta.software.web.entity.GroupLimitHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.AccessGroupService;
import com.jakarta.software.web.service.AccountService;
import com.jakarta.software.web.service.GroupLimitService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.ReportService;

public class TerminatedCifReportAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CifReportAction.class);

	private int key;
	private int cifId;
	private String message;
	private UserActivity userActivity;
	private List<TerminatedCifReportVO> listTerminatedCif;
	private List<UserActivityVO> changedAttribute;
	private String json;
	private WebResultVO wrv;
	private String exportType;
	private HashMap<String, String> reportParameters;
	private List<TerminatedCifReportVO> listCifHistory;
	private List<AccountHistory> listAccountHistory;
	
	@Autowired
	private ReportService reportService;

	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private AccessGroupService accessGroupService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private GroupLimitService groupLimitService;
	
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
			prepareParamVO(new MerchantParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_TERMINATED_CIF, 
					"ch.id", WebConstants.SORT_ORDER_ASC);
			ReportParamVO reportParamVO = (ReportParamVO) paramVO;
			reportParamVO.setAuthStatus(WebConstants.STAT_APPROVED);
			reportParamVO.setStatus(WebConstants.STATUS_CIF_TERMINATE);
			listTerminatedCif = reportService.findTerminatedCifByParamNoPaging(reportParamVO);
			reportParameters=new HashMap<String, String>();
			generateExportParameter(reportParamVO, reportParameters);
			if(exportType.equals(PDF)) return PDF;
			else if(exportType.equals(XLS)) return XLS;
			else return CSV;
		}
	}
	
	private void generateExportParameter(ReportParamVO paramVO,HashMap<String, String> reportParam)
	{
		reportParam.put("startDate", "");
		reportParam.put("endDate", "");
		reportParam.put("deviceCode", paramVO.getDeviceCode());
		if(paramVO.getAccessGroup()==0)
		{
			reportParam.put("accessGroup", "all");
		}
		else
		{
			AccessGroupHeader accessGroupHeader = accessGroupService.findAccessById(paramVO.getAccessGroup());
			reportParam.put("accessGroup", accessGroupHeader.getAccessName());
		}
		if(paramVO.getCifGroup()==0)
		{
			reportParam.put("cifGroup", "all");
		}
		else
		{
			GroupLimitHeader glh = groupLimitService.findGroupLimitHeaderById(paramVO.getCifGroup());
			reportParam.put("cifGroup", glh.getGroupName());
		}
		
		if(paramVO.getStatus().equals("0"))
		{
			reportParam.put("status", "all");
		}
		else
		{
			List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_STATUS);
			for (Lookup lookup : listStatus) 
			{
				if(lookup.getLookupValue().equals(paramVO.getStatus()))
				{
					reportParam.put("status", lookup.getLookupDesc());
				}
			}
		}
		if(paramVO.getStartDate()!=null)
		{
			reportParam.put("startDate", DateConverter.stringValue(paramVO.getStartDate()));
		}
		if(paramVO.getEndDate()!=null)
		{
			reportParam.put("endDate", DateConverter.stringValue(paramVO.getEndDate()));
		}
	}
	
	public String findAccount()
	{
		listAccountHistory=accountService.findListAccountHistoryByCifHistoryId(cifId);
		Gson gson=new Gson();
		json=gson.toJson(listAccountHistory);
		return "findAccountJson";
	}
	
	public String gotoSearch(){
		return SEARCH;
	}

	public String gotoInput(){
		return INPUT;
	}

	public String detail(){
		listCifHistory = reportService.findTerminatedCifDetail(key);
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
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_STATUS);
		return listStatus;
	}

	// list cif group
	public List<GroupLimitHeader> getListCifGroup() {
		List<GroupLimitHeader> listCifGroup = groupLimitService.findAllGroupLimit();				
		return listCifGroup;
	}

	// list account type
	public List<Lookup> getListAccountType() {
		List<Lookup> listAccountType = lookupService.findLookupByCat(LookupService.CAT_ACCOUNT_TYPE_DISPLAY);
		return listAccountType;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_REPORT_TERMINATED_CIF;
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

	public HashMap<String, String> getReportParameters() {
		return reportParameters;
	}

	public void setReportParameters(HashMap<String, String> reportParameters) {
		this.reportParameters = reportParameters;
	}

	public List<TerminatedCifReportVO> getListTerminatedCif() {
		return listTerminatedCif;
	}

	public void setListCif(List<TerminatedCifReportVO> listTerminatedCif) {
		this.listTerminatedCif = listTerminatedCif;
	}
	
	public List<AccessGroupHeader> getListAccessGroup() throws MmbsWebException
	{
		List<AccessGroupHeader> listAccessGroup = accessGroupService.findAllAccessGroup();
		return listAccessGroup;
	}
	
	public void setListTerminatedCif(List<TerminatedCifReportVO> listTerminatedCif) {
		this.listTerminatedCif = listTerminatedCif;
	}
	
	public List<TerminatedCifReportVO> getListCifHistory() {
		return listCifHistory;
	}

	public void setListCifHistory(List<TerminatedCifReportVO> listCifHistory) {
		this.listCifHistory = listCifHistory;
	}

	public List<AccountHistory> getListAccountHistory() {
		return listAccountHistory;
	}

	public void setListAccountHistory(List<AccountHistory> listAccountHistory) {
		this.listAccountHistory = listAccountHistory;
	}

	public int getCifId() {
		return cifId;
	}

	public void setCifId(int cifId) {
		this.cifId = cifId;
	}
	
	public GroupLimitService getGroupLimitService() {
		return groupLimitService;
	}

	public void setGroupLimitService(GroupLimitService groupLimitService) {
		this.groupLimitService = groupLimitService;
	}



	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_TERMINATED_CIF, 
				"ch.id", WebConstants.SORT_ORDER_ASC);		

		String[] arrayHeader={getText("l.phoneNo"), getText("l.cifName"), getText("l.email"), 
				getText("l.status"), getText("l.authStatus"), getText("l.cifGroup"), 
				getText("l.accessGroup"), getText("l.createdBy"), getText("l.createdOn"), 
				getText("l.updatedBy"), getText("l.terminatedDate")};
		
		String[] arrayBody={"deviceCode", "cifName", "email", 
				"status", "authStatus", "groupName",
				"accessName", "createdBy", "createdOn",
				"updatedBy", "updatedOn"};
		
		String[] arrayDbVariable={"ch.device_code", "ch.cif_name", "ch.email", 
				"ld.lookup_desc", "ld1.lookup_desc", "glh.group_name", 
				"ach.access_name", "ud.user_name", "ch.created_on",
				"ud1.user_name", "ch.updated_on"};
		
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		//listLinkTable.add(new LinkTableVO("TerminatedCifReport!detail.web", "deviceCode", new String[]{"key"}, new String[]{"cifId"}));
		
		ReportParamVO reportParamVO = (ReportParamVO) paramVO;
		reportParamVO.setAuthStatus(WebConstants.STAT_APPROVED);
		reportParamVO.setStatus(WebConstants.STATUS_CIF_TERMINATE);
		int totalRow = reportService.countCifReportByParam(reportParamVO);
		listTerminatedCif = reportService.findTerminatedCifByParam(reportParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.listTerminatedCif"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listTerminatedCif), getCurrentPage(), 
				totalRow, listLinkTable, language, listTerminatedCif.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new ReportParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/

}