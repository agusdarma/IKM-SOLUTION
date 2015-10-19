package com.jakarta.software.web.entity; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class GroupMsisdnHeader implements java.io.Serializable {
	private static final long serialVersionUID = 1L; 

	private int id; 
	private String groupName; 
	private String groupRemarks; 
	private Date createdOn; 
	private int createdBy; 
	private Date updatedOn; 
	private int updatedBy; 

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupRemarks() {
		return groupRemarks;
 	}

	public void setGroupRemarks(String groupRemarks) {
		this.groupRemarks = groupRemarks;
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

}