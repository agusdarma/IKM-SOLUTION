package com.jakarta.software.web.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class WebConstants {	
	public static final String[] ARRAY_PAGING	= {"25", "50", "100"};
	public static final int DEFAULT_ROW_PER_PAGE	= 25;
	
	public static final String ACCOUNT_MODIFY_STATUS_NEW		= "N";
	public static final String ACCOUNT_MODIFY_STATUS_UNCHANGED	= "U";
	public static final String ACCOUNT_MODIFY_STATUS_CHANGED	= "C";
	public static final String ACCOUNT_MODIFY_STATUS_REMOVED	= "R";
	
	public static final Map<Integer, String> MAP_MONTH = new LinkedHashMap<Integer, String>();
	static {
		MAP_MONTH.put(1, "JANUARY");
		MAP_MONTH.put(2, "FEBRUARY");
		MAP_MONTH.put(3, "MARCH");
		MAP_MONTH.put(4, "APRIL");
		MAP_MONTH.put(5, "MEI");
		MAP_MONTH.put(6, "JUNE");
		MAP_MONTH.put(7, "JULY");
		MAP_MONTH.put(8, "AUGUST");
		MAP_MONTH.put(9, "SEPTEMBER");
		MAP_MONTH.put(10, "OCTOBER");
		MAP_MONTH.put(11, "NOVEMBER");
		MAP_MONTH.put(12, "DECEMBER");
	}
	
	public static final int STATUS_ACTIVE		= 1;
	public static final int STATUS_INACTIVE		= 2;
	
	public static final int STATUS_SEND_SMS_START	= 0;
	public static final int STATUS_SEND_SMS_PROCESS	= 1;
	public static final int STATUS_SEND_SMS_FINISH	= 2;
	public static final int STATUS_SEND_SMS_CANCEL	= 3;
	
	public static final int DB_FALSE			= 0;
	public static final int DB_TRUE				= 1;
	
	public static final int YES	= 1;
	public static final int NO = 0;
	
	public static final boolean DB_FALSE_BOOLEAN			= false;
	public static final boolean DB_TRUE_BOOLEAN				= true;
	
	public static final int FULL_ACCESS				= 1;
	public static final int READ_ONLY				= 0;
	
	public static final int DAILY				= 3;	
	public static final int WEEKLY				= 2;
	public static final int MONTHLY				= 1;
	
	public static final String SORT_ORDER_ASC	="ASC";
	public static final String SORT_ORDER_DESC	="DESC";
	
	public static final String DISPLAY_FORMAT_DATETIME 		= "dd-MMM-yyyy HH:mm:ss";
	public static final String DISPLAY_FORMAT_AMOUNT 		= "#,##0";
	public static final String DISPLAY_FORMAT_DATE 		= "dd-MMM-yyyy";
	
	public static final int LOGIN_TYPE_NONE				= 0;
	public static final int LOGIN_TYPE_ALLOWED_FIRST	= 1;
	public static final int LOGIN_TYPE_ALLOWED_LAST		= 2;
	
	public static final int	TYPE_INSERT		= 0;
	public static final int TYPE_UPDATE		= 1;
	
	public static final int RESULT_SUCCESS	= 0;
	
	public static final String CHAR_EMPTY_STR	= "";
	public static final String NULL	= null;
	public static final String CHAR_DOT			= ".";
	
	public static final String STAT_NO_NEED_AUTHORIZATION= "N";
	public static final String STAT_NOT_AUTHORIZED= "Y";
	
	public static final String STAT_APPROVED= "A";
	public static final String STAT_REJECTED= "R";
	
	//EMAIL
	public static final String EMAIL_FROM	= "MMBS MAIL";
	
	public static final int USE_RANDOMIZE_PASS_YES	= 1;
	public static final int USE_RANDOMIZE_PASS_NO	= 0;	
	
	public static final int PASS_COMPOSITION_ALPHA_LENGTH		= 4;
	public static final int PASS_COMPOSITION_NUMERIC_LENGTH		= 3;
	public static final int PASS_COMPOSITION_SPECIALCHAR_LENGTH	= 3;
	
	public static final String ACCOUNT_TYPE_TABUNGAN	= "10";
	
	//css default
	public static final String DEFAULT_THEME		= "Persian Blue";
	public static final String DEFAULT_FONTFAMILY	= "Utsaah Regular";
	public static final String DEFAULT_FONTSIZE		= "Medium";
	public static final String DEFAULT_LANGUAGE		= "English";		

	public static final String DEFAULT_CIF_LANGUAGE	= "EN";
	
	public static final int APP_FLOW_USER_DATA_AUTH	= 1; //1 CREATE OR UPDATE NEED AUTHORIZATION 0 NO NEED AUTHORIZATION
	
	/* mobile service at menu auth customer */
	public static final String M_ATM= "mATM";
	public static final String SMS_TEXT_PLAIN= "SMS Text Plain";
	public static final String WALLET= "Wallet";
	
	public static final String STAT_ACTIVE= "A";

	public static final String NEED_CHANGE_PASS="Y";
	public static final String NO_NEED_CHANGE_PASS="N";	
	
	public static final String NULL_STRING_PARAM	= "^";
	
	public static final String ACT_TYPE_LOGIN			= "Login";
	public static final String ACT_TYPE_LOGOFF			= "Logoff";
	public static final String ACT_TYPE_INSERT			= "Insert";
	public static final String ACT_TYPE_UPDATE			= "Update";
	public static final String ACT_TYPE_DELETE			= "Delete";
	public static final String ACT_TYPE_RESET_PASSWORD	= "Reset Password";
	public static final String ACT_TYPE_RESET_PIN		= "Reset Pin";
	public static final String ACT_TYPE_TERMINATE		= "Terminate";
	public static final String ACT_TYPE_REGISTER		= "Register";
	
	//module = menu, table = db table
	public static final String ACT_MODULE_LOGIN				="Login";
	public static final String ACT_MODULE_LOGOFF			="Logoff";
	public static final String ACT_MODULE_USER_LEVEL		="User Level";
	public static final String ACT_MODULE_USER_DATA			="User Data";
	public static final String ACT_MODULE_CHANGE_PASSWORD	="Change Password";
	public static final String ACT_MODULE_RESET_PASSWORD	="Reset Password";
	public static final String ACT_MODULE_USER_PREFERENCE	="User Preference";
	
	public static final String ACT_MODULE_PRODUCT			="Product";
	public static final String ACT_MODULE_CUSTOMER_INFO		="Customer Info";
	public static final String ACT_MODULE_MERCHANT			="Merchant";
	public static final String ACT_MODULE_RESET_PIN			="Reset Pin";	
	public static final String ACT_MODULE_TRX_LOG_BROWSER	="Transaction Log Browser";
	public static final String ACT_MODULE_PRINT_STRUK_PLN	="Print Struk PLN";
	
	public static final String ACT_MODULE_GROUP_MSISDN		="Group Msisdn";
	public static final String ACT_MODULE_SEND_SMS			="Send Sms";	
	
	public static final String ACT_MODULE_AUTH_MERCHANT		="Authorize Merchant"; 
	public static final String ACT_MODULE_AUTH_CIF			="Authorize Customer Info";
	public static final String ACT_MODULE_AUTH_USER_DATA	="Authorize User Data";
	
	public static final String ACT_MODULE_SYSTEM_SETTING	="System Setting";
	public static final String ACT_MODULE_TARIFF			="Tariff";
	public static final String ACT_MODULE_ACCESS_GROUP		="Access Group";
	public static final String ACT_MODULE_BILL_PAYMENT		="Bill Payment";
	public static final String ACT_MODULE_BANK				="Bank";
	public static final String ACT_MODULE_BRANCH_DATA		="Branch Data";
	public static final String ACT_MODULE_GROUP_LIMIT		="Group Limit";
	
	public static final String ACT_TABLE_USER_DATA	= "USER_DATA";
	public static final String ACT_TABLE_USER_LEVEL	= "USER_LEVEL";
	public static final String ACT_TABLE_USER_PREFERENCE="USER_PREFERENCE";
	public static final String ACT_TABLE_USER_DATA_HISTORY="USER_DATA_HISTORY";
	public static final String ACT_TABLE_LEVEL_MENU	= "LEVEL_MENU";
	public static final String ACT_TABLE_CIF	= "CIF";
	public static final String ACT_TABLE_CIF_HISTORY	= "CIF_HISTORY";
	public static final String ACT_TABLE_BANK	= "BANK";
	public static final String ACT_TABLE_BRANCH	= "BRANCH";
	public static final String ACT_TABLE_BP_ORGANIZATION= "BP_ORGANIZATION";
	public static final String ACT_TABLE_GROUP_LIMIT= "GROUP_LIMIT";
	public static final String ACT_TABLE_PRODUCT= "PRODUCT";
	public static final String ACT_TABLE_PRODUCT_VALUE= "PRODUCT_VALUE";
	public static final String ACT_TABLE_REGIONAL= "REGIONAL";
	public static final String ACT_TABLE_REGIONAL_BRANCH= "REGIONAL_BRANCH";
	public static final String ACT_TABLE_ACCOUNT= "ACCOUNT";
	public static final String ACT_TABLE_SUGGESTION= "SUGGESTION";
	public static final String ACT_TABLE_TRX_LIMIT= "TRX_LIMIT";
	public static final String ACT_TABLE_MERCHANT= "MERCHANT";
	public static final String ACT_TABLE_BRANCH_DATA ="BRANCH_DATA";
	public static final String ACT_TABLE_GROUP_MSISDN_HEADER = "GROUP_MSISDN_HEADER";
	public static final String ACT_TABLE_GROUP_MSISDN_DETAIL = "GROUP_MSISDN_DETAIL";	
	public static final String ACT_TABLE_PUSH_REQUEST_HEADER = "PUSH_REQUEST_HEADER";
	public static final String ACT_TABLE_GROUP_LIMIT_HEADER = "GROUP_LIMIT_HEADER";
	public static final String ACT_TABLE_GROUP_LIMIT_DETAIL = "GROUP_LIMIT_DETAIL";	
	
	public static final String PATH_UPDATE_USER_DATA		= "UserData!execute.web";
	public static final String PATH_UPDATE_AUTH_USER_DATA	= "AuthUserData!execute.web";
	public static final String PATH_UPDATE_USER_LEVEL		= "UserLevel!execute.web";
	public static final String PATH_UPDATE_CHANGE_PASSWORD	= "ChangePassword!execute.web";
	public static final String PATH_UPDATE_RESET_PASSWORD	= "ResetPassword!execute.web";
	public static final String PATH_UPDATE_SYSTEM_SETTING	= "SystemSetting!execute.web";
	public static final String PATH_UPDATE_PRODUCT			= "Product!execute.web";
	public static final String PATH_UPDATE_BANK				= "Bank!execute.web";
	public static final String PATH_UPDATE_BILL_PAYMENT		= "BillPayment!execute.web";
	public static final String PATH_UPDATE_MERCHANT			= "Merchant!execute.web";
	public static final String PATH_UPDATE_AUTH_MERCHANT	= "AuthMerchant!execute.web";
	public static final String PATH_UPDATE_CIF				= "CIF!execute.web";
	public static final String PATH_UPDATE_BRANCH_DATA		= "BranchData!execute.web";
	public static final String PATH_UPDATE_ACCESS_GROUP		= "AccessGroup!execute.web";
	public static final String PATH_UPDATE_GROUP_MSISDN		= "GroupMsisdn!execute.web";
	public static final String PATH_UPDATE_SEND_SMS			= "SendSms!execute.web";
	public static final String PATH_UPDATE_RESET_PIN		= "ResetPin!execute.web";
	public static final String PATH_LOGIN_EXPIRED			= "Login!expired.web";	
	public static final String PATH_UPDATE_AUTH_CIF			= "AuthCif!execute.web";
	public static final String PATH_UPDATE_GROUP_LIMIT		= "GroupLimit!execute.web";
	
	public static final String STATUS_CIF_ACTIVE		= "A";
	public static final String STATUS_CIF_BLOCK			= "B";
	public static final String STATUS_CIF_TERMINATE		= "T";
	
}
