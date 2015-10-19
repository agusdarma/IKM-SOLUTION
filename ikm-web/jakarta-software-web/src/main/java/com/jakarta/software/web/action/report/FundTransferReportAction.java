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
import com.jakarta.software.web.service.BankService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.ReportService;

public class FundTransferReportAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(FundTransferReportAction.class);

	private int key;
	private String message;
	private UserActivity userActivity;
	private List<TransactionReportVO> listFundTransfer;
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
	private BankService bankService;
	
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
			prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_FUND_TRANSFER, 
					"th.device_code", WebConstants.SORT_ORDER_ASC);
			ReportParamVO reportParamVO = (ReportParamVO) paramVO;
			listFundTransfer = reportService.findFundTransferByParamNoPaging(reportParamVO);
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
		if(paramVO.getBankCode().equals("-1"))
		{
			reportParam.put("bankCode", "all");
		}
		else
		{
			Bank bank = bankService.findBankByCode(paramVO.getBankCode());
			reportParam.put("bankCode", bank.getBankName());			
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


	public List<TransactionReportVO> getListFundTransfer() {
		return listFundTransfer;
	}

	public void setListFundTransfer(List<TransactionReportVO> listFundTransfer) {
		this.listFundTransfer = listFundTransfer;
	}

	public List<Bank> getListBank()
	{
		try {
			List<Bank> listBank = bankService.selectAllBank();
			return listBank;
		} catch (MmbsWebException e) {
			List<Bank> listBank = new ArrayList<Bank>();
			return listBank;
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
		prepareParamVO(new ReportParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_FUND_TRANSFER, 
				"th.device_code", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.phoneNo"), getText("l.dateTime"), getText("l.sourceAccount"), 
				getText("l.destinationBank"), getText("l.destAccount"), getText("l.transactionValue"),
				getText("l.hostRef"), getText("l.lastRc")};
		
		String[] arrayBody={"deviceCode", "receivedTime", "srac",  
				"bankCode", "dsac", "trxValue",
				"btiRefNo", "lastRc"};
		
		String[] arrayDbVariable={"th.device_code", "td.received_time", "th.srac",
				"th.bank_code", "th.dsac", "th.trx_value", 
				"td.bti_ref_no", "th.last_rc"};
		
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		
		ReportParamVO reportParamVO = (ReportParamVO) paramVO;
		int totalRow = reportService.countFundTransferReportByParam(reportParamVO);
		listFundTransfer = reportService.findFundTransferByParam(reportParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.listFundTransfer"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listFundTransfer), getCurrentPage(), 
				totalRow, listLinkTable, language, listFundTransfer.size(), paramVO);
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