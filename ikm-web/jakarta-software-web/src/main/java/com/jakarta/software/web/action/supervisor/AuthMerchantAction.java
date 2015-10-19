package com.jakarta.software.web.action.supervisor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.MerchantVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MerchantService;

public class AuthMerchantAction extends BaseListAction implements ModuleCheckable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AuthMerchantAction.class);
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private LookupService lookupService;
	
	private int merchantId;
	private String message;
	private MerchantVO merchantVO;
	private List<MerchantVO> listMerchant;
	
	private String json;
	private String mode;
	private WebResultVO wrv;
	
	public String execute(){
		setMessage(getFlashMessage());
		return SEARCH;
	}
	
	public String gotoSearch()
	{
		return SEARCH;
	}
	
	public String detail() {
		getLogger().debug("Processing -> detail()");
		// called when user needs to edit, to display input form
		try {
			merchantVO = merchantService.findMerchantHistoryByIdHistory(merchantId);
			session.put(WEB_CONTENT_KEY, merchantVO);
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}
	
	public String processAuthorize() {
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		LOG.debug("mode = " + mode + ", Merchant : " + merchantVO);// mode	// 1.approve // 2.reject
		merchantVO.setAuthStatus(mode);
		
		try {
			wrv = merchantService.authorizeMerchant(merchantVO, loginVO, mode, language);
			if(wrv.getType()==1)
			{
				setFlashMessage(wrv.getMessage());
			}
		} catch (Exception e) {
			wrv = handleJsonException(e);
		}
		
		Gson gson = new Gson();
		json = gson.toJson(wrv);
		return "json";
	}
	
	@Override
	public int getMenuId() {
		getFormatDate();
		return WebModules.MENU_SUPERVISOR_AUTH_MERCHANT;
	}
	
	public String processSearch() {
		makeTableContent();
		return "searchJson";
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
	
	public List<MerchantVO> getListMerchant() {
		return listMerchant;
	}

	public void setListMerchant(List<MerchantVO> listMerchant) {
		this.listMerchant = listMerchant;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public MerchantVO getMerchantVO() {
		if(merchantVO == null){
			Object obj = session.get(WEB_CONTENT_KEY);
			if(obj != null &&  obj instanceof MerchantVO){
				merchantVO = (MerchantVO) obj;
			}
		}
		return merchantVO;
	}

	public void setMerchantVO(MerchantVO merchantVO) {
		this.merchantVO = merchantVO;
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

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		prepareParamVO(new MerchantParamVO(), WEB_PARAM_KEY + WebModules.MENU_SUPERVISOR_AUTH_MERCHANT, 
				"m.id", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.merchantCode"), getText("l.merchantName"), 
				getText("l.alias"), getText("l.accountNumber"), getText("l.requestType")};
		String[] arrayBody={"merchantCode", "merchantName", "alias", "accountNumber", "activityType"};
		String[] arrayDbVariable={"m.merchant_code", "m.merchant_name", "m.alias", "m.account_number"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("AuthMerchant!detail.web", "merchantCode", new String[]{"merchantId"}, new String[]{"id"}));//jadiin constructor entity
		
		MerchantParamVO merchantParamVO = (MerchantParamVO) paramVO;
		merchantParamVO.setUpdatedBy(loginVO.getId());
		merchantParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
		merchantParamVO.setBranchId(loginVO.getBranchId());
		int totalRow =  merchantService.countMerchantHistoryByParam(merchantParamVO);
		listMerchant = merchantService.findMerchantHistoryByParam(merchantParamVO);		

		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listMerchant"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listMerchant), getCurrentPage(), 
				totalRow, listLinkTable, language, listMerchant.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new MerchantParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}

	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/
	
}
