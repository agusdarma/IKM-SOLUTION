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

import com.jakarta.software.DateConverter;
import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.CifReportVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserActivityVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ReportParamVO;
import com.jakarta.software.web.entity.AccessGroupHeader;
import com.jakarta.software.web.entity.GroupLimitHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.AccessGroupService;
import com.jakarta.software.web.service.GroupLimitService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.ReportService;

public class CifReportAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CifReportAction.class);

	private int key;
	private String message;
	private UserActivity userActivity;
	private List<CifReportVO> listCif;
	private List<UserActivityVO> changedAttribute;
	private String json;
	private WebResultVO wrv;
	private String exportType;
	private HashMap<String, String> reportParameters;
	
	@Autowired
	private ReportService reportService;

	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private AccessGroupService accessGroupService;
	
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
			prepareParamVO(new MerchantParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_CIF, 
					"c.id", WebConstants.SORT_ORDER_ASC);
			ReportParamVO reportParamVO = (ReportParamVO) paramVO;
			listCif = reportService.findCifByParamNoPaging(reportParamVO);
			reportParameters=new HashMap<String, String>();
			generateExportParameter(reportParamVO, reportParameters);
			if(exportType.equals(PDF)) return PDF;
			else if(exportType.equals(XLS)) return XLS;
			else return CSV;
		}
	}
	
	public String gotoSearch(){
		return SEARCH;
	}

	public String gotoInput(){
		return INPUT;
	}

	public String detail(){
/*		userActivity=userActivityService.selectUserActivityById(key);
		changedAttribute = userActivityService.convertStringToUserActivityVO(userActivity.getChangedAttribute());
*/		return DETAIL;
	}

	public String finish(){
		addActionMessage(message);
		return SEARCH;
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
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_REPORT_CIF;
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

	public List<CifReportVO> getListCif() {
		return listCif;
	}

	public void setListCif(List<CifReportVO> listCif) {
		this.listCif = listCif;
	}

	
	public List<AccessGroupHeader> getListAccessGroup() throws MmbsWebException
	{
		List<AccessGroupHeader> listAccessGroup = accessGroupService.findAllAccessGroup();
		return listAccessGroup;
	}
	
	// list status
	public List<Lookup> getListStatus() {
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_STATUS);
		for (int i = 0; i < listStatus.size(); i++) {
			if(listStatus.get(i).getLookupValue().equals(WebConstants.STATUS_CIF_TERMINATE))
			{
				listStatus.remove(i);
			}
		}
		return listStatus;
	}

	// list cif group
	public List<GroupLimitHeader> getListCifGroup() {
		List<GroupLimitHeader> listCifGroup = groupLimitService.findAllGroupLimit();				
		return listCifGroup;
	}

	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_CIF, 
				"c.id", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.phoneNo"), getText("l.cifName"), getText("l.email"), getText("l.status"), 
				getText("l.authStatus"),  getText("l.groupName"), getText("l.accessName"), getText("l.createdBy"), 
				getText("l.createdOn"), getText("l.updatedBy"), getText("l.updatedOn")};
		
		String[] arrayBody={"deviceCode", "cifName", "email", "status", 
				"authStatus", "groupName", "accessName", "createdBy",
				"createdOn", "updatedBy", "updatedOn"};
		
		String[] arrayDbVariable={"c.device_code", "c.cif_name", "c.email", "ld.lookup_desc", 
				"ld1.lookup_desc", "glh.group_name", "ach.access_name", "ud.user_name",
				"c.created_on", "ud1.user_name", "c.updated_on"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		//listLinkTable.add(new LinkTableVO("CifReport!detail.web", "deviceCode", new String[]{"key"}, new String[]{"id"}));
		
		ReportParamVO reportParamVO = (ReportParamVO) paramVO;
		int totalRow = reportService.countCifReportByParam(reportParamVO);
		listCif = reportService.findCifReportByParam(reportParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.listCif"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listCif), getCurrentPage(), 
				totalRow, listLinkTable, language, listCif.size(), paramVO);
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