package com.jakarta.software.web.data;


public class UserActivityVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String field;
	private String oldValue;
	private String newValue;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	

}
