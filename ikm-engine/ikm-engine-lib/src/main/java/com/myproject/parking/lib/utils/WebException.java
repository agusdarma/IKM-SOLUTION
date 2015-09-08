package com.myproject.parking.lib.utils;

import java.util.Arrays;

public class WebException extends Exception {
	private static final long serialVersionUID = 1L;	
	
	public static final int NE_UNKNOWN_ERROR			= 99999;
	
	public static final int NE_FAILED_CONNECT_TO_ENGINE	= 99998;
	public static final int NE_ERROR_SEQ_GEN			= 99997;
	public static final int NE_FAILED_PROCESS			= 99996;
	public static final int NE_CONNECTION_ERROR			= 99995;
	public static final int APPS_SETTING_NOT_FOUND		= 99994;
	public static final int APPS_INVALID_SETTING		= 99993;
	
			
	public static final int NE_MISSING_INPUT		= 10000; // {0}
	public static final int NE_SESSION_EXPIRED		= 10001;
	public static final int NE_INVALID_USER 		= 10002;
	public static final int NE_DATA_NOT_FOUND 		= 10003; // {0}=type,{1}=data
	public static final int NE_DATA_NOT_VALID 		= 10004;
	public static final int NE_DATA_DUPLICATE		= 10005; // {0}=type,{1}=data
	public static final int NE_INVALID_EMAIL 		= 10006; // {0}=email
	public static final int NE_ERROR_INSERT			= 10007; 	
	public static final int NE_DATA_HAVE_BEEN_INSERT= 10008; // {0}=type,{1}=data
	public static final int NE_DATA_NOT_FOUND_PARAM	= 10009; // {0}=type
	public static final int NE_TOO_MANY_RESULT_THIS_BRANCH	= 10010; // {0}=type
	
	
	public static final int NE_USER_DATA_INVALID 		= 10101;	// {0} = user code
	public static final int NE_USER_DATA_INACTIVE 		= 10102;	// {0} = user code
	public static final int NE_USER_DATA_BLOCKED 		= 10103;	// {0} = user code
	public static final int NE_USER_DATA_INVALID_PASS	= 10104;	// {0} = user code
	public static final int NE_USER_DATA_INVALID_ROLE	= 10105;	// {0} = user code
	public static final int NE_USER_DATA_LOCKED 		= 10106;
	public static final int NE_NO_MODULES_SELECTED		= 10107;
	public static final int NE_WRONG_CAPTCHA			= 10108;
	public static final int NE_USER_DATA_INVALID_OLD_PASS	= 10109;	// {0} = user code
	public static final int NE_DATA_NOT_ALLOW_SAME_WITH	= 10110;	// {0} = data 1, {1} = data 2
	public static final int NE_DATA_REK_DUPLICATE		= 10111; //{0} = no rek

	//for commons error
	public static final int NE_PASSWORD_DIFFERENT		= 20000;  // newPassword != confirmPassword
	public static final int NE_INVALID_PASSWORD_LENGTH 	= 20001; // {0} invalid password length
	public static final int NE_PIN_DIFFERENT			= 20002;  // newPin != confirmPin
	public static final int NE_INVALID_PIN_LENGTH 		= 20003; // {0} invalid pin length
	public static final int NE_OLD_PASSWORD_SAME		= 20004;  // newPassword = oldPassword
	public static final int NE_UNABLE_INSERT_DATA		= 20005;
	public static final int NE_UNABLE_UPDATE_DATA		= 20006;
	public static final int NE_INVALID_PASS_COMBINATION = 20007;
	public static final int NE_PASS_HAVE_USED_BEFORE 	= 20008;
	
	public static final int NE_MUST_SELECTED		 	= 30000;
	public static final int NE_INVALID_USER_BM		 	= 30001;
	public static final int NE_INVALID_BRANCH_BM		= 30002; //BM only have 1 branch
	public static final int NE_MUST_HAVE_PARAM			= 30003; //PIC have 1 Account
	public static final int NE_JOIN_NOT_FOUND			= 30003;
	public static final int NE_INVALID_POSITION_LEVEL	= 30004;
	public static final int NE_INVALID_BRANCH_PRODUCT	= 30005;		
	
	public static final String MYBATIS_TOO_MANY_RESULT = "org.apache.ibatis.exceptions.TooManyResultsException";
	
			
	
	private int errorCode;
	private String[] info;
		
	public WebException() {
		super("rc." +  NE_UNKNOWN_ERROR);
		this.errorCode = NE_UNKNOWN_ERROR;
	}
	
	public WebException(int errorCode) {
		super("rc." + errorCode);
		this.errorCode = errorCode;
	}
	
	public WebException(int errorCode, String[] info) {
		super("rc." + errorCode + ", info: " + Arrays.toString(info));
		this.errorCode = errorCode;
		this.info = info;
	}
	
	public WebException(int errorCode, String info) {
		super();
		this.errorCode = errorCode;
		this.info = new String[] {info};
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public String[] getInfo() {
		return info;
	}
	public boolean hasInfo() {
		return (info != null) && (info.length > 0);
	}
}
