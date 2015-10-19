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
import com.jakarta.software.web.data.CifHistoryVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.LoginData;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.CifHistoryParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.entity.AccountHistory;
import com.jakarta.software.web.entity.Cif;
import com.jakarta.software.web.entity.UserLevel;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.AccountService;
import com.jakarta.software.web.service.CifHistoryService;
import com.jakarta.software.web.service.CifService;
import com.jakarta.software.web.service.UserDataService;
import com.jakarta.software.web.service.UserLevelService;

public class AuthCifAction extends BaseListAction implements ModuleCheckable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(AuthCifAction.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserLevelService userLevelService;

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private CifHistoryService cifHistoryService;
	
	@Autowired
	private CifService cifService;

	private CifHistoryVO cifHistoryVO;
	private Cif cifHistory;
	private Cif cif;

	private List<CifHistoryParamVO> listCifHisParamVO;

	private WebResultVO wrv;

	@SuppressWarnings("unused")
	private String tableId; // table id, used in JMesa
	private boolean isExport;
	// private int userId;

	private int cifHistoryId;
	
	private String message;
	private String json;
	private String mode;
	private String resultMessage;
	private String actionFrom; //to define forced auth cif path
	private List<AccountHistory> listAccHist;
	
	private LoginData loginData;
	
	@Override
	protected Logger getLogger() {
		return LOG;
	}

	// SAVE & INITIALIZATION
	public String execute() {
		setMessage(getFlashMessage());
		return SEARCH;
	}

	public String processAuthorize() 
	{
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		Locale language = (Locale) session.get(WEB_LOCALE_KEY);
		cifHistoryVO = (CifHistoryVO) session.get(WEB_CONTENT_KEY);
		LOG.debug("mode = " + mode + ", cifHistoryVO : " + cifHistoryVO);
		cifHistoryVO.setAuthStatus(mode);
		cifHistoryId = cifHistoryVO.getCifHistoryId();
		LOG.debug("cif: " + cifHistoryVO);
		try {
			wrv = cifService.authorizeCifData(cifHistoryVO, loginVO, language);			
			if(actionFrom.equals(WebConstants.ACT_MODULE_CUSTOMER_INFO))
			{
				wrv.setPath(WebConstants.PATH_UPDATE_CIF);
			}
			else if(actionFrom.equals(WebConstants.ACT_MODULE_RESET_PIN))
			{
				wrv.setPath(WebConstants.PATH_UPDATE_RESET_PIN);
			}
			else
			{
				wrv.setPath(WebConstants.PATH_UPDATE_AUTH_CIF);
			}
			if (wrv.getType() == WebConstants.TYPE_UPDATE) {				
				setFlashMessage(wrv.getMessage());
			}
		} catch (Exception e) {
			wrv = handleJsonException(e);
		}
		Gson gson = new Gson();
		json = gson.toJson(wrv);
		return "json";
	}
	
	public String gotoInput() {
		return INPUT;
	}

	public String processSearch() {
		makeTableContent();
		return "searchJson";
	}

	public String gotoSearch() {
		return SEARCH;
	}

	public String detail() {
		getLogger().debug("Processing -> detail()");
		try {
			cifHistoryVO = cifHistoryService.findCifHistoryDetailById(cifHistoryId);
			List<AccountHistory> listAccountHistory = accountService.getListAccountHist(cifHistoryVO.getCifHistoryId());
			cifHistoryVO.setListAccountHistory(listAccountHistory);
			cifHistoryVO.setListAccount(accountService.getListAccount(cifHistoryVO.getCifId()));
			session.put(WEB_CONTENT_KEY, cifHistoryVO);
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}


	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}

	// GET DROPDOWNLIST CONTENT
	public List<UserLevel> getListUserLevel() {
		List<UserLevel> listUserLevel = userLevelService.getAllUserLevel();
		return listUserLevel;
	}

	@Override
	public int getMenuId() {
		return WebModules.MENU_SUPERVISOR_AUTH_USER_DATA;
	}

	// SETTER GETTER AREA
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public boolean isExport() {
		return isExport;
	}
	
	public CifHistoryVO getCifHistoryVO() {
		return cifHistoryVO;
	}

	public void setCifHistoryVO(CifHistoryVO cifHistoryVO) {
		this.cifHistoryVO = cifHistoryVO;
	}

	public List<CifHistoryParamVO> getListCifHisParamVO() {
		return listCifHisParamVO;
	}

	public int getCifHistoryId() {
		return cifHistoryId;
	}

	public void setCifHistoryId(int cifHistoryId) {
		this.cifHistoryId = cifHistoryId;
	}

	public void setListCifHisParamVO(List<CifHistoryParamVO> listCifHisParamVO) {
		this.listCifHisParamVO = listCifHisParamVO;
	}

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

	public void setWrv(WebResultVO wrv) {
		this.wrv = wrv;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public List<AccountHistory> getListAccHist() {
		return listAccHist;
	}

	public void setListAccHist(List<AccountHistory> listAccHist) {
		this.listAccHist = listAccHist;
	}

	public LoginData getLoginData() {
		return loginData;
	}

	public void setLoginData(LoginData loginData) {
		this.loginData = loginData;
	}

	public String getActionFrom() {
		return actionFrom;
	}

	public void setActionFrom(String actionFrom) {
		this.actionFrom = actionFrom;
	}

	/************************************** ESSENTIAL FOR SEARCH *******************************************/
	private String resultSearchJson;

	public void makeTableContent() {
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);
		prepareParamVO(new CifHistoryParamVO(), WEB_PARAM_KEY
				+ WebModules.MENU_SUPERVISOR_AUTH_USER_DATA, "ch.id",
				WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader = { getText("l.phoneNo"), getText("l.userName"), getText("l.requestType"), getText("l.accessName"),
				getText("l.groupName")};
		String[] arrayBody = { "deviceCode", "cifName", "activityType", "accessName",
				"cifGroup"};
		String[] arrayDbVariable = { "ch.device_code", "ch.host_cif_id", "ch.activity_type", "agh.access_name",
				"glh.group_name"};
		List<LinkTableVO> listLinkTable = new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("AuthCif!detail.web", "deviceCode",
				new String[] { "cifHistoryId" }, new String[] { "id" }));// jadiin
																			// constructor
																			// entity
		CifHistoryParamVO cifHisParamVO = (CifHistoryParamVO) paramVO;
		cifHisParamVO.setUpdatedBy(loginVO.getId());
		cifHisParamVO.setBranchId(loginVO.getBranchId());
		cifHistoryId = cifHisParamVO.getId();
		LOG.debug("UpdatedBy = " + loginVO.getId() + ", BranchId : "
				+ loginVO.getBranchId());
		int totalRow = cifHistoryService.countCifHistoryByParam(cifHisParamVO);
		listCifHisParamVO = cifHistoryService.findCifHistoryByParam(cifHisParamVO);

		Locale language = (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson = webSearchResultService.composeSearchResult(
				getText("l.listCif"), arrayHeader, arrayBody, arrayDbVariable,
				gson.toJson(listCifHisParamVO), getCurrentPage(), totalRow,
				listLinkTable, language, listCifHisParamVO.size(), paramVO);
	}

	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new CifHistoryParamVO();
		return paramVO;
	}

	public Cif getCifHistory() {
		return cifHistory;
	}

	public void setCifHistory(Cif cifHistory) {
		this.cifHistory = cifHistory;
	}

	public Cif getCif() {
		return cif;
	}

	public void setCif(Cif cif) {
		this.cif = cif;
	}

	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}

	/************************************** ESSENTIAL FOR SEARCH *******************************************/

}
