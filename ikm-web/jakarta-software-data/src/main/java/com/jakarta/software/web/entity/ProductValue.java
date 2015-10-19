package com.jakarta.software.web.entity; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class ProductValue implements java.io.Serializable {
	private static final long serialVersionUID = 1L; 

	private int id; 
	private int productId; 
	private int productValue; 
	private int createdBy; 
	private Date createdOn; 
	private int updatedBy; 
	private Date updatedOn; 
	private String createdByDisplay;
	private String updatedByDisplay;
	
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

	public int getProductId(){
		return productId; 
	} 

	public void setProductId(int productId){
		this.productId = productId; 
	}

	public int getProductValue(){
		return productValue; 
	} 

	public void setProductValue(int productValue){
		this.productValue = productValue; 
	}

	public int getCreatedBy(){
		return createdBy; 
	} 

	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy; 
	}

	public Date getCreatedOn(){
		return createdOn; 
	} 

	public void setCreatedOn(Date createdOn){
		this.createdOn = createdOn; 
	}

	public int getUpdatedBy(){
		return updatedBy; 
	} 

	public void setUpdatedBy(int updatedBy){
		this.updatedBy = updatedBy; 
	}

	public Date getUpdatedOn(){
		return updatedOn; 
	} 

	public void setUpdatedOn(Date updatedOn){
		this.updatedOn = updatedOn; 
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

	
}