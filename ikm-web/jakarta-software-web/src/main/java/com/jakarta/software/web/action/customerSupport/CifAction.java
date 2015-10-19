package com.jakarta.software.web.action.customerSupport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.AccountResponseVO;
import com.jakarta.software.web.data.CifHistoryVO;
import com.jakarta.software.web.data.CifResultVO;
import com.jakarta.software.web.data.CifVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.CifParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ProductParamVO;
import com.jakarta.software.web.entity.AccessGroupHeader;
import com.jakarta.software.web.entity.Account;
import com.jakarta.software.web.entity.AccountHistory;
import com.jakarta.software.web.entity.Cif;
import com.jakarta.software.web.entity.GroupLimitHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.AccessGroupService;
import com.jakarta.software.web.service.AccountService;
import com.jakarta.software.web.service.CifHistoryService;
import com.jakarta.software.web.service.CifService;
import com.jakarta.software.web.service.GroupLimitService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.SettingService;

public class CifAction extends BaseListAction implements ModuleCheckable {
	private static final long serialVersionUID 				= 1L;
	private static final Logger LOG 						= LoggerFactory.getLogger(CifAction.class);
	public static final String SESSION_ACCOUNT_CIF			= "ses_acc_cif";
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private AccessGroupService accessGroupService;
	
	@Autowired
	private CifService cifService;
	
	@Autowired
	private CifHistoryService cifHistoryService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private GroupLimitService groupLimitService;
	
	@Autowired
	private SettingService settingService;
	
	private WebResultVO wrv;
	private CifResultVO crv;
	
	private Cif cif;
	private List<CifVO> listCif;
	private String message;
	private String jsonMessage;
	private String json;
	
	private int cifId;
	private int popUpflag;
	private String deviceCode;
	private String accountOption;
	private String cardNo;
	private String accountNo;
	private String confirmPin;
	private int forcedAuthorize;
	private int cifHistoryId;	
	private String actionFrom; //to define forced auth cif path
	private int hasLogin;
	
	private CifHistoryVO cifHistoryVO;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

	//SAVE & INITIALIZATION
	public String execute() {
		setMessage(getFlashMessage());
		session.remove(SESSION_ACCOUNT_CIF);
		session.remove(WEB_CONTENT_KEY);
		session.remove(LOGIN_KEY_SUPERVISOR);
		return SEARCH;
	}

	public String gotoSearch() {
		session.remove(SESSION_ACCOUNT_CIF);
		session.remove(WEB_CONTENT_KEY);
		session.remove(LOGIN_KEY_SUPERVISOR);
		return SEARCH;
	}

	public String gotoInput() 
	{		
		sessionListAccount();
		return INPUT;
	}
	
	public String gotoInputTemp()
	{
		session.remove(WEB_CONTENT_KEY);
		session.remove(SESSION_ACCOUNT_CIF);
		session.remove(LOGIN_KEY_SUPERVISOR);
		return "inputTemp";
	}
	
