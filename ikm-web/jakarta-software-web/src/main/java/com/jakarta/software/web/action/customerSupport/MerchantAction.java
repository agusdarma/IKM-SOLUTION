package com.jakarta.software.web.action.customerSupport;

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
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.Merchant;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MerchantService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.utils.Constants;

public class MerchantAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(MerchantAction.class);

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private LookupService lookupService;
	
	private int merchantId;
	private Merchant merchant;
	private List<MerchantVO> listMerchant;
	
	private WebResultVO wrv;
	private String json;
	private String message;
	
	public String execute(){
		getLogger().debug("Processing -> execute()");
		setMessage(getFlashMessage());
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
	}
	
	public String processSearch(){
		getLogger().debug("Processing -> processSearch()");
		makeTableContent();
		return "searchJson";
	}
	
	public String processInput(){
		getLogger().debug("Processing -> processInput()");
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		try {
			wrv = merchantService.insertOrUpdateMerchant(merchant, loginVO, language);
			if(wrv.getType() == WebConstants.TYPE_UPDATE)
			{
				setFlashMessage(wrv.getMessage());
			}
		} catch (MmbsWebException mwe) {
			wrv = handleJsonException(mwe);
		} catch (Exception e) {
			wrv = handleJsonException(e);
		}
		Gson gson = new Gson();
		json = gson.toJson(wrv);
		return "json";
	}
	
	public String detail(){
		getLogger().debug("Processing -> detail()");
		try {
			merchant = merchantService.findMerchantById(merchantId);
			session.put(WEB_CONTENT_KEY, merchant);
			return INPUT;
		} catch (Exception e) {
			handleException(e);
		}
		return SEARCH;
	}
	
	// list currency
	public List<Lookup> getListAccountType() {
		try {
			List<Lookup> listAccountType = lookupService.findLookupByCat(LookupService.CAT_ACCOUNT_TYPE_DISPLAY);
			return listAccountType;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	// list currency
	public List<Lookup> getListCurrency() {
		try {
			List<Lookup> listCurrency = lookupService.findLookupByCat(LookupService.CAT_CURR_CODE);
			return listCurrency;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	// list status
	public List<Lookup> getListStatus() {
		try {
			List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_STATUS);
			for (int i = 0; i < listStatus.size(); i++) {
				if(listStatus.get(i).getLookupValue().equalsIgnoreCase(Constants.BLOCK_CODE)){
					listStatus.remove(i);
				}
			}
			return listStatus;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	public String gotoInput(){
		session.remove(WEB_CONTENT_KEY);
		return INPUT;
	}
	
	public String gotoSearch(){
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
	}
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getMenuId() {
		return WebModules.MENU_CUSTOMER_SUPPORT_MERCHANT;
	}

	public Merchant getMerchant() {
		if(merchant == null){
			Object obj = (Object) session.get(WEB_CONTENT_KEY);
			if(obj != null && obj instanceof Merchant){
				merchant = (Merchant) obj;
			}
			else
			{
				merchant = new Merchant();
			}
		}
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
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
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new MerchantParamVO(), WEB_PARAM_KEY + WebModules.MENU_CUSTOMER_SUPPORT_MERCHANT, 
				"m.merchant_code", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.merchantCode"), getText("l.merchantName"), getText("l.alias"), getText("l.accountType"), 
				getText("l.accountNumber"), getText("l.currency"), getText("l.timeout"),
				getText("l.status"), getText("l.createdOn"), getText("l.createdBy"), getText("l.updatedOn"), getText("l.updatedBy")};
		String[] arrayBody={"merchantCode", "merchantName", "alias", "accountTypeDisplay", 
				"accountNumber", "currencyTypeDisplay", "timeout",
				"merchantStatusDisplay", "createdOn", "createdByDisplay","updatedOn", "updatedByDisplay"};		
		String[] arrayDbVariable={"m.merchant_code", "m.merchant_name", "m.alias", "m.account_type", 
				"m.account_number", "m.currency", "m.timeout",
				"m.status", "m.created_on", "ud.user_name", "m.updated_on", "ud2.user_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("Merchant!detail.web", "merchantCode", new String[]{"merchantId"}, new String[]{"id"}));
		
		MerchantParamVO merchantParamVO = (MerchantParamVO) paramVO;
		int totalRow=0;
		try {
			totalRow = merchantService.countMerchantByParam(merchantParamVO);
			listMerchant = merchantService.findMerchantByParam(merchantParamVO);
		} 
		catch (MmbsWebException e) {
		}	
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
