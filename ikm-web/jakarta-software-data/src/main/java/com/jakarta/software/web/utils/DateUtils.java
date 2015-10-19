package com.jakarta.software.web.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
		
	public static String DateTimeToStr(String pattern, Date input) {
		if (pattern == null)  return null;
		if (input == null)  return null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(input);
		} catch (Exception e) {
			LOGGER.warn("Error Parse Exception : " + e, e);
			return null;
		}
	}

	public static Date StrToDateTime(String pattern, String input) {
		if (pattern == null)  return null;
		if (input == null)  return null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.parse(input);
		} catch (Exception pe) {
			LOGGER.warn("Error Parse Exception : " + pe, pe);
			return null;
		}
	}
	
	public static Date generateDateStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.setLenient(false);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTimeInMillis());
	}
	
	public static Date generateDateEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.setLenient(false);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);

		return new Date(cal.getTimeInMillis());
	}
	
	public static Date generateNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.setLenient(false);
		cal.add(Calendar.DAY_OF_MONTH, 1);		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());
	}
	
	public  static  Date getTodayEarlyDate(Date date) {
		Calendar calFr = Calendar.getInstance();
		calFr.setTime(date);

		Date dateFr = calFr.getTime();

		calFr.set(Calendar.HOUR, 0);
		calFr.set(Calendar.MINUTE, 0);
		calFr.set(Calendar.SECOND, 0);
		calFr.set(Calendar.AM_PM, Calendar.AM);

		dateFr = calFr.getTime();

		return  dateFr;
		}

	
	public  static  Date getTodayLaterDate(Date date) {
		Calendar calFr = Calendar.getInstance();
		calFr.setTime(date);
		Calendar calTo = Calendar.getInstance();
		calTo.set(calFr.get(Calendar.YEAR), calFr.get(Calendar.MONTH), calFr.get(Calendar.DATE));

		calTo.set(Calendar.HOUR, 11);
		calTo.set(Calendar.MINUTE, 59);
		calTo.set(Calendar.SECOND, 59);
		calFr.set(Calendar.AM_PM, Calendar.PM);

		Date dateTo = calTo.getTime();

		return  dateTo;
		}
	
	public static HashMap<Integer, String> getListMonthUtil()
	{
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(1,"January");
		hm.put(2,"February");
		hm.put(3,"March");
		hm.put(4,"April");
		hm.put(5,"May");
		hm.put(6,"June");
		hm.put(7,"July");
		hm.put(8,"August");
		hm.put(9,"September");
		hm.put(10,"October");
		hm.put(11,"November");
		hm.put(12, "December");
		return hm;
	}
}