	public String checkAccount() {
		getLogger().debug("Processing -> checkAccount()");
		try
		{
			Locale language = (Locale) session.get(WEB_LOCALE_KEY);
			crv = new CifResultVO();
			if(accountNo.length() !=  getSettingAccLength())
			{
				crv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
				crv.setErrorMessage(getText("l.accountMinimumLength", new String[]{Integer.toString(getSettingAccLength())}));
				Gson gson = new Gson();
				json = gson.toJson(crv);
				return "checkJson";
			}
			AccountResponseVO arv = new AccountResponseVO();
			int counter = accountService.checkAccountHistoryByAccNo(accountNo);
			if(counter!=0)
			{
				crv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
				crv.setErrorMessage(getText("l.accountInAuthProcess", new String[]{accountNo}));
				Gson gson = new Gson();
				json = gson.toJson(crv);
				return "checkJson";
			}
			else
			{
				counter = accountService.checkAccountByAccNo(accountNo);
				if(counter!=0)
				{
					crv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
					crv.setErrorMessage(getText("l.accountAlreadyExist", new String[]{accountNo}));
					Gson gson = new Gson();
					json = gson.toJson(crv);
					return "checkJson";
				}
			}

			//MOCKUP
			//arv = fillCifResponseVO(cardNo, accountNo);
			
			//ENGINE
			arv = cifService.inquiryCifByAccountNo(cardNo, accountNo, language);
			Object obj = session.get(SESSION_ACCOUNT_CIF);
			if(arv.getAccountNo()==null)
			{
				crv.setErrorMessage("Problem connecting to host, please contact your system administrator");
				crv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
				Gson gson = new Gson();
				json = gson.toJson(crv);
				return "checkJson";
			}
			if(StringUtils.isEmpty(arv.getErrorMessage()))
			{					
				if(obj!=null)
				{
					@SuppressWarnings("unchecked")
					List<Account> sessionAccount = (List<Account>) obj;
					
					int score = 0;
					for (Account sesAccount : cif.getListAccount()) 
					{
						if(sesAccount.getAccountNo().equals(arv.getAccountNo()))
						{
							sesAccount.setCardNo(cardNo);
							sesAccount.setRemarks(arv.getRemarks());
							score++;
						}
					}
					if(score==0)
					{
						Account acc = new Account();
						acc.setCardNo(arv.getCardNo());
						acc.setAccountNo(arv.getAccountNo());
						acc.setRemarks(arv.getRemarks());
						cif.getListAccount().add(acc);
					}					
					crv.setListAccount(cif.getListAccount());
					session.put(SESSION_ACCOUNT_CIF, cif.getListAccount());
				}
				else
				{
					List<Account> listAccount = new ArrayList<Account>();
					Account acc = new Account();
					acc.setCardNo(arv.getCardNo());
					acc.setAccountNo(arv.getAccountNo());
					acc.setRemarks(arv.getRemarks());
					listAccount.add(acc);					
					session.put(SESSION_ACCOUNT_CIF, listAccount);
					crv.setListAccount(listAccount);
				}
			}
			else
			{
				crv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
				crv.setErrorMessage(arv.getErrorMessage());
				Gson gson = new Gson();
				json = gson.toJson(crv);
				return "checkJson";
			}
		} catch (Exception e) {
			handleException(e);
			crv = new CifResultVO();
			crv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
			crv.setErrorMessage(e.getMessage());
		}
		crv.setResultCode(""+WebConstants.RESULT_SUCCESS);
		crv.setErrorMessage("");
		Gson gson = new Gson();
		json = gson.toJson(crv);
		return "checkJson";
	}
	
	
	//THIS IS ONLY MOCKUP
	private AccountResponseVO fillCifResponseVO(String cardNo, String accountNo){
		AccountResponseVO accRespVO = new AccountResponseVO();
		accRespVO.setAccountNo(accountNo);
		accRespVO.setCardNo(cardNo);
		accRespVO.setRemarks("Nama:Presiden, Profesi:Pemimpin Indonesia ");
		return accRespVO;
	}
	
	private void sessionListAccount()
	{
		if(cifId!=0)
		{
			Object objCif = session.get(WEB_CONTENT_KEY);
			if(objCif==null)
			{				
				try {
					cif = cifService.findCifById(cifId);
				} catch (MmbsWebException e) {
					cif = new Cif();
				}				
			}
			else
			{
				cif = (Cif) objCif;
			}
		}
		else
		{
			cif=new Cif();
		}
		Object obj = session.get(SESSION_ACCOUNT_CIF);
		if(obj!=null)
		{
			List<Account> sessionResponseVO = (List<Account>) obj;
			cif.setListAccount(sessionResponseVO);
		}		
	}
	
