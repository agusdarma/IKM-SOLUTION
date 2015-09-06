package com.parking.data;



public class InqLoginResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private LoginData loginData;

	public LoginData getLoginData() {
		return loginData;
	}

	public void setLoginData(LoginData loginData) {
		this.loginData = loginData;
	}
	
	
	
}
