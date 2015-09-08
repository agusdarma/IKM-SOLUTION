package com.myproject.parking.lib.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.digests.RIPEMD256Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CipherUtil {
	private static final String INTERNAL_PASSWORD_TOKEN = "124A7BDF5C701B69B4BACD05F1538EA2";
	public static final String PASSWORD = "PARKING_ONLINE124A7BDF5C701B69B4BACD05F1538EA2";
	private static final int MAX_LENGTH_3DES_KEY_SMARTPHONE = 24;
	private static final Logger LOG = LoggerFactory.getLogger(CipherUtil.class);
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	/*
	 * Convert from HexByte to HexString
	 */
	public static String toHexString(byte[] data) {
		return new String(Hex.encode(data)).toUpperCase();
	}

	/*
	 * Convert from HexString to HexByte
	 */
	public static byte[] toHexByte(String input) {
		return Hex.decode(input);
	}

	public static String toBase64(byte[] data) {
		return new String(Base64.encode(data));
	}
	
	public static String passwordDigest(String userId, String password) {
		String upperUserId = userId.toUpperCase();
		String lowerPassword = password.toLowerCase();
		String mixedData = mixString(upperUserId, lowerPassword);;
		/*while (mixedData.length() < INTERNAL_PASSWORD_TOKEN.length()) {
			mixedData = mixString(mixedData, mixString(lowerPassword, upperUserId));
		}*/

		String temp1 = new String(Base64.encode(mixedData.getBytes()));
		String temp2 = mixString((new StringBuilder(INTERNAL_PASSWORD_TOKEN)).reverse().toString(), temp1);

		RIPEMD256Digest digester = new RIPEMD256Digest();
		byte[] resBuf = new byte[digester.getDigestSize()];
		byte[] input = temp2.getBytes();
		digester.update(input, 0, input.length);
		digester.doFinal(resBuf, 0);

		String result = toHexString(resBuf);

		return result.toUpperCase();
	}
	
	private static String mixString(String data1, String data2) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Math.max(data1.length(), data2.length()); i++) {
			if (i < data1.length())
				sb.append(data1.substring(i, i + 1));
			if (i < data2.length())
				sb.append(data2.substring(i, i + 1));
		}
		return sb.toString();
	}
	
	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}
	
	public static String hashSHA1(String input) {
		SHA1Digest digester = new SHA1Digest();
		byte[] resBuf = new byte[digester.getDigestSize()];
		byte[] resPass = input.getBytes();
		digester.update(resPass, 0, resPass.length);
		digester.doFinal(resBuf, 0);

		return convertToHex(resBuf);
	}
	
	public static String encryptTripleDES(String message, String password) {
		return toHexString(encryptDESedeSmartPhone(message.getBytes(),password.getBytes())); 
	}
	
	private static byte[] encryptDESedeSmartPhone(byte[] input, byte[] key) {
		try {
			//hash ing
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(key);
			byte[] hassPass = md.digest();
			StringBuffer sb = new StringBuffer();
			int maxLength = hassPass.length;
			if (maxLength >= MAX_LENGTH_3DES_KEY_SMARTPHONE)
				maxLength = MAX_LENGTH_3DES_KEY_SMARTPHONE;
	        for (int i = 0; i < maxLength; i++) {
	         sb.append(Integer.toString((hassPass[i] & 0xff) + 0x100, 16).substring(1));
	        }	        
	        byte[] hassPassWord = sb.toString().getBytes();
			byte[] hashPasswordx = new byte[MAX_LENGTH_3DES_KEY_SMARTPHONE];
			System.arraycopy(hassPassWord, 0, hashPasswordx, 0, MAX_LENGTH_3DES_KEY_SMARTPHONE);
			final SecretKey secretkey = new SecretKeySpec(hashPasswordx, "DESede");
		    final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		    final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, secretkey, iv);
		    return cipher.doFinal(input);
			/*standart encryption without hashing
			final SecretKey secretkey = new SecretKeySpec(key, "DESede");
		    final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		    final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, secretkey, iv);
		    return cipher.doFinal(input);
		    */
		} catch (Exception e) {
			LOG.error("Failed to encrypt DES EDE. Input: " + 
					", key: " + Arrays.toString(key) +". "  + e);
			return null;
		}
	}
	
	public static String decryptTripleDES(String message, String password) {
		return new String(decryptDESedeSmartPhone(toHexByte(message),password.getBytes())); 
	}
	
	private static byte[] decryptDESedeSmartPhone(byte[] input, byte[] key) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(key);
			byte[] hassPass = md.digest();
			StringBuffer sb = new StringBuffer();
			int maxLength = hassPass.length;
			if (maxLength >= MAX_LENGTH_3DES_KEY_SMARTPHONE)
				maxLength = MAX_LENGTH_3DES_KEY_SMARTPHONE;
	        for (int i = 0; i < maxLength; i++) {
	         sb.append(Integer.toString((hassPass[i] & 0xff) + 0x100, 16).substring(1));
	        }	        
	        byte[] hassPassWord = sb.toString().getBytes();
			byte[] hashPasswordx = new byte[MAX_LENGTH_3DES_KEY_SMARTPHONE];
			System.arraycopy(hassPassWord, 0, hashPasswordx, 0, MAX_LENGTH_3DES_KEY_SMARTPHONE);
			final SecretKey secretkey = new SecretKeySpec(hashPasswordx, "DESede");
		    final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		    final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		    
		    cipher.init(Cipher.DECRYPT_MODE, secretkey, iv);
		    return cipher.doFinal(input);
			/*standart decryption without hashing
			final SecretKey secretkey = new SecretKeySpec(key, "DESede");
		    final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		    final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		    
		    cipher.init(Cipher.DECRYPT_MODE, secretkey, iv);
		    return cipher.doFinal(input);
		    */
		} catch (Exception e) {
			LOG.error("Failed to decrypt DES EDE. Input: " + 
					", key: " + Arrays.toString(key) +". "  + e);
			return null;
		}
	}

}
