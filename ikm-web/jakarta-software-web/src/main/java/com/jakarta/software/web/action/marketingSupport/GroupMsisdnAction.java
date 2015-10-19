package com.jakarta.software.web.action.marketingSupport;

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
import com.jakarta.software.web.data.GroupMsisdnHeaderVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.BankParamVO;
import com.jakarta.software.web.data.param.GroupMsisdnParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.entity.GroupMsisdnHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.GroupMsisdnService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;

public class GroupMsisdnAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(GroupMsisdnAction.class);

	@Autowired
	private GroupMsisdnService groupMsisdnService;
	
	@Autowired
	private LookupService lookupService;
	
	private GroupMsisdnHeader groupMsisdnHeader;
	private int groupId;
	private List<GroupMsisdnHeaderVO> listGroupMsisdnHeader;
	
	private WebResultVO wrv;
	private String json;
	private String message;
	private String msisdn;
	
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
			wrv = groupMsisdnService.insertGroupMsisdnHeader(groupMsisdnHeader, loginVO, language, msisdn);
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
		return "inputJson";
	}
	
	public String detail(){
		getLogger().debug("Processing -> detail()");
		try {
			groupMsisdnHeader = groupMsisdnService.selectGroupMsisdnById(groupId);
			msisdn=groupMsisdnService.selectMsisdnByGroupId(groupId);
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
		return WebModules.MENU_MARKETING_SUPPORT_GROUP_MSISDN;
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
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public GroupMsisdnHeader getGroupMsisdnHeader() {
		if(groupMsisdnHeader==null)
			groupMsisdnHeader = new GroupMsisdnHeader();
		return groupMsisdnHeader;
	}

	public void setGroupMsisdnHeader(GroupMsisdnHeader groupMsisdnHeader) {
		this.groupMsisdnHeader = groupMsisdnHeader;
	}

	public List<GroupMsisdnHeaderVO> getListGroupMsisdnHeader() {
		return listGroupMsisdnHeader;
	}

	public void setListGroupMsisdnHeader(List<GroupMsisdnHeaderVO> listGroupMsisdnHeader) {
		this.listGroupMsisdnHeader = listGroupMsisdnHeader;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new BankParamVO(), WEB_PARAM_KEY + WebModules.MENU_MARKETING_SUPPORT_GROUP_MSISDN,
				"gmh.id", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.name"), getText("l.remarks"), getText("l.createdOn"), getText("l.createdBy"), 
				getText("l.updatedOn"), getText("l.updatedBy")};
		String[] arrayBody={"groupName", "groupRemarks", "createdOn", "createdByDisplay","updatedOn", "updatedByDisplay"};		
		String[] arrayDbVariable={"gmh.group_name", "gmh.group_remarks", "gmh.created_on", "ud1.user_name",
				"gmh.updated_on", "ud2.user_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("GroupMsisdn!detail.web", "groupName", new String[]{"groupId"}, new String[]{"id"}));

		GroupMsisdnParamVO groupMsisdnParamVO = (GroupMsisdnParamVO) paramVO;
		int totalRow=0;
		totalRow = groupMsisdnService.countGroupMsisdnHeaderByParam(groupMsisdnParamVO);
		listGroupMsisdnHeader = groupMsisdnService.selectGroupMsisdnHeaderByParam(groupMsisdnParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listGroupMsisdn"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listGroupMsisdnHeader), getCurrentPage(), 
				totalRow, listLinkTable, language, listGroupMsisdnHeader.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new GroupMsisdnParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/

}
