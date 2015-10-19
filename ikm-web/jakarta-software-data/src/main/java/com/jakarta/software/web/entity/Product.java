package com.jakarta.software.web.entity; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class Product implements java.io.Serializable {
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
	private String createdByDisplay;
	private String updatedByDisplay;
	private String statusDisplay;
	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
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

	public String getStatus(){
		return status; 
	} 

	public void setStatus(String status){
		this.status = status; 
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getCreatedByDisplay() {
		return createdByDisplay;
	}

	public void setCreatedByDisplay(String createdByDisplay) {
		this.createdByDisplay = createdByDisplay;
	}

	public String getUpdatedByDisplay() {
		return updatedByDisplay;
	}

	public void setUpdatedByDisplay(String updatedByDisplay) {
		this.updatedByDisplay = updatedByDisplay;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

}