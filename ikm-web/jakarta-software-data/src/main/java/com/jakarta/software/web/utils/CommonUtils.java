package com.jakarta.software.web.utils;

public class CommonUtils {
	
	public static boolean compareEqual(String str1, String str2) {
		if (str1 == null) str1 = "";
		if (str2 == null) str2 = "";
		
		str1 = str1.trim();
		str2 = str2.trim();
		
		return str1.equalsIgnoreCase(str2);
	}

}
