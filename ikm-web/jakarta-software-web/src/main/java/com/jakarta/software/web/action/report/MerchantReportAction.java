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
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.MerchantVO;
import com.jakarta.software.web.data.UserDataLoginVO;
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
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.ReportService;

public class MerchantReportAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MerchantReportAction.class);

	private int key;
	private String message;
	private UserActivity userActivity;
	private MerchantVO merchantVO;
	private List<MerchantVO> listMerchant;
	private List<MerchantVO> listHistoryMerchant;
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
			prepareParamVO(new MerchantParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_MERCHANT, 
					"m.id", WebConstants.SORT_ORDER_ASC);
			ReportParamVO reportParamVO = (ReportParamVO) paramVO;
			listMerchant = reportService.findMerchantReportByParamNoPaging(reportParamVO);
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
		reportParam.put("merchantCode", paramVO.getMerchantCode());
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
	
	public String gotoSearch(){
		return SEARCH;
	}

	public String gotoInput(){
		return INPUT;
	}

	public String detail(){
		merchantVO=reportService.findMerchantById(key);
		listHistoryMerchant=reportService.findHistoryMerchantById(key);
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
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_REPORT_MERCHANT;
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

	public List<MerchantVO> getListHistoryMerchant() {
		return listHistoryMerchant;
	}

	public void setListHistoryMerchant(List<MerchantVO> listHistoryMerchant) {
		this.listHistoryMerchant = listHistoryMerchant;
	}

	public UserActivity getUserActivity() {
		return userActivity;
	}

	public void setUserActivity(UserActivity userActivity) {
		this.userActivity = userActivity;
	}

	public List<MerchantVO> getListMerchant() {
		return listMerchant;
	}

	public void setListMerchant(List<MerchantVO> listMerchant) {
		this.listMerchant = listMerchant;
	}

	public MerchantVO getMerchantVO() {
		return merchantVO;
	}

	public void setMerchantVO(MerchantVO merchant) {
		this.merchantVO = merchant;
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
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		prepareParamVO(new MerchantParamVO(), WEB_PARAM_KEY + WebModules.MENU_REPORT_MERCHANT, 
				"m.id", WebConstants.SORT_ORDER_ASC);		
		
		String[] arrayHeader={getText("l.merchantName"), getText("l.merchantCode"), getText("l.alias"), getText("l.accountNumber"), 
				getText("l.status"), getText("l.accountType"), getText("l.createdBy"), getText("l.createdOn"), 
				getText("l.updatedBy"), getText("l.updatedOn")};
		
		String[] arrayBody={"merchantName", "merchantCode", "alias", "accountNumber", 
				"merchantStatusDisplay", "accountTypeDisplay", "createdByDisplay", "createdOn", 
				"updatedByDisplay", "updatedOn"};
		
		String[] arrayDbVariable={"m.merchant_name", "m.merchant_code", "m.alias", "m.account_number", 
				"m.status", "ld1.lookup_desc", "ud.user_name", "c.created_on", 
				"ud2.user_name", "c.updated_on"};
		
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("MerchantReport!detail.web", "merchantName", new String[]{"key"}, new String[]{"id"}));//jadiin constructor entity
		
		ReportParamVO reportParamVO = (ReportParamVO) paramVO;
		int totalRow =  reportService.countMerchantReportByParam(reportParamVO);
		listMerchant = reportService.findMerchantReportByParam(reportParamVO);		

		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResultWithExport(getText("l.listMerchant"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listMerchant), getCurrentPage(), 
				totalRow, listLinkTable, language, listMerchant.size(), paramVO);
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