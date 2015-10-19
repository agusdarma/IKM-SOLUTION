package com.jakarta.software.web.data.param; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UserActivityParamVO extends ParamPagingVO {
	private static final long serialVersionUID = 1L; 

	private int userDataId; 
	private String action; 
	private String moduleName; 
	private String targetTable; 

	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	@Override
	protected String getPrimaryKey() {
		return "";
	}
	
	public int getUserDataId() {
		return userDataId;
	}
	public void setUserDataId(int userDataId) {
		this.userDataId = userDataId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getTargetTable() {
		return targetTable;
	}
	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

}