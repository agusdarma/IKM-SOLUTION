package com.jakarta.software.web.data.param; 

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import java.util.Date;

public class GroupMsisdnParamVO extends ParamPagingVO {

	private String groupName;
	private String msisdn; 
	private int groupId; 

	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	@Override
	protected String getPrimaryKey() {
		return "";
	}

	public String getMsisdn(){
		return msisdn; 
	} 

	public void setMsisdn(String msisdn){
		this.msisdn = msisdn; 
	}

	public int getGroupId(){
		return groupId; 
	} 

	public void setGroupId(int groupId){
		this.groupId = groupId; 
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
}