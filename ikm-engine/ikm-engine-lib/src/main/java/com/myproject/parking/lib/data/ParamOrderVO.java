package com.myproject.parking.lib.data;

public class ParamOrderVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
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
