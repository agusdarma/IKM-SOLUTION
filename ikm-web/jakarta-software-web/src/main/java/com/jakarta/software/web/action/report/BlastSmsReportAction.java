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
import com.jakarta.software.web.data.PushRequestVO;
import com.jakarta.software.web.data.TransactionReportVO;
import com.jakarta.software.web.data.UserActivityVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ReportParamVO;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.PushRequestDetail;
import com.jakarta.software.web.entity.PushRequestHeader;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.PushRequestService;
import com.jakarta.software.web.service.ReportService;
import com.jakarta.software.web.utils.Constants;

public class BlastSmsReportAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(BlastSmsReportAction.class);

	private int key;
	private String message;
	private UserActivity userActivity;
	private List<TransactionReportVO> listBlastSms;
	private List<PushRequestVO> pushRequestVO;
	private List<UserActivityVO> changedAttribute;
	private String json;
	private WebResultVO wrv;
	private String exportType;
	private HashMap<String, String> reportParameters;
	private PushRequestVO pushRequestHeader;
	
	@Autowired
	private ReportService reportService;

	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private PushRequestService pushRequestService;
	
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
			prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_BLAST_SMS, 
					"prh.id", WebConstants.SORT_ORDER_ASC);	
			ReportParamVO reportParamVO = (ReportParamVO) paramVO;
			listBlastSms = reportService.findBlastSmsByParamNoPaging(reportParamVO);
			reportParameters=new HashMap<String, String>();
			generateExportParameter(reportParamVO,reportParameters);
			if(exportType.equals(PDF)) return PDF;
			else if(exportType.equals(XLS)) return XLS;
			else return CSV;
		}
	}
	
	private void generateExportParameter(ReportParamVO paramVO,HashMap<String, String> reportParam)
	{
		reportParam.put("startDate", "");
		reportParam.put("endDate", "");
		if(paramVO.getStatus().equals("-1"))
		{
			reportParam.put("status", "all");
		}
		else
		{
			List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_BLAST_STATUS);
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
	
	public String gotoSearch(){
		return SEARCH;
	}

	public String gotoInput(){
		return INPUT;
	}

	public String detail(){
		pushRequestHeader = pushRequestService.selectPushRequestHeaderById(key);
		pushRequestVO=pushRequestService.selectListPushRequestDetailByHeaderId(key);
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
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_BLAST_STATUS);
		return listStatus;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_REPORT_BLAST_SMS;
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


	public List<TransactionReportVO> getListBlastSms() {
		return listBlastSms;
	}

	public void setListBlastSms(List<TransactionReportVO> listBlastSms) {
		this.listBlastSms = listBlastSms;
	}

	public List<PushRequestVO> getPushRequestVO() {
		return pushRequestVO;
	}

	public void setPushRequestVO(List<PushRequestVO> pushRequestVO) {
		this.pushRequestVO = pushRequestVO;
	}

	public PushRequestVO getPushRequestHeader() {
		return pushRequestHeader;
	}

	public void setPushRequestHeader(PushRequestVO pushRequestHeader) {
		this.pushRequestHeader = pushRequestHeader;
	}

	public List<Lookup> getListTrxType() {
		List<Lookup> listTrxType = lookupService.findLookupByCat(LookupService.CAT_TRX_STATUS);
		return listTrxType;
	}	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_BLAST_SMS, 
				"prh.id", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.sendTime"), getText("l.status"), getText("l.message"), getText("l.subject"), 
				getText("l.groupName"), getText("l.createdOn"), getText("l.createdBy")};
		String[] arrayBody={"sendTime", "statusDisplay", "message", "subject",
				"groupName", "createdOn", "createdBy"};
		String[] arrayDbVariable={"prh.sent_time", "ld.lookup_desc", "prh.message", "prh.subject",
				"gmh.group_name", "prh.created_on", "ud.user_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("BlastSmsReport!detail.web", "sendTime", new String[]{"key"}, new String[]{"id"}));
		
		ReportParamVO reportParamVO = (ReportParamVO) paramVO;
		int totalRow = reportService.countBlastSmsByParam(reportParamVO);
		listBlastSms = reportService.findBlastSmsByParam(reportParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.listBlastSms"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listBlastSms), getCurrentPage(), 
				totalRow, listLinkTable, language, listBlastSms.size(), paramVO);
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