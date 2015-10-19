package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.ProductParamVO;
import com.jakarta.software.web.entity.Product;
import com.jakarta.software.web.entity.ProductValue;
import com.jakarta.software.web.entity.UserData;
import com.jakarta.software.web.mapper.ProductMapper;

@Service
public class ProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;

	
	public List<Product> selectProduct() {
		List<Product> listProduct = productMapper.selectProduct();
		if (listProduct == null) {
			listProduct = new ArrayList<Product>();
		}
		return listProduct;
	}

	@Transactional(rollbackFor = Exception.class)
	public WebResultVO insertOrUpdateProduct(Product product, UserDataLoginVO loginVO, Locale language) throws MmbsWebException {
		WebResultVO wrv = new WebResultVO();
		Date now = new Date();
		product.setCreatedBy(loginVO.getId());
		product.setCreatedOn(now);
		product.setUpdatedBy(loginVO.getId());
		product.setUpdatedOn(now);
		LOGGER.debug("validate properties product !");
		if(StringUtils.isEmpty(product.getProductCode())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.productCode", language)});
		}
		if(StringUtils.isEmpty(product.getProductName())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.productName", language)});
		}
		if(StringUtils.isEmpty(product.getInstitutionCode())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.institutionCode", language)});
		}
		product.setProductCode(product.getProductCode().replaceAll(" ", ""));
		
		if(product.getId()==0)
		{
			int duplicity =productMapper.checkDuplicateProduct(product);
			if(duplicity > 0){
				throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.productCode", language)});
			}
				
			try {				
				productMapper.insertProduct(product);
				wrv.setRc(0);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("t.product", language), messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);						
				
				/** SET TO USER ACTIVITY **/
				try {
					Product oriProduct=new Product();
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriProduct, product, loginVO.getId(), 
							WebConstants.ACT_MODULE_PRODUCT, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_PRODUCT, product.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/
				
			} catch (Exception e) {
				LOGGER.warn("Gagal insert product service :" + e);
				throw new MmbsWebException(e);
			}
		}
		else
		{
			try {
				Product oriProd=selectProductById(product.getId());
				if(!oriProd.getProductType().equals(product.getProductType()))
				{
					int duplicity =productMapper.checkDuplicateProduct(product);
					if(duplicity > 0){
						throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.productCode", language)});
					}
				}
				productMapper.updateProduct(product);
				
				/** SET TO USER ACTIVITY **/
				try {
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriProd, product, loginVO.getId(), 
							WebConstants.ACT_MODULE_PRODUCT, WebConstants.ACT_TYPE_UPDATE,
							WebConstants.ACT_TABLE_PRODUCT, product.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/

				
				wrv.setRc(0);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("t.product", language), messageService.getMessageFor("l.updated", language)}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);	
				wrv.setPath(WebConstants.PATH_UPDATE_PRODUCT);
			} catch (MmbsWebException mwe) {
				throw mwe;
			} catch (Exception e) {
				LOGGER.warn("Gagal update product service :" + e);
				throw new MmbsWebException(e);
			}
		}
		return wrv;
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateProduct(Product product) {
		try {
			productMapper.updateProduct(product);
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
		}
	}

	public List<Product> selectProductByParam(ProductParamVO productParamVO) {
		List<Product> listProduct = productMapper.selectProductByParam(productParamVO);
		if (listProduct == null) {
			listProduct = new ArrayList<Product>();
		}
		return listProduct;
	}

	public int countProductByParam(ProductParamVO productParamVO) {
		try {
			int count = productMapper.countProductByParam(productParamVO);
			return count;
		} catch (Exception e) {
			return 0;
		}
	}

	public Product selectProductById(int id) {
		Product product = productMapper.selectProductById(id);
		if (product == null) {
			product = new Product();
		}
		return product;
	}

	public List<ProductValue> selectProductValueByProductId(int productId) {
		List<ProductValue> listProductValue = productMapper.selectProductValueByProductId(productId);
		if (listProductValue == null) {
			listProductValue = new ArrayList<ProductValue>();
		}
		return listProductValue;
	}

	@Transactional(rollbackFor = Exception.class)
	public void insertProductValue(ProductValue productValue, UserDataLoginVO loginVO) {
		try {
			Date now =new Date();
			productValue.setCreatedBy(loginVO.getId());
			productValue.setCreatedOn(now);
			productValue.setUpdatedBy(loginVO.getId());
			productValue.setUpdatedOn(now);			
			productMapper.insertProductValue(productValue);
			
			/** SET TO USER ACTIVITY **/
			try {
				ProductValue oriProduct=new ProductValue();
				Collection<String> excludes = new ArrayList<String>();
				excludes.add("createdOn");
				excludes.add("createdBy");
				excludes.add("updatedOn");
				excludes.add("updatedBy");
				
				userActivityService.generateHistoryActivity(excludes, oriProduct, productValue, loginVO.getId(), 
						WebConstants.ACT_MODULE_PRODUCT, WebConstants.ACT_TYPE_INSERT,
						WebConstants.ACT_TABLE_PRODUCT_VALUE, productValue.getId());				
			} catch (Exception e) {
				LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
			}		
			/** SET TO USER ACTIVITY **/
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateProductValue(ProductValue productValue, UserDataLoginVO loginVO) {
		try {
			Date now =new Date();
			productValue.setUpdatedBy(loginVO.getId());
			productValue.setUpdatedOn(now);
			productMapper.updateProductValue(productValue);
			
			/** SET TO USER ACTIVITY **/
			try {
				ProductValue oriProduct=new ProductValue();
				Collection<String> excludes = new ArrayList<String>();
				excludes.add("createdOn");
				excludes.add("createdBy");
				excludes.add("updatedOn");
				excludes.add("updatedBy");
				
				userActivityService.generateHistoryActivity(excludes, oriProduct, productValue, loginVO.getId(), 
						WebConstants.ACT_MODULE_PRODUCT, WebConstants.ACT_TYPE_UPDATE,
						WebConstants.ACT_TABLE_PRODUCT_VALUE, productValue.getId());				
			} catch (Exception e) {
				LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
			}		
			/** SET TO USER ACTIVITY **/
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
		}
	}

	public List<ProductValue> selectProductValueByParam(ProductParamVO productParamVO) {
		List<ProductValue> listProductValue = productMapper.selectProductValueByParam(productParamVO);
		if (listProductValue == null) {
			listProductValue = new ArrayList<ProductValue>();
		}
		return listProductValue;
	}

	public int countProductValueByParam(ProductParamVO productParamVO) {
		try {
			int count = productMapper.countProductValueByParam(productParamVO);
			return count;
		} catch (Exception e) {
			return 0;
		}
	}

	public int countProductValueByProductValueAndProductId(int productId, int productValue) {
		try {
			int counter = productMapper.countProductValueByProductValueAndProductId(productId, productValue);
			return counter;
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public void deleteProductValue(int id){
		try {
			productMapper.deleteProductValue(id);
		} catch (Exception e) {
			LOGGER.warn("Fail To Delete Product Value Cause:" + e);
		}
	}

}