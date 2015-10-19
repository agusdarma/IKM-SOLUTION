package com.jakarta.software.web.data.param; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class BranchDataParamVO extends ParamPagingVO {
	private static final long serialVersionUID = 1L; 

	private int id; 
	private String branchCode; 
	private String branchName; 
	private int branchStatus; 
	private int createdBy; 
	private Date createdOn; 
	private int updatedBy; 
	private Date updatedOn; 

	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	@Override
	protected String getPrimaryKey() {
		return "";
	}

/*	@Override
	protected String getAliasTable() {
		return "";
	}*/

	public int getId(){
		return id; 
	} 

	public void setId(int id){
		this.id = id; 
	}

	public String getBranchCode(){
		return branchCode; 
	} 

	public void setBranchCode(String branchCode){
		this.branchCode = branchCode; 
	}

	public String getBranchName(){
		return branchName; 
	} 

	public void setBranchName(String branchName){
		this.branchName = branchName; 
	}

	public int getBranchStatus(){
		return branchStatus; 
	} 

	public void setBranchStatus(int branchStatus){
		this.branchStatus = branchStatus; 
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

}