	public String detail() {
		// called when user needs to edit, to display input form
		try {
			Object obj = session.get(SESSION_ACCOUNT_CIF);
			if(obj==null)
			{
				cif = cifService.findCifById(cifId);
				getLogger().debug("Show detail CIF - > {}", cif);
				session.put(WEB_CONTENT_KEY, cif);
				session.put(SESSION_ACCOUNT_CIF,cif.getListAccount());
			}
			else
			{
				sessionListAccount();
			}			
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}
		
	public String forcedUrl(){		
		try{
			cifHistoryVO = cifHistoryService.findCifHistoryDetailById(cifHistoryId);
			List<AccountHistory> listAccountHistory = accountService.getListAccountHist(cifHistoryVO.getCifHistoryId());
			cifHistoryVO.setListAccountHistory(listAccountHistory);
			cifHistoryVO.setListAccount(accountService.getListAccount(cifHistoryVO.getCifId()));
			actionFrom=WebConstants.ACT_MODULE_CUSTOMER_INFO;
			hasLogin=0;
			session.put(WEB_CONTENT_KEY, cifHistoryVO);
			setMessage(getFlashMessage());
		} catch (Exception e) {
			cifHistoryVO = new CifHistoryVO();
		}
		return "cif_forced_auth";
	}

	public String processInput(){
		getLogger().debug("Processing -> processInput()");
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		try {
			wrv = cifService.createOrUpdate(cif, loginVO, language);
			if(wrv.getType()==WebConstants.TYPE_UPDATE)
			{
				setFlashMessage(wrv.getMessage());
			}
			session.remove(SESSION_ACCOUNT_CIF);
		} catch (MmbsWebException mwe) {
			wrv = handleJsonException(mwe);
		} catch (Exception e) {
			wrv = handleJsonException(e);
		}
		Gson gson = new Gson();
		json = gson.toJson(wrv);
		return "inputJson";
	}

	public String processSearch() {
		makeTableContent();
		return "searchJson";
	}
		
	@Override
	public int getMenuId() {
		return WebModules.MENU_CUSTOMER_SUPPORT_CIF;
	}

	@Override
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new CifParamVO();
		return paramVO;
	}

	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}

	public InputStream getCrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}

	public void setCrv(CifResultVO crv) {
		this.crv = crv;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJsonMessage() {
		return jsonMessage;
	}

	public void setJsonMessage(String jsonMessage) {
		this.jsonMessage = jsonMessage;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Cif getCif() {
		return cif;
	}

	public void setCif(Cif cif) {
		this.cif = cif;
	}

	public int getPopUpflag() {
		return popUpflag;
	}

	public void setPopUpflag(int popUpflag) {
		this.popUpflag = popUpflag;
	}
	
	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getAccountOption() {
		return accountOption;
	}

	public void setAccountOption(String accountOption) {
		this.accountOption = accountOption;
	}

	public int getCifId() {
		return cifId;
	}

	public void setCifId(int cifId) {
		this.cifId = cifId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getConfirmPin() {
		return confirmPin;
	}

	public void setConfirmPin(String confirmPin) {
		this.confirmPin = confirmPin;
	}

	// list status
	public List<Lookup> getListStatus() {
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_STATUS);
		return listStatus;
	}

	// list cif group
	public List<GroupLimitHeader> getListCifGroup() {
		List<GroupLimitHeader> listCifGroup = groupLimitService.findAllGroupLimit();				
		return listCifGroup;
	}

	// list account type
	public List<Lookup> getListAccountType() {
		List<Lookup> listAccountType = lookupService.findLookupByCat(LookupService.CAT_ACCOUNT_TYPE_DISPLAY);
		return listAccountType;
	}

	// list access group id
	public List<AccessGroupHeader> getListAccessId() {
		List<AccessGroupHeader> listStatus;
		try {
			listStatus = accessGroupService.findAllAccessGroup();
			return listStatus;
		} catch (MmbsWebException e) {
			LOG.error("Access Group empty!");
			return new ArrayList<AccessGroupHeader>();
		}
	}
	
	public int getForcedAuthorize() {
		return forcedAuthorize;
	}

	public void setForcedAuthorize(int forcedAuthorize) {
		this.forcedAuthorize = forcedAuthorize;
	}

	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	public int getSettingAccLength()
	{
		return settingService.getSettingAsInt(SettingService.SETTING_BANK_ACCOUNT_LENGTH); 
	}
	
	public int getCifHistoryId() {
		return cifHistoryId;
	}

	public void setCifHistoryId(int cifHistoryId) {
		this.cifHistoryId = cifHistoryId;
	}


	public CifHistoryVO getCifHistoryVO() {
		return cifHistoryVO;
	}

	public void setCifHistoryVO(CifHistoryVO cifHistoryVO) {
		this.cifHistoryVO = cifHistoryVO;
	}

	public int getHasLogin() {
		Object obj = session.get(LOGIN_KEY_SUPERVISOR);
		if(obj!=null)
		{
			hasLogin = 1;
		}
		return hasLogin;
	}

	public void setHasLogin(int hasLogin) {
		this.hasLogin = hasLogin;
	}

	public String getActionFrom() {
		return actionFrom;
	}

	public void setActionFrom(String actionFrom) {
		this.actionFrom = actionFrom;
	}

	public List<CifVO> getListCif() {
		return listCif;
	}

	public void setListCif(List<CifVO> listCif) {
		this.listCif = listCif;
	}
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{	
		prepareParamVO(new ProductParamVO(),  WEB_PARAM_KEY + WebModules.MENU_CUSTOMER_SUPPORT_CIF, 
				"c.id", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.cifDeviceCode"), getText("l.cifName"), 
				getText("l.registrationDate"), getText("l.cifGroup"), getText("l.status"), getText("l.authStatus")};
		String[] arrayBody={"deviceCode", "cifName", "createdOn", "groupDisplay", "mobileStatusDisplay", "authStatusDisplay"};
		String[] arrayDbVariable={"c.device_code", "c.cif_name", "c.created_on", "glh.group_name", "c.status", "c.auth_status"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("CIF!detail.web", "deviceCode", new String[]{"cifId"}, new String[]{"id"}));
		
		try {
			CifParamVO cifParamVO = (CifParamVO) paramVO;
			int totalRow = cifService.countByParam(cifParamVO);
			listCif = cifService.findListCifByParam(cifParamVO);		
			Locale language = (Locale) session.get(WEB_LOCALE_KEY);
			resultSearchJson = webSearchResultService.composeSearchResult(getText("l.listCif"), arrayHeader, arrayBody, 
					arrayDbVariable, gson.toJson(listCif), getCurrentPage(), 
					totalRow, listLinkTable, language, listCif.size(), paramVO);
		} catch (MmbsWebException we) {
			LOG.error("Error while make table content: ", we);
		}
		
	}
	
}
