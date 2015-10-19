package com.jakarta.software.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 public class OwnEncrypter {
	 private static final Logger LOG = LoggerFactory.getLogger(OwnEncrypter.class);
        
    
        public OwnEncrypter() {
        }
        
        public static String encrypt(String str) {
        	 int[] key = {3, 4, 5, 6, 3, 4, 5, 6};
             int[] acak = {6, 4, 5, 1, 8, 2, 7, 3};
             int[] acakDec = {4, 6, 8, 2, 3, 1, 7, 5};
            
        	StringBuffer sb = new StringBuffer(str);
    		StringBuffer sb1 = new StringBuffer();
            try {
            	if (str.length() != 6) {
            		LOG.warn("Length of PIN must 6 digit/character : " + str.length());
            		throw new Exception("Panjang PIN harus 6 digit/character");
            	} else {
            		sb.append("00");
            		int u = 0 ;
            		// to hexadecimal
            		for (int y=0; y <sb.length(); y++) {
            			u = key[y] + Integer.parseInt(String.valueOf(sb.charAt(y)), 16);
            			sb1.append(Integer.toHexString(u));
                	}
            		// acak pin
            		sb = new StringBuffer();
            		for (int x=0; x<acak.length;x++) {
            			sb.append(sb1.charAt(acak[x]-1));
            		}
            	}
            } catch (Exception e) {
            	LOG.warn("Error encrypt pin with input: " + str, e);
            }
            return sb.toString();
        }
    
        public static String decrypt(String str) {
        	 int[] key = {3, 4, 5, 6, 3, 4, 5, 6};
             int[] acak = {6, 4, 5, 1, 8, 2, 7, 3};
             int[] acakDec = {4, 6, 8, 2, 3, 1, 7, 5};
             
        	StringBuffer sb = new StringBuffer(str);
    		StringBuffer sb1 = new StringBuffer();
            try {
            	// de acak pin
            	for (int x=0; x<acakDec.length;x++) {
        			sb1.append(sb.charAt(acakDec[x]-1));
        		}
            	// hexadecimal to int
            	int u = 0;
            	sb = new StringBuffer();
        		for (int y=0; y <sb1.length(); y++) {
        			u = Integer.parseInt( String.valueOf(sb1.charAt(y)), 16 /* radix */ ) - key[y];
        			sb.append(u);
            	}
            } catch (Exception e) {
            }
            return sb.substring(0,6);
        }
        
        
    }