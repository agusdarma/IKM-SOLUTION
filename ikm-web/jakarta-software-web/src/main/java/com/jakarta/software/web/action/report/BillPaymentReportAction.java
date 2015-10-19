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
import com.jakarta.software.web.entity.Bank;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.UserActivity;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.ReportService;

public class BillPaymentReportAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(BillPaymentReportAction.class);

	private int key;
	private String message;
	private UserActivity userActivity;
	private List<TransactionReportVO> listBillPayment;
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
			prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_BILL_PAYMENT, 
					"th.syslogno", WebConstants.SORT_ORDER_ASC);
			ReportParamVO reportParamVO = (ReportParamVO) paramVO;
			listBillPayment = reportService.findBillPaymentByParamNoPaging(reportParamVO);			
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
		if(paramVO.getCategory().equals("-1"))
		{
			reportParam.put("category", "all");
		}
		else
		{
			List<Lookup> listBpCategory = lookupService.findLookupByCat(LookupService.CAT_BILL_PAYMENT_CATEGORY);
			for (Lookup lookup : listBpCategory) 
			{
				if(lookup.getLookupValue().equals(paramVO.getCategory()))
				{
					reportParam.put("category", lookup.getLookupDesc());
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
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_LOGIN_USER);
		return listStatus;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_REPORT_FUND_TRANSFER;
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

	public List<TransactionReportVO> getListBillPayment() {
		return listBillPayment;
	}

	public void setListBillPayment(List<TransactionReportVO> listBillPayment) {
		this.listBillPayment = listBillPayment;
	}

	public List<Lookup> getListBillPaymentCategory() {
		try {
			List<Lookup> listBpCategory = lookupService.findLookupByCat(LookupService.CAT_BILL_PAYMENT_CATEGORY);
			return listBpCategory;
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
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_BILL_PAYMENT, 
				"th.device_code", WebConstants.SORT_ORDER_ASC);		
		
		String[] arrayHeader={getText("l.phoneNo"), getText("l.dateTime"), getText("l.sourceAccount"),
				getText("l.billerNo"), getText("l.custNo"), getText("l.transactionValue"), 
				getText("l.hostRef"), getText("l.lastRc")};

		String[] arrayBody={"deviceCode" , "receivedTime", "srac", 
				"billerNo", "clientAccNo", "trxValue",
				"btiRefNo", "lastRc"};		
		
		String[] arrayDbVariable={"th.device_code", "td.received_time", "th.srac",
				"th.biller_no", "th.client_acc_no", "th.trx_value", 
				"td.bti_ref_no", "th.last_rc"};
		
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		
		ReportParamVO reportParamVO = (ReportParamVO) paramVO;
		int totalRow = reportService.countBillPaymentReportByParam(reportParamVO);
		listBillPayment = reportService.findBillPaymentByParam(reportParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.billPayment"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listBillPayment), getCurrentPage(), 
				totalRow, listLinkTable, language, listBillPayment.size(), paramVO);
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