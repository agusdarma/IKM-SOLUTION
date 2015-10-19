package com.jakarta.software.web.data;

import com.jakarta.software.web.entity.Merchant;

public class MerchantVO extends Merchant {
	private static final long serialVersionUID = 1L;
	
	private int merchantId;
	private String currencyTypeDisplay;
	private String accountTypeDisplay;
	private String merchantStatusDisplay; 
	private String authStatusDisplay;
	private String createdByDisplay;
	private String updatedByDisplay;
    private String activityType;
	
	public String getAuthStatusDisplay() {
		return authStatusDisplay;
	}
	public void setAuthStatusDisplay(String authStatusDisplay) {
		this.authStatusDisplay = authStatusDisplay;
	}
	public String getCreatedByDisplay() {
		return createdByDisplay;
	}
	public void setCreatedByDisplay(String createdByDisplay) {
		this.createdByDisplay = createdByDisplay;
	}
	public String getUpdatedByDisplay() {
		return updatedByDisplay;
	}
	public void setUpdatedByDisplay(String updatedByDisplay) {
		this.updatedByDisplay = updatedByDisplay;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getMerchantStatusDisplay() {
		return merchantStatusDisplay;
	}
	public void setMerchantStatusDisplay(String merchantStatusDisplay) {
		this.merchantStatusDisplay = merchantStatusDisplay;
	}
	public String getAccountTypeDisplay() {
		return accountTypeDisplay;
	}
	public void setAccountTypeDisplay(String accountTypeDisplay) {
		this.accountTypeDisplay = accountTypeDisplay;
	}
	public String getCurrencyTypeDisplay() {
		return currencyTypeDisplay;
	}
	public void setCurrencyTypeDisplay(String currencyTypeDisplay) {
		this.currencyTypeDisplay = currencyTypeDisplay;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

}
