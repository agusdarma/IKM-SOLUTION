package com.jakarta.software.web.data.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MerchantParamVO extends ParamPagingVO {
	private String merchantCode;
	private String merchantName;
	private int updatedBy;  // updated_by INT NOT NULL,
	private String authStatus;
	private int branchId;
	private String status;
	
	@Override
	protected String getPrimaryKey() {
		return null;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
