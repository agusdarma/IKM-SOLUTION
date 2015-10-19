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
import com.jakarta.software.web.data.CifHistoryVO;
import com.jakarta.software.web.data.CifVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.CifParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ProductParamVO;
import com.jakarta.software.web.entity.Account;
import com.jakarta.software.web.entity.AccountHistory;
import com.jakarta.software.web.entity.Cif;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.AccountService;
import com.jakarta.software.web.service.CifHistoryService;
import com.jakarta.software.web.service.CifService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;

public class ResetPinAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ResetPinAction.class);
		
	@Autowired
	private CifService cifService;
		
	@Autowired
	private CifHistoryService cifHistoryService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private LookupService lookupService;
	private List<CifVO> listCif;
	private List<Account> listAccount;
	private WebResultVO wrv;	
	private int cifId;
	private String confirmPin;
	private String message;
	private String jsonMessage;
	private String json;
	private Cif cif;
	private CifHistoryVO cifHistoryVO;
	private String actionFrom;
	private int hasLogin;
	private int cifHistoryId;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

//SAVE & INITIALIZATION
	public String execute() {
		setMessage(getFlashMessage());
		return SEARCH;
	}

	public String gotoSearch(){
		return SEARCH;
	}
	
	public String processInput(){
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		try {
			wrv = cifHistoryService.insertCifHistoryResetPin(cif.getId(), loginVO, language);
			LOG.info("Reset Pin with cif:" + cif);
			if(wrv.getKey1()==WebConstants.YES)
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
		return "inputJson";
		
	}

	public String forcedUrl(){		
		try{
			cifHistoryVO = cifHistoryService.findCifHistoryDetailById(cifHistoryId);
			List<AccountHistory> listAccountHistory = accountService.getListAccountHist(cifHistoryVO.getCifHistoryId());
			cifHistoryVO.setListAccountHistory(listAccountHistory);
			cifHistoryVO.setListAccount(accountService.getListAccount(cifHistoryVO.getCifId()));
			actionFrom=WebConstants.ACT_MODULE_RESET_PIN;
			hasLogin=0;
			session.put(WEB_CONTENT_KEY, cifHistoryVO);
			setMessage(getFlashMessage());
		} catch (Exception e) {
			cifHistoryVO = new CifHistoryVO();
		}
		return "cif_forced_auth";
	}
	
	public String finish(){
		addActionMessage(message);
		return SEARCH;
	}
	
	public String processSearch() {
		makeTableContent();
		return "searchJson";
	}
		
	public String detail() {
		getLogger().debug("Processing -> detail()");
		try {
			listAccount = accountService.findListAccByCifId(cifId);
			cif = cifService.findCifById(cifId);
			session.put(WEB_CONTENT_KEY, cifId);
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}

	public InputStream getJsonMessage(){
		return new ByteArrayInputStream(jsonMessage.toString().getBytes());
	}

	@Override
	public int getMenuId() {
		return WebModules.MENU_CUSTOMER_SUPPORT_RESET_PIN;
	}

// SETTER GETTER AREA
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getResultSearchJson() {
		return resultSearchJson;
	}

	public void setResultSearchJson(String resultSearchJson) {
		this.resultSearchJson = resultSearchJson;
	}

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}
	
	// list status
	public List<Lookup> getListStatus() {
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_STATUS);
		return listStatus;
	}
	
	// list department
	public List<Lookup> getListProductType() {
		List<Lookup> listDepartment = lookupService.findLookupByCat(LookupService.CAT_PRODUCT_TYPE);
		return listDepartment;
	}

	public void setJsonMessage(String jsonMessage) {
		this.jsonMessage = jsonMessage;
	}

	public List<CifVO> getListCif() {
		return listCif;
	}

	public void setListCif(List<CifVO> listCif) {
		this.listCif = listCif;
	}

	public int getCifId() {
		return cifId;
	}

	public void setCifId(int cifId) {
		this.cifId = cifId;
	}

	public Cif getCif() {
		return cif;
	}

	public void setCif(Cif cif) {
		this.cif = cif;
	}

	public List<Account> getListAccount() {
		return listAccount;
	}

	public void setListAccount(List<Account> listAccount) {
		this.listAccount = listAccount;
	}
	
	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}
	
	public CifHistoryVO getCifHistoryVO() {
		return cifHistoryVO;
	}

	public void setCifHistoryVO(CifHistoryVO cifHistoryVO) {
		this.cifHistoryVO = cifHistoryVO;
	}

	public String getActionFrom() {
		return actionFrom;
	}

	public void setActionFrom(String actionFrom) {
		this.actionFrom = actionFrom;
	}

	public int getHasLogin() {
		return hasLogin;
	}

	public void setHasLogin(int hasLogin) {
		this.hasLogin = hasLogin;
	}

	public int getCifHistoryId() {
		return cifHistoryId;
	}

	public void setCifHistoryId(int cifHistoryId) {
		this.cifHistoryId = cifHistoryId;
	}



	/************************************** ESSENTIAL FOR SEARCH *******************************************/
	private String resultSearchJson;

	public void makeTableContent()
	{	
		prepareParamVO(new ProductParamVO(),  WEB_PARAM_KEY + WebModules.MENU_CUSTOMER_SUPPORT_RESET_PIN, 
				"c.id", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.cifDeviceCode"), getText("l.cifName"), getText("l.status")};
		String[] arrayBody={"deviceCode", "cifName", "mobileStatusDisplay"};
		String[] arrayDbVariable={"c.device_code", "c.cif_name", "c.status"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("ResetPin!detail.web", "deviceCode", new String[]{"cifId"}, new String[]{"id"}));
		
		try {
			CifParamVO cifParamVO = (CifParamVO) paramVO;
			cifParamVO.setAuthStatus(WebConstants.STAT_APPROVED);
			int totalRow = cifService.countCifForResetPin(cifParamVO);
			listCif = cifService.findCifForResetPin(cifParamVO);		
			Locale language = (Locale) session.get(WEB_LOCALE_KEY);
			resultSearchJson = webSearchResultService.composeSearchResult(getText("l.listCif"), arrayHeader, arrayBody, 
					arrayDbVariable, gson.toJson(listCif), getCurrentPage(), 
					totalRow, listLinkTable, language, listCif.size(), paramVO);
		} catch (MmbsWebException we) {
			LOG.error("Error while make table content: ", we);
		}
		
	}

	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new CifParamVO();
		return paramVO;
	}

	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}

	public String getConfirmPin() {
		return confirmPin;
	}

	public void setConfirmPin(String confirmPin) {
		this.confirmPin = confirmPin;
	}

	/************************************** ESSENTIAL FOR SEARCH *******************************************/

}
