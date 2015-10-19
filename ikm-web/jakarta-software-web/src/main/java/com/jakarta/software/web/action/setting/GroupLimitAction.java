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
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.GroupLimitParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ProductParamVO;
import com.jakarta.software.web.entity.GroupLimitDetail;
import com.jakarta.software.web.entity.GroupLimitHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.Product;
import com.jakarta.software.web.entity.ProductValue;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.GroupLimitService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.ProductService;

public class GroupLimitAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(GroupLimitAction.class);
		
	@Autowired
	private GroupLimitService groupLimitService;
		
	@Autowired
	private LookupService lookupService;
	
	private GroupLimitHeader groupLimitHeader;
	private GroupLimitDetail groupLimitDetail;
	private List<GroupLimitHeader> listGroupLimitHeader;
	private List<GroupLimitDetail> listGroupLimitDetail;
	private WebResultVO wrv;
	
	private int headerId;
	private int productValue;
	private int detailId;
	private String message;
	private String jsonMessage;
	private String json;
	private String groupLimitJson;
	private String listTrxCodeJson;
	
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
		getLogger().debug("Processing -> processInput()");
		UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
		Locale language=(Locale) session.get(WEB_LOCALE_KEY);
		try {
			wrv = groupLimitService.insertOrUpdateGroupLimitHeader(groupLimitHeader, loginVO, language); 
			if(wrv.getType()==1)
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

	public String finish(){
		addActionMessage(message);
		return SEARCH;
	}
	
	public String gotoInput() {
		groupLimitHeader = new GroupLimitHeader();
		return INPUT;
	}

	
	public String processSearch() {
		makeTableContent();
		return "searchJson";
	}
		
	public String detail() {
		getLogger().debug("Processing -> detail()");
		// called when user needs to edit, to display input form
		try {
			groupLimitHeader = groupLimitService.findGroupLimitHeaderById(headerId);
			listGroupLimitDetail=groupLimitService.findListGroupLimitDetailByHeaderId(headerId);
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}
	
	public void setGroupLimitJson(String groupLimitJson) {
		this.groupLimitJson = groupLimitJson;
	}

	public InputStream getPv() {
		return new ByteArrayInputStream(groupLimitJson.toString().getBytes());
	}
	
	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}
	
	public InputStream getJsonMessage(){
		return new ByteArrayInputStream(jsonMessage.toString().getBytes());
	}
	
	public String saveGroupLimitDetail(){
		int duplicity=0;
		if(groupLimitDetail.getId()==0)
		{
			duplicity = groupLimitService.checkDuplicateGroupDetail(groupLimitDetail.getTrxCode(), groupLimitDetail.getGroupLimitId());
		}
		else
		{
			GroupLimitDetail oriDetail=groupLimitService.findGroupLimitDetailById(groupLimitDetail.getId());
			if(!oriDetail.getTrxCode().equals(groupLimitDetail.getTrxCode()))
			{
				duplicity = groupLimitService.checkDuplicateGroupDetail(groupLimitDetail.getTrxCode(), groupLimitDetail.getGroupLimitId());
			}
		}
		
		if(duplicity == 0)
		{
			UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
			groupLimitService.insertOrUpdateGroupLimitDetail(groupLimitDetail, loginVO);
			List<GroupLimitDetail> gld = groupLimitService.findListGroupLimitDetailByHeaderId(groupLimitDetail.getGroupLimitId());
			Gson gson=new Gson();
			groupLimitJson=gson.toJson(gld);
			return "addGroupLimitDetailJson";
		}
		else
		{
			jsonMessage="duplicate-"+getText("rc.10013", new String[]{getText("l.trxCode")});
			return "duplicateProduct";
		}
		
	}
	
	public String deleteGroupLimitDetail()
	{
		groupLimitService.deleteGroupLimitDetail(detailId);
		List<GroupLimitDetail> gld = groupLimitService.findListGroupLimitDetailByHeaderId(headerId);
		Gson gson=new Gson();
		groupLimitJson=gson.toJson(gld);
		return "addGroupLimitDetailJson";
	}
	
	/*
	public String updateProductValue(){
		int checkDuplicate= productService.countProductValueByProductValueAndProductId(productId, productValue);
		if(checkDuplicate == 0)
		{
			UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
			ProductValue prodVal=new ProductValue();
			prodVal.setProductId(productId);
			prodVal.setProductValue(productValue);
			prodVal.setId(prodValId);
			productService.updateProductValue(prodVal, loginVO);
			LOG.info("update product value with param : " + prodVal);
			List<ProductValue> lpv = productService.selectProductValueByProductId(productId);
			Gson gson=new Gson();
			pv=gson.toJson(lpv);
			return "addProductValueJson";
		}
		else
		{
			jsonMessage="duplicate-"+getText("rc.10013", new String[]{getText("l.productValue")});
			return "duplicateProduct";
		}
	}*/
	
	public String deleteProductValue(){
		groupLimitService.deleteGroupLimitDetail(detailId);
		List<GroupLimitDetail> listDetail = groupLimitService.findListGroupLimitDetailByHeaderId(headerId);
		Gson gson=new Gson();
		groupLimitJson=gson.toJson(listDetail);
		return "addProductValueJson";
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_SETTING_GROUP_LIMIT;
	}

// SETTER GETTER AREA

	public String getMessage() {
		return message;
	}

	public int getHeaderId() {
		return headerId;
	}

	public void setHeaderId(int headerId) {
		this.headerId = headerId;
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

	public int getProductValue() {
		return productValue;
	}

	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public void setJsonMessage(String jsonMessage) {
		this.jsonMessage = jsonMessage;
	}

	public GroupLimitHeader getGroupLimitHeader() {
		return groupLimitHeader;
	}

	public void setGroupLimitHeader(GroupLimitHeader groupLimitHeader) {
		this.groupLimitHeader = groupLimitHeader;
	}

	public List<GroupLimitHeader> getListGroupLimitHeader() {
		return listGroupLimitHeader;
	}

	public void setListGroupLimitHeader(List<GroupLimitHeader> listGroupLimitHeader) {
		this.listGroupLimitHeader = listGroupLimitHeader;
	}

	public List<GroupLimitDetail> getListGroupLimitDetail() {
		return listGroupLimitDetail;
	}

	public void setListGroupLimitDetail(List<GroupLimitDetail> listGroupLimitDetail) {
		this.listGroupLimitDetail = listGroupLimitDetail;
	}

	// list trx code
	public List<Lookup> getListTrxCode() {
		try {
			List<Lookup> listTrxCode = lookupService.findLookupByCat(LookupService.CAT_LIMIT_TRF_CODE);
			return listTrxCode;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
		
	public GroupLimitDetail getGroupLimitDetail() {
		return groupLimitDetail;
	}

	public void setGroupLimitDetail(GroupLimitDetail groupLimitDetail) {
		this.groupLimitDetail = groupLimitDetail;
	}

	
	public String getListTrxCodeJson() {
		List<Lookup> listTrxCode = lookupService.findLookupByCat(LookupService.CAT_LIMIT_TRF_CODE);
		Gson gson=new Gson();
		listTrxCodeJson=gson.toJson(listTrxCode);
		return listTrxCodeJson;
	}

	public void setListTrxCodeJson(String listTrxCodeJson) {
		this.listTrxCodeJson = listTrxCodeJson;
	}

	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{	
		prepareParamVO(new GroupLimitParamVO(),  WEB_PARAM_KEY + WebModules.MENU_SETTING_GROUP_LIMIT, 
				"glh.id", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.groupName"), getText("l.createdOn"), getText("l.createdBy"),
				getText("l.updatedOn"), getText("l.updatedBy")};
		String[] arrayBody={"groupName", "createdOn", "createdByDisplay", 
				"updatedOn", "updatedByDisplay"};
		String[] arrayDbVariable={"glh.groupName", "glh.created_on", "ud1.user_name", 
				"glh.updated_on", "ud2.user_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("GroupLimit!detail.web", "groupName", new String[]{"headerId"}, new String[]{"id"}));
		
		GroupLimitParamVO groupLimitParamVO = (GroupLimitParamVO) paramVO;
		int totalRow = groupLimitService.countListGroupLimitHeaderByParam(groupLimitParamVO);
		listGroupLimitHeader = groupLimitService.findListGroupLimitHeaderByParam(groupLimitParamVO);	
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listGroupLimit"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listGroupLimitHeader), getCurrentPage(), 
				totalRow, listLinkTable, language, listGroupLimitHeader.size(), paramVO);
		
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new GroupLimitParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/

}
