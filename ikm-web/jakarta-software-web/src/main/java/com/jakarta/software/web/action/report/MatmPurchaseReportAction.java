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
import com.jakarta.software.web.data.TransactionReportVO;
import com.jakarta.software.web.data.UserActivityVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ReportParamVO;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.ReportService;

public class MatmPurchaseReportAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MatmPurchaseReportAction.class);

	private int key;
	private String message;
	private UserActivity userActivity;
	private List<TransactionReportVO> listMatmPurchase;
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
			prepareParamVO(new MerchantParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_MATM_PURCHASE, 
					"th.syslogno", WebConstants.SORT_ORDER_ASC);
			ReportParamVO reportParamVO = (ReportParamVO) paramVO;
			listMatmPurchase = reportService.findMatmByParamNoPaging(reportParamVO);			
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
		reportParam.put("merchantName", paramVO.getMerchantName());		
		if(paramVO.getTrxType().equals("-1"))
		{
			reportParam.put("trxType", "all");
		}
		else
		{
			List<Lookup> listTrxType = lookupService.findLookupByCat(LookupService.CAT_TRX_STATUS);
			for (Lookup lookup : listTrxType) 
			{
				if(lookup.getLookupValue().equals(paramVO.getStatus()))
				{
					reportParam.put("trxType", lookup.getLookupDesc());
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
/*		userActivity=userActivityService.selectUserActivityById(key);
		changedAttribute = userActivityService.convertStringToUserActivityVO(userActivity.getChangedAttribute());
*/		return DETAIL;
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
		return WebModules.MENU_REPORT_MATM_PURCHASE;
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

	public List<TransactionReportVO> getListMatmPurchase() {
		return listMatmPurchase;
	}

	public void setListMatmPurchase(List<TransactionReportVO> listMatmPurchase) {
		this.listMatmPurchase = listMatmPurchase;
	}

	// list trx type
	public List<Lookup> getListTrxType() {
		List<Lookup> listTrxType = lookupService.findLookupByCat(LookupService.CAT_TRX_STATUS);
		return listTrxType;
	}
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_MATM_PURCHASE, 
				"th.syslogno", WebConstants.SORT_ORDER_ASC);
		
		String[] arrayHeader={getText("l.dateTime"), getText("l.transactionNo"), getText("l.merchantCode"), 
				getText("l.merchantName"), getText("l.hostRef"), getText("l.lastRc")};
		
		String[] arrayBody={"receivedTime", "syslogno", "merchantCode", "merchantName", "btiRefNo", "lastRc"};
		
		String[] arrayDbVariable={"th.received_time", "th.syslogno", "th.merchant_code", 
				"th.merchant_name", "th.bti_ref_no", "th.last_rc"};
		
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		
		ReportParamVO reportParamVO = (ReportParamVO) paramVO;
		int totalRow = reportService.countMatmReportByParam(reportParamVO);
		listMatmPurchase = reportService.findMatmByParam(reportParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.listMatmPurchase"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listMatmPurchase), getCurrentPage(), 
				totalRow, listLinkTable, language, listMatmPurchase.size(), paramVO);
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