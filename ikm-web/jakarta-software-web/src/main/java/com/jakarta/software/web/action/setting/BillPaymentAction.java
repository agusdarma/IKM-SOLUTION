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
import com.jakarta.software.web.data.BillPaymentVO;
import com.jakarta.software.web.data.LinkTableVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.BillPaymentParamVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.BillPaymentService;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.utils.Constants;

public class BillPaymentAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(BillPaymentAction.class);

	@Autowired
	private BillPaymentService billPaymentService;
	
	@Autowired
	private LookupService lookupService;
	
	private int bpId;
	private BillPaymentVO billPayment;
	private List<BillPaymentVO> listBillPayment;
	
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
			wrv = billPaymentService.insertOrUpdateBillPayment(billPayment, loginVO, language);
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
			billPayment = billPaymentService.findBillPaymentById(bpId);
			session.put(WEB_CONTENT_KEY, billPayment);
			return INPUT;
		} catch (Exception e) {
			handleException(e);
		}
		return SEARCH;
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
	
	// list bill type
	public List<Lookup> getListBillType() {
		try {
			List<Lookup> listBillType = lookupService.findLookupByCat(LookupService.CAT_BILL_TYPE);
			return listBillType;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	// list iso type
	public List<Lookup> getListIsoType() {
		try {
			List<Lookup> listIsoType = lookupService.findLookupByCat(LookupService.CAT_ISO_TYPE);
			return listIsoType;
		} catch (Exception e) {
			getLogger().error("error: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	// list iso type
		public List<Lookup> getListBillPaymentCategory() {
			try {
				List<Lookup> listBpCategory = lookupService.findLookupByCat(LookupService.CAT_BILL_PAYMENT_CATEGORY);
				return listBpCategory;
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

	@Override
	public int getMenuId() {
		return WebModules.MENU_SETTING_BILL_PAYMENT;
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
	
	public BillPaymentVO getBillPayment() {
		Object obj = (BillPaymentVO) session.get(WEB_CONTENT_KEY);
		if(obj != null && obj instanceof BillPaymentVO){
			billPayment = (BillPaymentVO) obj;
		}
		if(billPayment==null)
		{
			billPayment = new BillPaymentVO();
		}
		return billPayment;
	}

	public void setBillPayment(BillPaymentVO billPayment) {
		this.billPayment = billPayment;
	}

	public List<BillPaymentVO> getListBillPayment() {
		return listBillPayment;
	}

	public void setListBillPayment(List<BillPaymentVO> listBillPayment) {
		this.listBillPayment = listBillPayment;
	}
	
	public int getBpId() {
		return bpId;
	}

	public void setBpId(int bpId) {
		this.bpId = bpId;
	}

	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{	
		prepareParamVO(new BillPaymentParamVO(), WEB_PARAM_KEY + WebModules.MENU_SETTING_BILL_PAYMENT,
				"bp.biller_no", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.billerCode"), getText("l.billerName"), getText("l.billerDesc"), 
				getText("l.status"), getText("l.createdOn"), getText("l.createdBy"), getText("l.updatedOn"), getText("l.updatedBy")};
		String[] arrayBody={"billerNo" , "billerName", "billerDesc", "status", "createdOn", "createdByDisplay","updatedOn", "updatedByDisplay"};		
		String[] arrayDbVariable={"bp.biller_no" , "bp.biller_name", "bp.biller_desc", "bp.status", "bp.created_on", "ud.user_name",
				"bp.updated_on", "ud2.user_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("BillPayment!detail.web", "billerNo", new String[]{"bpId"}, new String[]{"id"}));
		
		BillPaymentParamVO billPaymentParamVO = (BillPaymentParamVO) paramVO;
		int totalRow=0;
		try {
			totalRow = billPaymentService.countBillPaymentByParam(billPaymentParamVO);
			listBillPayment = billPaymentService.findBillPaymentByParam(billPaymentParamVO);
		} catch (MmbsWebException e) {
		}			
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listBillPayment"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listBillPayment), getCurrentPage(), 
				totalRow, listLinkTable, language, listBillPayment.size(), paramVO);
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new BillPaymentParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/
}
