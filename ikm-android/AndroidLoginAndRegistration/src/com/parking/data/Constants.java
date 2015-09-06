package com.parking.data;

public class Constants {
	public static final String PARKING_PREFERENCE = "PARKING_PREFERENCE";
	public static final String LOGIN_DATA_PREF 	  = "LoginDataPref";
	public static final String SETTING_DATA_PREF 	  = "SettingDataPref";
	public static final int SHOW_TIPS				= 3; // 3 kali
	public static final int SESSION_EXPIRED				= 11; // SESSION EXPIRED
	public static final int USER_NOT_LOGIN				= 17; // USER NOT LOGIN
	public static final int SESSION_DIFFERENT			= 18; // SESSION_DIFFERENT EXPIRED
	public static final int REDIRECT_DELAY_LOGIN		= 3000; 
	
	
	public static final int STATUS_NEED_TO_PAY								= 0	;
	public static final int STATUS_AUTO_RELEASE_AFTER_BOOKING				= 1	;
	public static final int STATUS_ALREADY_PAY								= 2	;
	public static final int STATUS_ALREADY_CHECK_IN							= 3	;
	public static final int STATUS_AUTO_RELEASE_AFTER_PAY					= 4	;
	public static final int STATUS_ALREADY_CHECK_OUT						= 5	;
	
	public static final String ADMIN 	  = "admin";
	public static final String STAFF 	  = "staff";
	public static final String USER 	  = "user";
	
	public static final String STATUS_VAL_AVAILABLE	  	  = "Tersedia";
	public static final String STATUS_VAL_NEED_TO_PAY	  = "Need to pay";
	public static final String STATUS_VAL_ALREADY_PAY	  = "Already Payed";
	public static final String STATUS_VAL_ALREADY_CHECK_IN	  = "Checked In";
	public static final String STATUS_VAL_EXPIRED_PAY	  = "Expired Pay";
	public static final String STATUS_VAL_EXPIRED_CHECK_IN	  = "Expired Check In";
	public static final String STATUS_VAL_EXPIRED_COMPLETE	  = "Complete";
}
