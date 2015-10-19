package com.jakarta.software.web.mapper; 

import java.util.List;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.jakarta.software.web.data.param.ProductParamVO;
import com.jakarta.software.web.entity.Product;
import com.jakarta.software.web.entity.ProductValue;

public interface ProductMapper {

	public void insertProduct(Product product);

	public List<Product> selectProduct();

	public List<Product> selectProductByParam(ProductParamVO productParamVO);

	public int countProductByParam(ProductParamVO productParamVOs);

	public void updateProduct(Product product);

	public Product selectProductById(int id);

	public void insertProductValue(ProductValue productvalue);

	public List<ProductValue> selectProductValueByProductId(int productId);

	public List<ProductValue> selectProductValueByParam(ProductParamVO productvalueParamVO);

	public int countProductValueByParam(ProductParamVO productvalueParamVOs);

	public void updateProductValue(ProductValue productvalue);

	public int countProductValueByProductValueAndProductId(@Param ("productId") int productId, @Param ("productValue") int productValue);
	
	public void deleteProductValue(int id);
	
	public int checkDuplicateProduct(Product product);
}