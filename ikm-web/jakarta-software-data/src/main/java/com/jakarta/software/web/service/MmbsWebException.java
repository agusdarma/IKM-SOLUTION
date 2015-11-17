package com.jakarta.software.web.service;

public class MmbsWebException extends Exception {
	private static final long serialVersionUID = 1L;
	
	
	public static final int NE_SENDING_EMAIL_FAILED	= 100;
	public static final int NE_AGENDA_DATE_EMPTY	= 1;
	
	public static final int NE_ENGINE_ERROR_MSG 	= 99998;
	public static final int NE_UNKNOWN_ERROR		= 99999;
	
	//TODO: later must revised all error code
	public static final int NE_MISSING_INPUT		= 10000; // {0}
	public static final int NE_SESSION_EXPIRED		= 10001;
	public static final int NE_INVALID_USER 		= 10002;
	public static final int NE_DATA_NOT_FOUND		= 10003;
	public static final int NE_USER_DATA_INACTIVE	= 10004;
	public static final int NE_USER_WRONG_PASSWORD	= 10005;
	public static final int NE_USER_DATA_LOCKED		= 10006;
	public static final int NE_USER_DATA_BLOCKED	= 10007;
	public static final int NE_USER_DATA_INVALID_PASS	= 10008;
	public static final int NE_USER_DATA_INVALID_LEVEL	= 10009;
	public static final int NE_CIF_INVALID_PHONE_NO		= 10010;
	public static final int NE_SETTING_EMPTY		= 10012;
	public static final int NE_DUPLICATE_DATA		= 10013;
	public static final int NE_INVALID_EMAIL_FORMAT	= 10014;
	public static final int NE_CIF_PIN_CONFIRM_NOT_SAME	= 10015;
	public static final int NE_CIF_PIN_NUMERIC	= 10016;
	public static final int NE_CIF_INVALID_LENGTH_PIN	= 10017;
	public static final int NE_ACCOUNT_NOT_AUTH	= 10018;
	public static final int NE_MSG_CODE_MAX	= 10019;
	public static final int NE_ISO_TYPE_NUMERIC	= 10020;
	public static final int NE_CIF_TERMINATED	= 10021;
	public static final int NE_CIF_PENDING	= 10022;
	public static final int NE_CIF_BLOCK	= 10023;
	public static final int NE_MUST_CHOOSE_MENU	= 10024;  // {0} type
	public static final int NE_USER_BRANCH_NOT_SAME_WITH_CIF = 10025;
	public static final int NE_CIF_PRIMARY_INDEX_MUST_HAVE_CARD_NO = 10026;
	public static final int NE_CIF_MUST_HAVE_MINIMUM_ONE_ACCOUNT = 10027;
	public static final int NE_CIF_MUST_HAVE_PRIMARY_INDEX = 10028;
	public static final int NE_SUPERVISOR_LOGIN_SAME = 10029;
	
	public static final int NE_SETTING_INVALID_TYPE = 10050;
	public static final int NE_USER_REGISTRATION_NOT_YET_AUTHORIZED	= 10051;
	public static final int NE_USER_REQUEST_NOT_YET_AUTHORIZED	= 10052;
	public static final int NE_MERCHANT_REGISTRATION_NOT_YET_AUTHORIZED	= 10053;
	public static final int NE_INVALID_MINIMUM_CHAR_LENGTH = 10060;
	
	// CONNECTION ERROR
	public static final int NE_INVALID_URI = 11000;
	public static final int NE_ERROR_RESPONSE_HOST = 11001;

	//PUSH REQUEST
	public static final int NE_MUST_FILL_GROUP_OR_MSISDN	= 12000;
	
	public static final int NE_PASSWORD_DIFFERENT	= 20000;  // newPassword != confirmPassword
	public static final int NE_INVALID_PASSWORD_LENGTH = 20001; // {0} invalid password length
	public static final int NE_PASSWORD_NEW_OLD_SAME	= 20002;  // newPassword != confirmPassword
	public static final int NE_NEW_CONF_PASS_DIFFERENT = 20003;
	public static final int NE_INVALID_PASS_FORMAT = 20004;
	public static final int NE_WRONG_OLD_PASSWORD = 20005;


	public static final int NE_USSD_MENU_EMPTY  = 30001; //ussd menu empty	
	public static final int NE_CANNOT_AUTH_CUST = 30002; //can't auth customer in same month
	

	
	public static final int NE_CARD_NO_EMPTY = 30007;
	public static final int NE_INVALID_LENGTH_INPUT = 30008;
	public static final int NE_CIF_ALREADY_TERMINATED = 30009;
	public static final int NE_CARD_NO_NUMERIC = 30010;
	public static final int NE_SUGGESTION_HAS_BEEN_SOLVE = 30011;
	public static final int NE_INVALID_TYPE_NUMERIC = 30012;
	
	public static final int NE_USSD_MENU_CANNOT_DELETE		= 40001; //


	private int errorCode;
	private String[] info;
	
	public MmbsWebException(Throwable t) {
		super("rc." + NE_UNKNOWN_ERROR, t);
		this.errorCode = NE_UNKNOWN_ERROR;
		this.info = new String[] { t.getMessage() };
	}
	
	
	public MmbsWebException() {
		super();
		this.errorCode = NE_UNKNOWN_ERROR;
	}
	
	public MmbsWebException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}
	
	public MmbsWebException(int errorCode, String[] info) {
		super();
		this.errorCode = errorCode;
		this.info = info;
	}
	
	public MmbsWebException(int errorCode, String info) {
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
