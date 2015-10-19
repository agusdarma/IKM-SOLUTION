package com.jakarta.software.web.data.param; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class ProductParamVO extends ParamPagingVO {
	private static final long serialVersionUID = 1L; 

	private int id; 
	private String productType; 
	private String productCode; 
	private String productName; 
	private Date createdOn; 
	private int createdBy; 
	private Date updatedOn; 
	private int updatedBy; 
	private String institutionCode; 
	private String status; 

	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	@Override
	protected String getPrimaryKey() {
		return "";
	}

	public int getId(){
		return id; 
	} 

	public void setId(int id){
		this.id = id; 
	}

	public String getProductType(){
		return productType; 
	} 

	public void setProductType(String productType){
		this.productType = productType; 
	}

	public String getProductCode(){
		return productCode; 
	} 

	public void setProductCode(String productCode){
		this.productCode = productCode; 
	}

	public String getProductName(){
		return productName; 
	} 

	public void setProductName(String productName){
		this.productName = productName; 
	}

	public Date getCreatedOn(){
		return createdOn; 
	} 

	public void setCreatedOn(Date createdOn){
		this.createdOn = createdOn; 
	}

	public int getCreatedBy(){
		return createdBy; 
	} 

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy; 
	}

	public Date getUpdatedOn(){
		return updatedOn; 
	} 

	public void setUpdatedOn(Date updatedOn){
		this.updatedOn = updatedOn; 
	}

	public int getUpdatedBy(){
		return updatedBy; 
	} 

	public void setUpdatedBy(int updatedBy){
		this.updatedBy = updatedBy; 
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}
	
	public String getStatus(){
		return status; 
	} 

	public void setStatus(String status){
		this.status = status; 
	}

}