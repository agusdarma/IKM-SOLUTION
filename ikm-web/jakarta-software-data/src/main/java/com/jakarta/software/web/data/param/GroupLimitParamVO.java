package com.jakarta.software.web.data.param; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class GroupLimitParamVO extends ParamPagingVO {
	private static final long serialVersionUID = 1L; 

	private int id; 
	private String groupName; 

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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}