package com.myproject.parking.lib.service;

import java.util.Arrays;

public class ParkingEngineException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final int ENGINE_UNKNOWN_ERROR			= 99;
	public static final int ENGINE_USER_NOT_FOUND			= 10;
	public static final int ENGINE_SESSION_KEY_EXPIRED		= 11;
	public static final int ENGINE_USER_ALREADY_ACTIVATED	= 12;
	public static final int ENGINE_USER_BLOCKED				= 13;
	public static final int ENGINE_USER_HAS_BEEN_REGISTERED	= 14;
	public static final int ENGINE_USER_NOT_ACTIVE			= 15;
	public static final int ENGINE_WRONG_EMAIL_OR_PASSWORD	= 16;
	public static final int ENGINE_USER_NOT_LOGIN			= 17;
	public static final int ENGINE_SESSION_KEY_DIFFERENT	= 18;
	public static final int ENGINE_WRONG_OLD_PASSWORD		= 19;
	
	public static final int VERITRANS_CHARGE_FAILED			= 20;
	public static final int ENGINE_SLOT_NOT_AVAILABLE		= 21;
	public static final int BOOKING_ID_EXPIRED_TO_PAY		= 22;
	public static final int BOOKING_CODE_NOT_AVAILABLE		= 23;
	public static final int BOOKING_CODE_EXPIRED			= 24;
	public static final int ALL_SLOT_AVAILABLE				= 25;
	public static final int FAILED_SENDING_EMAIL			= 26;
	public static final int PARAMETER_NOT_COMPLETE			= 27;
	
	private int errorCode;
	private String[] info;
	
	public ParkingEngineException(Throwable t) {
		super("rc." + ENGINE_UNKNOWN_ERROR, t);
		this.errorCode = ENGINE_UNKNOWN_ERROR;
		this.info = new String[] { t.getMessage() };
	}
	
	public ParkingEngineException(int errorCode) {
		super("rc." + errorCode);
		this.errorCode = errorCode;
	}
	
	public ParkingEngineException(int errorCode, String[] info) {
		super("rc." + errorCode + ", Info: " + Arrays.toString(info));
		this.errorCode = errorCode;
		this.info = info;
	}
	
	public ParkingEngineException(int errorCode, String info) {
		this(errorCode, new String[] {info});
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
	
	public String toLogFormat() {
		return "rc." + errorCode + ", Info: " + Arrays.toString(info);
	}
}
