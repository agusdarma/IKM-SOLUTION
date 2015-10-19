package com.jakarta.software.web.action.customerSupport;

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
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.TrxLogBrowserDetailVO;
import com.jakarta.software.web.data.TrxLogBrowserVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.TrxLogBrowserParamVO;
import com.jakarta.software.web.entity.Bank;
import com.jakarta.software.web.entity.BillPayment;
import com.jakarta.software.web.entity.GroupLimitHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.Product;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.service.BankService;
import com.jakarta.software.web.service.BillPaymentService;
import com.jakarta.software.web.service.GroupLimitService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.ProductService;
import com.jakarta.software.web.service.TrxLogBrowserService;
import com.jakarta.software.web.utils.DateUtils;

public class TrxLogBrowserAction extends BaseListAction{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(TrxLogBrowserAction.class);
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BillPaymentService billPaymentService;
	
	@Autowired
	private GroupLimitService groupLimitService;
	
	@Autowired
	private TrxLogBrowserService trxLogBrowserService;
	
	private String syslogno;
	private String message;
	private TrxLogBrowserVO detailTrx;
	private List<TrxLogBrowserVO> listTrx;
	
	private List<Bank> listBankCode;
	private List<Product> listProduct;
	private List<BillPayment> listBillPayment;
	private List<GroupLimitHeader> listGroupLimit;
	private List<Lookup> listStatus;
	private List<Lookup> listTrxType;
	private List<Lookup> listChannelType;
	private String exportType;
	private HashMap<String, String> reportParameters;
	
