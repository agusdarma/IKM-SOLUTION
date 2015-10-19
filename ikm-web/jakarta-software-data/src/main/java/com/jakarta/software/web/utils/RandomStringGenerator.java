package com.jakarta.software.web.utils;

public class RandomStringGenerator {

	public static String generateRandomString(int alphaLength, int numericLength, int specialCharLength)  {

		StringBuffer buffer = new StringBuffer();

		String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		String numeric = "1234567890";

		String specialChar = "!@#$%^&*()_=-+";

		for (int i = 0; i < alphaLength; i++) {
			double index = Math.random() * alpha.length();
			buffer.append(alpha.charAt((int) index));
		}
		
		for (int i = 0; i < numericLength; i++) {
			double index = Math.random() * numeric.length();
			buffer.append(numeric.charAt((int) index));
		}
		
		for (int i = 0; i < specialCharLength; i++) {
			double index = Math.random() * specialChar.length();
			buffer.append(specialChar.charAt((int) index));
		}
		return buffer.toString();
	}
	
}
