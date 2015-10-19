package com.jakarta.software.web.data;

import java.util.Date;
import java.util.List;

public class GeneralStatusVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String updatedBy;
	private Date updatedOn;
	private String status;
	private String authStatus;
	private List<Integer> listId;
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public List<Integer> getListId() {
		return listId;
	}
	public void setListId(List<Integer> listId) {
		this.listId = listId;
	}
	
	
}
