package com.jakarta.software.web.action.setting; 

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.action.BaseListAction;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.BranchDataParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.UserDataParamVO;
import com.jakarta.software.web.entity.BranchData;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.BranchDataService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;

public class BranchDataAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(BranchDataAction.class);

	private int key;
	private String message;
	private BranchData branchData;
	private List<BranchData> listBranchData;
	private String json;
	private WebResultVO wrv;
	
	@Autowired
	private BranchDataService branchDataService;

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
		makeTableContent();
		return "searchJson";
	}
	
	public String gotoSearch(){
		return SEARCH;
	}

	public String gotoInput(){
		return INPUT;
	}

	public String processInput(){
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		try {
			wrv = branchDataService.insertOrUpdateBranchData(branchData, loginVO, language);
			if(wrv.getType()==WebConstants.TYPE_UPDATE)
			{
				setFlashMessage(wrv.getMessage());
			}
		} catch (MmbsWebException mwe) {
			wrv = handleJsonException(mwe);
		} catch (Exception e) {
			wrv = handleJsonException(e);
		}
		json = gson.toJson(wrv);
		return "inputJson";
		
	}

	public String finish(){
		addActionMessage(message);
		return SEARCH;
	}


	public String detail(){
		LOG.debug("Processing -> detail()");
		branchData=branchDataService.selectBranchDataById(key);
		if(branchData==null)
			branchData=new BranchData();
		return INPUT;
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

	public BranchData getBranchData(){
		if(branchData==null)
			branchData=new BranchData();
		return branchData;
	}

	public void setBranchData(BranchData branchData){
		this.branchData = branchData;
	}
	// list status
	public List<Lookup> getListStatus() {
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_LOGIN_USER);
		return listStatus;
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_SETTING_BRANCH_DATA;
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
	
	// list status
	public List<Lookup> getListBranchStatus() {
		List<Lookup> listStatus = lookupService.findLookupByCat(LookupService.CAT_LOGIN_USER);
		return listStatus;
	}
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new UserDataParamVO(), WEB_PARAM_KEY + WebModules.MENU_SETTING_BRANCH_DATA, 
				"b.id", WebConstants.SORT_ORDER_ASC);		
		String[] arrayHeader={getText("l.branchCode"), getText("l.branchName"), getText("l.branchStatus")};
		String[] arrayBody={"branchCode", "branchName", "branchStatus"};
		String[] arrayDbVariable={"b.branch_code", "b.branch_name", "b.branch_status"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("BranchData!detail.web", "branchCode", new String[]{"key"}, new String[]{"id"}));
		
		BranchDataParamVO branchDataParamVO = (BranchDataParamVO) paramVO;
		int totalRow = branchDataService.countBranchDataByParam(branchDataParamVO);
		listBranchData = branchDataService.selectBranchDataByParam(branchDataParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listUser"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listBranchData), getCurrentPage(), 
				totalRow, listLinkTable, language, listBranchData.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new BranchDataParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/

}