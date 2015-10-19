package com.jakarta.software.web.action.setting;

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
import com.jakarta.software.web.data.BankVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.BankParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.entity.Bank;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.BankService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;

public class BankAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(BankAction.class);

	@Autowired
	private BankService bankService;
	
	@Autowired
	private LookupService lookupService;
	
	private Bank bank;
	private int bankId;
	private List<BankVO> listBank;
	
	private WebResultVO wrv;
	private String json;
	private String message;
	
	public String execute(){
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
			wrv = bankService.insertOrUpdateBank(bank, loginVO, language);
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
			bank = bankService.findBankById(bankId);
			session.put(WEB_CONTENT_KEY, bank);
			return INPUT;
		} catch (Exception e) {
			handleException(e);
		}
		return SEARCH;
	}
	
	public String gotoInput(){
		session.remove(WEB_CONTENT_KEY);
		return INPUT;
	}
	
	public String gotoSearch(){
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
	}
	
	// list switching
	public List<Lookup> getListSwitching() {
		try {
			List<Lookup> listSwitching = lookupService.findLookupByCat(LookupService.CAT_SWITCHING);
			return listSwitching;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	@Override
	protected Logger getLogger() {		
		return LOG;
	}

	@Override
	public int getMenuId() {
		return WebModules.MENU_SETTING_BANK;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
	
	public List<BankVO> getListBank() {
		return listBank;
	}

	public void setListBank(List<BankVO> listBank) {
		this.listBank = listBank;
	}
	
	public Bank getBank() {
		if (bank == null) {
			Object obj = session.get(WEB_CONTENT_KEY);
			if (obj != null && obj instanceof Bank) {
				bank = (Bank) obj;
			}
		}
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new BankParamVO(), WEB_PARAM_KEY + WebModules.MENU_SETTING_BANK,
				"b.bank_code", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.bankCode"), getText("l.bankName"), getText("l.switching"), 
				getText("l.createdOn"), getText("l.createdBy"), getText("l.updatedOn"), getText("l.updatedBy")};
		String[] arrayBody={"bankCode", "bankName", "switching", "createdOn", "createdByDisplay","updatedOn", "updatedByDisplay"};		
		String[] arrayDbVariable={"b.bank_code", "b.bank_name", "b.switching","b.created_on", "ud.user_name",
				"b.updated_on", "ud2.user_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("Bank!detail.web", "bankCode", new String[]{"bankId"}, new String[]{"id"}));

		BankParamVO bankParamVO = (BankParamVO) paramVO;
		int totalRow=0;
		try {
			totalRow = bankService.countBankByParam(bankParamVO);
			listBank = bankService.findBankByParam(bankParamVO);
		} catch (MmbsWebException e) {
		}
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listBank"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listBank), getCurrentPage(), 
				totalRow, listLinkTable, language, listBank.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new BankParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/

}
