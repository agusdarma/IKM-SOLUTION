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
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.PushRequestVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.BankParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.PushRequestParamVO;
import com.jakarta.software.web.entity.GroupMsisdnHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.PushRequestHeader;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.GroupMsisdnService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.PushRequestService;

public class SendSmsAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SendSmsAction.class);

	@Autowired
	private PushRequestService pushRequestService;
	
	@Autowired
	private GroupMsisdnService groupMsisdnService; 
	
	@Autowired
	private LookupService lookupService;
	
	private PushRequestHeader pushRequestHeader;
	private int groupId;
	private List<PushRequestVO> listPushRequest;
	
	private WebResultVO wrv;
	private String json;
	private String message;
	private String msisdn;
	private int requestId;
	
	public String execute(){ 
		setMessage(getFlashMessage());
		session.remove(WEB_CONTENT_KEY);
		return SEARCH;
	}
	
	public String processSearch(){
		makeTableContent();
		return "searchJson";
	}
	
	public String processInput(){
		getLogger().debug("Processing -> processInput()");
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);		
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		try {
			wrv = pushRequestService.insertPushRequest(pushRequestHeader, loginVO, language, msisdn);
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
			pushRequestHeader = pushRequestService.selectPushRequestHeaderById(requestId);
			msisdn=pushRequestService.selectRequestDetailByHeaderId(requestId);
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
			getLogger().error("Error while get list switching: ", e);
			return new ArrayList<Lookup>();
		}
	}
	
	@Override
	protected Logger getLogger() {		
		return LOG;
	}

	@Override
	public int getMenuId() {
		return WebModules.MENU_MARKETING_SUPPORT_SEND_SMS;
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

	public PushRequestHeader getPushRequestHeader() {
		if(pushRequestHeader==null)
		{
			pushRequestHeader = new PushRequestHeader();
			return pushRequestHeader;
		}
		return pushRequestHeader;
	}

	public void setPushRequestHeader(PushRequestHeader pushRequestHeader) {
		this.pushRequestHeader = pushRequestHeader;
	}

	public List<PushRequestVO> getListPushRequest() {
		return listPushRequest;
	}

	public void setListPushRequest(List<PushRequestVO> listPushRequest) {
		this.listPushRequest = listPushRequest;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public List<GroupMsisdnHeader> getListGroupMsisdn()
	{
		return groupMsisdnService.selectGroupMsisdnHeader();
	}
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{
		prepareParamVO(new BankParamVO(), WEB_PARAM_KEY + WebModules.MENU_MARKETING_SUPPORT_SEND_SMS,
				"prh.id", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.sendTime"), getText("l.remarks"), getText("l.groupPhoneNo"), 
				getText("l.createdOn"), getText("l.createdBy"), getText("l.updatedOn"), getText("l.updatedBy")};
		String[] arrayBody={"sentTime", "message", "groupName", "createdOn", "createdByDisplay","updatedOn", "updatedByDisplay"};		
		String[] arrayDbVariable={"prh.sent_time", "prh.message", "gmh.group_name", "prh.created_on", "ud1.user_name",
				"prh.updated_on", "ud2.user_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("SendSms!detail.web", "sentTime", new String[]{"requestId"}, new String[]{"id"}));

		PushRequestParamVO pushRequestParamVO = (PushRequestParamVO) paramVO;
		int totalRow=0;
		totalRow = pushRequestService.countPushRequestHeaderByParam(pushRequestParamVO);
		listPushRequest = pushRequestService.selectPushRequestHeaderByParam(pushRequestParamVO);
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listSentSms"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listPushRequest), getCurrentPage(), 
				totalRow, listLinkTable, language, listPushRequest.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new PushRequestParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/

}
