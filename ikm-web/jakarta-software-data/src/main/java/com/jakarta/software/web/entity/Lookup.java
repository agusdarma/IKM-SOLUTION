package com.jakarta.software.web.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Lookup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int LOOKUP_CAT_OPERATOR			= 1;
	public static final int LOOKUP_CAT_TRX_TYPE			= 2;
	public static final int LOOKUP_CAT_TRX_CLIENT		= 3;
	public static final int LOOKUP_CAT_COMMAND_TYPE		= 4;

	private int lookupCat;
	private String lookupValue;
	private int orderIndex;
	private String lookupDesc;

	public int getLookupCat() {
		return lookupCat;
	}

	public void setLookupCat(int lookupCat) {
		this.lookupCat = lookupCat;
	}

	public String getLookupDesc() {
		return lookupDesc;
	}

	public void setLookupDesc(String lookupDesc) {
		this.lookupDesc = lookupDesc;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getLookupValue() {
		return lookupValue;
	}

	public void setLookupValue(String lookupValue) {
		this.lookupValue = lookupValue;
	}

	
}
