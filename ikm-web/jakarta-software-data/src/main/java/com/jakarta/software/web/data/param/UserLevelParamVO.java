package com.jakarta.software.web.data.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UserLevelParamVO extends ParamPagingVO {

	private String levelName;
	
	@Override 
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	@Override
	protected String getPrimaryKey() {
		return "";
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
}
