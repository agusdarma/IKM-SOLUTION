package com.jakarta.software.web.action.report; 

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.DateConverter;
import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.TransactionReportVO;
import com.jakarta.software.web.data.UserActivityVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ReportParamVO;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.ReportService;

public class SummaryTransactionAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SummaryTransactionAction.class);

	private int key;
	private String message;
	private UserActivity userActivity;
	private List<TransactionReportVO> listSummaryTransaction;
	private List<UserActivityVO> changedAttribute;
	private String json;
	private WebResultVO wrv;
	private String exportType;
	private HashMap<String, String> reportParameters;
	
	@Autowired
	private ReportService reportService;

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
			prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_SUMMARY_TRANSACTION, 
					"x.trxCode", WebConstants.SORT_ORDER_ASC);	
			ReportParamVO reportParamVO = (ReportParamVO) paramVO;
			listSummaryTransaction = reportService.findSummaryTransactionByParamNoPaging(reportParamVO);
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
	
	private void generateExportParameter(ReportParamVO paramVO,HashMap<String, String> reportParam)
	{
		Map<Integer, String> listMonth = WebConstants.MAP_MONTH;
		Object month = listMonth.get(paramVO.getMonth());
		reportParam.put("month", month.toString());		
		reportParam.put("year", Integer.toString(paramVO.getYear()));
		
		if(paramVO.getChannelType().equals("all"))
		{
			reportParam.put("channelType", "all");
		}
		else
		{
			List<Lookup> listChannelType = lookupService.findLookupByCat(LookupService.CAT_CHANNEL_TYPE);
			for (Lookup lookup : listChannelType) 
			{
				if(lookup.getLookupValue().equals(paramVO.getChannelType()))
				{
					reportParam.put("channelType", lookup.getLookupDesc());
				}
			}
		}
		
		if(paramVO.getTrxCode().equals("all"))
		{
			reportParam.put("trxCode", "all");
		}
		else
		{
			List<Lookup> listTrxCode = lookupService.findLookupByCat(LookupService.CAT_TRX_TYPE);
			for (Lookup lookup : listTrxCode) 
			{
				if(lookup.getLookupValue().equals(paramVO.getTrxCode()))
				{
					reportParam.put("trxCode", lookup.getLookupDesc());
				}
			}
		}
		
		if(paramVO.getTrxType().equals("all"))
		{
			reportParam.put("trxType", "all");
		}
		else
		{
			List<Lookup> listTrxType = lookupService.findLookupByCat(LookupService.CAT_TRX_STATUS);
			for (Lookup lookup : listTrxType) 
			{
				if(lookup.getLookupValue().equals(paramVO.getTrxType()))
				{
					reportParam.put("trxType", lookup.getLookupDesc());
				}
			}
		}
		
		if(paramVO.getOperatorCode().equals("all"))
		{
			reportParam.put("operatorCode", paramVO.getOperatorCode());
		}
		else
		{
			List<Lookup> listOperatorCode = lookupService.findLookupByCat(LookupService.CAT_OPERATOR);
			for (Lookup lookup : listOperatorCode) 
			{
				if(lookup.getLookupValue().equals(paramVO.getOperatorCode()))
				{
					reportParam.put("operatorCode", lookup.getLookupDesc());
				}
			}
		}
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
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_USER_STATUS);
		return listStatus;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_REPORT_SUMMARY_TRANSACTION;
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

	public List<TransactionReportVO> getListSummaryTransaction() {
		return listSummaryTransaction;
	}

	public void setListSummaryTransaction(List<TransactionReportVO> listSummaryTransaction) {
		this.listSummaryTransaction = listSummaryTransaction;
	}

	// list trx code
	public List<Lookup> getListTrxCode() {
		try {
			List<Lookup> listTrxCode = lookupService.findLookupByCat(LookupService.CAT_TRX_TYPE);
			return listTrxCode;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	// list trx type
	public List<Lookup> getListTrxType() {
		List<Lookup> listTrxType = lookupService.findLookupByCat(LookupService.CAT_TRX_STATUS);
		return listTrxType;
	}
	
	public List<Lookup> getListChannelType() {
		List<Lookup> listChannelType = lookupService.findLookupByCat(LookupService.CAT_CHANNEL_TYPE);
		return listChannelType;
	}
	
	public Map<Integer, String> getListMonth()
	{
		Map<Integer, String> listMonth = WebConstants.MAP_MONTH;
		return listMonth;
	}
	
	public List<Lookup> getListOperatorCode(){
		List<Lookup> listOperatorCode = lookupService.findLookupByCat(LookupService.CAT_OPERATOR);
		return listOperatorCode;
	}
	
	public Map<Integer, Integer> getListYear()
	{
		int nowYear = Calendar.getInstance().get(Calendar.YEAR);
		Map<Integer, Integer> listYear = new LinkedHashMap<Integer, Integer>();
		for (int i = 0; i < 3; i++) {
			listYear.put(nowYear-i, nowYear-i);
		}
		return listYear;
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_SUMMARY_TRANSACTION, 
				"x.trxCode", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.trxType"), getText("l.platform"), getText("l.operator"), 
				getText("l.status"), getText("l.totalTransaction")};
		
		String[] arrayBody={"trxCode", "channelType", "operatorCode", 
				"status", "totalTransaction"};
		
		String[] arrayDbVariable={"x.trxCode", "x.channelType", "x.operatorCode", 
				"x.status", "x.totalTransaction"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		
		ReportParamVO reportParamVO = (ReportParamVO) paramVO;
		int totalRow = reportService.countSummaryTransactionReportByParam(reportParamVO);
		listSummaryTransaction = reportService.findSummaryTransactionByParam(reportParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.listSummaryTransaction"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listSummaryTransaction), getCurrentPage(), 
				totalRow, listLinkTable, language, listSummaryTransaction.size(), paramVO);
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