	public String execute() {
		getLogger().debug("Processing -> execute()");
		setMessage(getFlashMessage());
		session.remove(WEB_CONTENT_KEY);
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
			prepareParamVO(new TrxLogBrowserParamVO(), WEB_PARAM_KEY + WebModules.MENU_CUSTOMER_SUPPORT_TRX_LOG_BROWSER, 
					"h.received_time", WebConstants.SORT_ORDER_ASC);	
			TrxLogBrowserParamVO trxLogBrowserParamVO = (TrxLogBrowserParamVO) paramVO;
			listTrx = trxLogBrowserService.findTrxLogBrowserByParamNoPaging(trxLogBrowserParamVO);
			reportParameters=new HashMap<String, String>();
			generateReportParameters(trxLogBrowserParamVO, reportParameters);
			if(exportType.equals(PDF)) return PDF;
			else if(exportType.equals(XLS)) return XLS;
			else return CSV;
		}
	}
	
	private void generateReportParameters(TrxLogBrowserParamVO trxLogBrowserParamVO,HashMap<String, String> reportParameters){
		reportParameters.put("startDate","");
		reportParameters.put("endDate","");
		reportParameters.put("syslogno","");
		reportParameters.put("phoneNo","");
		reportParameters.put("sourceAccount","");
//		reportParameters.put("startDate","");
//		reportParameters.put("startDate","");
//		reportParameters.put("startDate","");
//		reportParameters.put("startDate","");
//		reportParameters.put("startDate","");
		if(trxLogBrowserParamVO.getStartDate()!=null){
			reportParameters.put("startDate", DateConverter.stringValue(trxLogBrowserParamVO.getStartDate()));			
		}
		if(trxLogBrowserParamVO.getEndDate()!=null){
			reportParameters.put("endDate", DateConverter.stringValue(trxLogBrowserParamVO.getEndDate()));			
		}
		if(StringUtils.isNotEmpty(trxLogBrowserParamVO.getSyslogno())){
			reportParameters.put("syslogno", trxLogBrowserParamVO.getSyslogno());			
		}
		if(StringUtils.isNotEmpty(trxLogBrowserParamVO.getPhoneNo())){
			reportParameters.put("phoneNo", trxLogBrowserParamVO.getPhoneNo());			
		}
		if(StringUtils.isNotEmpty(trxLogBrowserParamVO.getSourceAccount())){
			reportParameters.put("sourceAccount", trxLogBrowserParamVO.getSourceAccount());			
		}
	}
	
	public String listDetailTrx(){
		getLogger().info("Processing -> listDetailTrx()");
		makeTableContent2();
		return "searchJsonDetail";
	}
	
	public String detail(){
		getLogger().debug("Processing -> detail()");
		try {
			detailTrx = trxLogBrowserService.findTrxLogBrowserDetailBySyslogno(syslogno);
			session.put(WEB_CONTENT_KEY, detailTrx);
			return DETAIL;
		} catch (Exception e) {
			handleException(e);
		}
		return SEARCH;
	}
	
	public String gotoSearch(){
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
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
	
	// list trx code
	public List<Lookup> getListChannelSmi() {
		try {
			List<Lookup> listChannelSmi = lookupService.findLookupByCat(LookupService.CAT_CHANNEL_SMI);
			return listChannelSmi;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	// list all bank
	public List<Bank> getListBankCode() {
		try {
			listBankCode = bankService.selectAllBank();
			return listBankCode;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Bank>();
		}
	}
	
	// list all product
	public List<Product> getListProduct(){
		try {
			listProduct = productService.selectProduct();
			return listProduct;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Product>();
		}
	}
	
	// list all bill payment
	public List<BillPayment> getListBillPayment(){
		listBillPayment = billPaymentService.selectAllBillPayment();
		return listBillPayment;
	}
	
	// list all account type
	public List<GroupLimitHeader> getListAccountType(){
		listGroupLimit = groupLimitService.findAllGroupLimit();
		return listGroupLimit;
	}
	
	// list status
	public List<Lookup> getListStatus() {
		listStatus = lookupService.findLookupByCat(LookupService.CAT_STATUS);
		return listStatus;
	}
	
	// list trx type
	public List<Lookup> getListTrxType() {
		listTrxType = lookupService.findLookupByCat(LookupService.CAT_TRX_STATUS);
		return listTrxType;
	}
	
	// list channel type
	public List<Lookup> getListChannelType() {
		listChannelType = lookupService.findLookupByCat(LookupService.CAT_CHANNEL_TYPE);
		return listChannelType;
	}
	
	@Override
	protected Logger getLogger() {	
		return LOG;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<TrxLogBrowserVO> getListTrx() {
		return listTrx;
	}

	public void setListTrx(List<TrxLogBrowserVO> listTrx) {
		this.listTrx = listTrx;
	}
	
	public String getSyslogno() {
		return syslogno;
	}

	public void setSyslogno(String syslogno) {
		this.syslogno = syslogno;
	}
	
	public TrxLogBrowserVO getDetailTrx() {
		return detailTrx;
	}

	public void setDetailTrx(TrxLogBrowserVO detailTrx) {
		this.detailTrx = detailTrx;
	}
	
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new TrxLogBrowserParamVO(), WEB_PARAM_KEY + WebModules.MENU_CUSTOMER_SUPPORT_TRX_LOG_BROWSER, 
				"h.received_time", WebConstants.SORT_ORDER_DESC);		
		String[] arrayHeader={getText("l.syslogno"), getText("l.phoneNo"), getText("l.dateTime"), getText("l.trxName"), 
				getText("l.sourceAccount"), getText("l.destAccount"), getText("l.amount"), getText("l.channelType"), 
				getText("l.lastRc"), getText("l.hostRef")};
		String[] arrayBody={"sysLogNo", "deviceCode", "receivedTime", "trxCode", 
				"sourceAccount", "destAccount", "amount", "channelType", 
				"lastRc", "hostRef"};		
		String[] arrayDbVariable={"h.syslogno", "h.device_code", "h.received_time", "h.trx_code", 
				"h.srac", "h.dsac", "h.trx_value", "h.channel_type", 
				"h.last_rc", "td.host_ref"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("TrxLogBrowser!detail.web", "sysLogNo", new String[]{"syslogno"}, new String[]{"sysLogNo"}));
		
		TrxLogBrowserParamVO trxLogBrowserParamVO = (TrxLogBrowserParamVO) paramVO;
		int totalRow=0;
		try {
			if(trxLogBrowserParamVO.getEndDate() != null)
				trxLogBrowserParamVO.setEndDate(DateUtils.generateDateEnd(trxLogBrowserParamVO.getEndDate()));
			totalRow = trxLogBrowserService.countTrxLogBrowserByParam(trxLogBrowserParamVO);
			listTrx = trxLogBrowserService.findTrxLogBrowserByParam(trxLogBrowserParamVO);
		} catch (MmbsWebException e) {
		}	
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listTransaction"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listTrx), getCurrentPage(), 
				totalRow, listLinkTable, language, listTrx.size(), paramVO);
		
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new TrxLogBrowserParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}

	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/
	
	
	
	
	/**************************************   ESSENTIAL FOR LIST TRXLOGD  *******************************************/	
	private String resultSearchJson2;
	
	public void makeTableContent2()
	{
		prepareParamVO(new TrxLogBrowserParamVO(), WEB_PARAM_KEY + WebModules.MENU_CUSTOMER_SUPPORT_TRX_LOG_BROWSER, 
				null, WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.msglogno"), getText("l.custIn"), getText("l.custOut"), getText("l.messageFromUser"), 
				getText("l.messageForUser"), getText("l.lastRc"), getText("l.lastState"), getText("l.dateTime")};
		String[] arrayBody={"msgLogNo", "custIn", "custOut", "messageFromUser", 
				"messageForUser", "lastRc", "lastState", "receivedTime"};		
		String[] arrayDbVariable={"d.msglogno", "d.cust_in", "d.cust_out", "d.message_in", 
				"d.message_out", "d.result_code", "d.state", "h.received_time"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("TrxLogBrowser!detail.web", "sysLogNo", new String[]{"syslogno"}, new String[]{"sysLogNo"}));

		int totalRow=0;
		detailTrx = (TrxLogBrowserVO) session.get(WEB_CONTENT_KEY);
		totalRow = detailTrx.getListTrxDetail().size();
		List<TrxLogBrowserDetailVO> listDetail = detailTrx.getListTrxDetail();
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson2=webSearchResultService.composeSearchResultWithExport(getText("l.listDetailTrx"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listDetail), getCurrentPage(), 
				totalRow, listLinkTable, language, detailTrx.getListTrxDetail().size(), paramVO);
		
	}
	
	public InputStream getWsr2() {
		return new ByteArrayInputStream(resultSearchJson2.toString().getBytes());
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

	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/
	
}
