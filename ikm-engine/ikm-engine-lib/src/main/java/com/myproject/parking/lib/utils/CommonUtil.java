package com.myproject.parking.lib.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {
	
	private static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static NumberFormat nfNoDecimal = new DecimalFormat("#,##0");
	private static NumberFormat nfPlain = new DecimalFormat("###0");
	private static SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");
	public static NumberFormat nf = new DecimalFormat("#,###");
	public static NumberFormat nfx = new DecimalFormat("####.##");
	private static Random r = new Random();
	
	public static String convertCamelCaseToUnderScore(String camelCase) {
		if (camelCase == null) return null;
		StringBuilder sb = new StringBuilder();
		String[] tokens = camelCase.split("[A-Z]");
		int count = 0;
		for (String token: tokens) {
			sb.append(token);
			if (++count < tokens.length) {
				//get first upper case string
				int idx = sb.length() - (count - 1);
				String s = camelCase.substring(idx, idx+1);
				sb.append("_").append(s.toLowerCase());
			}
		}
		if (sb.length() > 0 && sb.charAt(0) == '_')
			sb.deleteCharAt(0);
		return sb.toString();
	}
	
	public static String repeat(String str, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static String padRight(String str, int length) {
		if (str == null) str = "";
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() < length)
			sb.append(' ');
		return sb.toString();
	}

	public static String padLeft(String str, char c, int length) {
		if (str == null) str = "";
		str = str.trim();
		StringBuilder sb = new StringBuilder();
		if (str.length() >= length) {
			return str;
		}
		int fill = length - str.length();
		while (fill-- > 0)
			sb.append(c);
		sb.append(str);
		return sb.toString();		
	}
	
	public static String zeroPadLeft(String str, int length) {
		return padLeft(str, '0', length);
	}
	
	/**
	 * The string length return is strict to the length, it will cut the string
	 * if necessary. This function is important in composing ISO which require
	 * an exact length. return pad right if string < length
	 */
	public static String cutOrPad(String str, int length) {
		if (str == null) {
			return repeat(" ", length);
		}
		if (str.length() < length) {
			return padRight(str, length);
		} else if (str.length() > length) {
			return str.substring(0, length);
		}
		return str;
	}
	
	/**
	 * normalize +6281xxx -> 081xxx
	 * @param phoneNo
	 * @return
	 */
	public static String normalizePhoneNo(String phoneNo) {
		if (StringUtils.isEmpty(phoneNo)) return phoneNo;
		if (phoneNo.startsWith("+62") && phoneNo.length() > 3)
			phoneNo = "0" + phoneNo.substring(3);
		else if (phoneNo.startsWith("+"))  // just in case +555
			phoneNo = phoneNo.substring(1);
		else if (phoneNo.startsWith("62") && phoneNo.length() > 5)
			phoneNo = "0" + phoneNo.substring(2);
		return phoneNo;
	}
	
	public static String displayDateTime(Date dateTime) {
		return sdfDateTime.format(dateTime);
	}
	public static String displayNumberNoDecimal(double number) {
		return nfNoDecimal.format(number);
	}
	
	public static String displayNumberPlain(double number) {
		return nfPlain.format(number);
	}
	
	public static String displayYear(Date dateTime){
		return dfYear.format(dateTime);
	}
	
	public static String generateNumeric(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i< length; i++)
			sb.append(r.nextInt(10));
		return sb.toString();
	}
	
	public static String generateAlphaNumeric(int length) {
		String C = "QWERTYUIOPLKJHGFDAZXCVBNM0987654321";
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			int idx = r.nextInt(C.length());
			sb.append(C.substring(idx, idx + 1));
		}
		return sb.toString();
	}
	
	public static Calendar DateToCalendar(Date date){ 
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(date);
	  return cal;
	}
	
	public static long DateDiff(Calendar calendar1, Calendar calendar2){
		 long milliseconds1 = calendar1.getTimeInMillis();
		 long milliseconds2 = calendar2.getTimeInMillis();
		 long diff = milliseconds2 - milliseconds1;
		 long diffDays = diff / (24 * 60 * 60 * 1000);
		 return diffDays;
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
	
	public static String convertNumericFormatToString(String ori) {
		String result = "";
		//result = ori.replaceAll(",", "");
		result = ori.replaceAll("\\.", "");
		if(result.substring(result.length()-3, result.length()).equals(",00"))
		{
			result = result.substring(0,result.length()-3);
		}
		return result;
	}
	
	 public static String convertStringToNumericFormat(String num) {
		 String coma = "";
		 if(num.contains(","))
		 {
			coma = num.substring(num.length()-3, num.length());
			num  = num.substring(0,num.length()-3);
			long temp = Long.parseLong(num);
			num=nf.format(temp)+coma;
			return num;
		 }
		 else
		 {
			 long temp = Long.parseLong(num);
			 return nf.format(temp);
		 }		
	 }
	 
	public static String convertToLocalGermany(double x){
		 NumberFormat nf2 = NumberFormat.getInstance(Locale.GERMAN);
		 String z = nf2.format(x);
		 return z;
	}
	
	public static long dateDifferentInMinutes(Date d1, Date currentTime){
		//in milliseconds
		long diff = currentTime.getTime() - d1.getTime();

//		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
//		long diffHours = diff / (60 * 60 * 1000) % 24;
//		long diffDays = diff / (24 * 60 * 60 * 1000);

//		System.out.print(diffDays + " days, ");
//		System.out.print(diffHours + " hours, ");
		System.out.print(diffMinutes + " minutes, ");
//		System.out.print(diffSeconds + " seconds.");
		return diffMinutes;
	}
	
	public static long dateDifferentInHours(Date d1, Date currentTime){
		//in milliseconds
		long diff = currentTime.getTime() - d1.getTime();

//		long diffSeconds = diff / 1000 % 60;
//		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
//		long diffDays = diff / (24 * 60 * 60 * 1000);

//		System.out.print(diffDays + " days, ");
//		System.out.print(diffHours + " hours, ");
//		System.out.print(diffHours + " hours, ");
//		System.out.print(diffSeconds + " seconds.");
		return diffHours;
	}
	
	public static long dateDifferentInMinutesOnly(Date d1, Date currentTime){
		//in milliseconds
		long diff = currentTime.getTime() - d1.getTime();
		long diffMinutesOnly = diff / (60 * 1000);
		return diffMinutesOnly;
	}
	
}
