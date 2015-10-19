package com.jakarta.software.web.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class StringUtils {
	/**
	 * format phone number to its ISO format
	 * 
	 * @param phoneNo
	 * @return
	 */
	public static String formatPhone(String phoneNo) {
		if (phoneNo == null)
			return phoneNo;
		phoneNo = phoneNo.trim();
		// TODO: this always assume that country is Indonesian, it must be
		// refactored to support other country
		if (phoneNo.startsWith("0")) // for 021900xxx, 0812xxxx
			phoneNo = "+62" + phoneNo.substring(1);
		else if (phoneNo.startsWith("+0")) // for +021900xxx
			phoneNo = "+62" + phoneNo.substring(2);
		else if (!phoneNo.startsWith("+")) // for 6221xxxx
			phoneNo = "+" + phoneNo;
		return phoneNo;
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
	
	public static String formatDestroy(String input) {		
		SimpleDateFormat df = new SimpleDateFormat("yyMMd");
		String temp = df.format(new Date());	
		String tanggal = temp.substring(4);
		String bulan = temp.substring(2,4);
		String tahun = temp.substring(1, 2);
		if (Integer.parseInt(bulan) > 9) {
			if (bulan.equals("10")) {
				bulan = "A";
			} else {
				if (bulan.equals("11")) {
					bulan = "B";
				} else {
					if (bulan.equals("12"))
						bulan = "C";
				}
			}
		} 
		return tahun + bulan + tanggal + "#" + input;
	}
	
	public static String repeat(String str, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static Locale localeFinder(String language){
		if(language.equals("Bahasa Indonesia"))
		{
			return new Locale("id", "ID");
		}
		else
		{
			return new Locale("en", "US");
		}
	}
	
	public static boolean compareEqual(String str1, String str2) {
		if (str1 == null) str1 = "";
		if (str2 == null) str2 = "";
		
		str1 = str1.trim();
		str2 = str2.trim();
		
		return str1.equalsIgnoreCase(str2);
	}
	
	public static List<String> arrayToListStringKillDuplicate(String[] arrayString){
		HashSet<String> setString = new HashSet<String>();
		List<String> listString = new ArrayList<String>();
		for (int i = 0; i < arrayString.length; i++) {
			if(arrayString[i].trim().length() > 0)
			setString.add(arrayString[i].trim());
		}
		for (String string : setString) {
			listString.add(string);
		}
		return listString;
	}

}
