package com.jakarta.software.web.data.param;

public class ParamOrderVO {
	private String paramField;
	private boolean asc;
	
	public boolean isAsc() {
		return asc;
	}
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	public String getParamField() {
		return paramField;
	}
	public void setParamField(String paramField) {
		this.paramField = paramField;
	}
}
