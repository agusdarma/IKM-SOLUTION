package com.jakarta.software.web.data.param;

import com.jakarta.software.web.data.ViewPagingParamVO;

public class CifAuthParamVO {
	/* for auth cif */
	private String userLogin;
	private String branchCode;
	private String mobilePhone;
	private ViewPagingParamVO paramPagingVO;


	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public ViewPagingParamVO getParamPagingVO() {
		return paramPagingVO;
	}

	public void setParamPagingVO(ViewPagingParamVO paramPagingVO) {
		this.paramPagingVO = paramPagingVO;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	
}
