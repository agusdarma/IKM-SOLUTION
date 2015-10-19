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
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ParamPagingVO;
import com.jakarta.software.web.data.param.ProductParamVO;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.entity.Product;
import com.jakarta.software.web.entity.ProductValue;
import com.jakarta.software.web.helper.WebModules;
import com.jakarta.software.web.interceptor.ModuleCheckable;
import com.jakarta.software.web.service.LookupService;
import com.jakarta.software.web.service.MmbsWebException;
import com.jakarta.software.web.service.ProductService;

public class ProductAction extends BaseListAction implements ModuleCheckable{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ProductAction.class);
		
	@Autowired
	private ProductService productService;
		
	@Autowired
	private LookupService lookupService;
	
	private Product product;
	private List<Product> listProduct;
	private List<ProductValue> listProductValue;
	private WebResultVO wrv;
	
	private int productId;
	private int productValue;
	private int prodValId;
	private String message;
	private String jsonMessage;
	private String json;
	private String pv;
	
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
			wrv = productService.insertOrUpdateProduct(product, loginVO, language); 
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
		product = new Product();
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
			product = productService.selectProductById(productId);
			listProductValue=productService.selectProductValueByProductId(productId);
		} catch (Exception e) {
			handleException(e);
		}
		return INPUT;
	}
	
	public void setPv(String pv) {
		this.pv = pv;
	}

	public InputStream getPv() {
		return new ByteArrayInputStream(pv.toString().getBytes());
	}
	
	public InputStream getWrv() {
		return new ByteArrayInputStream(json.toString().getBytes());
	}
	
	public InputStream getJsonMessage(){
		return new ByteArrayInputStream(jsonMessage.toString().getBytes());
	}
	
	public String saveProductValue(){
		int checkDuplicate= productService.countProductValueByProductValueAndProductId(productId, productValue);
		if(checkDuplicate == 0)
		{
			UserDataLoginVO loginVO = (UserDataLoginVO) session.get(LOGIN_KEY);	
			ProductValue prodVal=new ProductValue();
			prodVal.setProductId(productId);
			prodVal.setProductValue(productValue);
			productService.insertProductValue(prodVal, loginVO);
			LOG.info("insert product value with param : " + prodVal);
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
		
	}
	
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
	}
	
	public String deleteProductValue(){
		productService.deleteProductValue(prodValId);
		List<ProductValue> lpv = productService.selectProductValueByProductId(productId);
		Gson gson=new Gson();
		pv=gson.toJson(lpv);
		return "addProductValueJson";
	}
	
	@Override
	public int getMenuId() {
		return WebModules.MENU_CUSTOMER_SUPPORT_PRODUCT;
	}

// SETTER GETTER AREA

	public List<Product> getListProduct() {
		return listProduct;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setListProduct(List<Product> listProduct) {
		this.listProduct = listProduct;
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
		
	public Product getProduct() {
		if(product==null)
		{
			product = new Product();
		}
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getProductValue() {
		return productValue;
	}

	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}

	
	public List<ProductValue> getListProductValue() {
		return listProductValue;
	}

	public void setListProductValue(List<ProductValue> listProductValue) {
		this.listProductValue = listProductValue;
	}


	public int getProdValId() {
		return prodValId;
	}

	public void setProdValId(int prodValId) {
		this.prodValId = prodValId;
	}

	public void setJsonMessage(String jsonMessage) {
		this.jsonMessage = jsonMessage;
	}


	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/	
	private String resultSearchJson;
	
	public void makeTableContent()
	{	
		prepareParamVO(new ProductParamVO(),  WEB_PARAM_KEY + WebModules.MENU_CUSTOMER_SUPPORT_PRODUCT, 
				"p.id", WebConstants.SORT_ORDER_ASC);
		String[] arrayHeader={getText("l.productCode"), getText("l.productName"), getText("l.institutionCode"),
				getText("l.status"), getText("l.createdOn"), getText("l.createdBy"),
				getText("l.updatedOn"), getText("l.updatedBy")};
		String[] arrayBody={"productCode", "productName", "institutionCode", 
				"status", "createdOn", "createdByDisplay", 
				"updatedOn", "updatedByDisplay"};
		String[] arrayDbVariable={"p.product_code", "p.product_name", "p.institution_code", 
				"p.status", "p.created_on", "ud1.user_name", 
				"p.updated_on", "ud2.user_name"};
		List<LinkTableVO> listLinkTable=new ArrayList<LinkTableVO>();
		listLinkTable.add(new LinkTableVO("Product!detail.web", "productCode", new String[]{"productId"}, new String[]{"id"}));
		
		ProductParamVO productParamVO = (ProductParamVO) paramVO;
		int totalRow = productService.countProductByParam(productParamVO);
		listProduct = productService.selectProductByParam(productParamVO);		
		Locale language =  (Locale) session.get(WEB_LOCALE_KEY);
		resultSearchJson=webSearchResultService.composeSearchResult(getText("l.listProduct"), arrayHeader, arrayBody, 
				arrayDbVariable, gson.toJson(listProduct), getCurrentPage(), 
				totalRow, listLinkTable, language, listProduct.size(), paramVO);
		
	}
	
	public ParamPagingVO getParamVO() {
		if (paramVO == null)
			paramVO = new ProductParamVO();
		return paramVO;
	}
	
	public InputStream getWsr() {
		return new ByteArrayInputStream(resultSearchJson.toString().getBytes());
	}
	
	/**************************************   ESSENTIAL FOR SEARCH  *******************************************/

}
