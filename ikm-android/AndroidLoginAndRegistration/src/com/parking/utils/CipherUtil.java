package com.parking.utils;

import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;
import java.util.BitSet;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.digests.RIPEMD256Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import android.util.Log;

import com.parking.activity.ForgetPasswordActivity;

public class CipherUtil {
//	private static final Logger LOGGER = LoggerFactory.getLogger(CipherUtil.class);
	private static final String TAG = ForgetPasswordActivity.class.getSimpleName();
	private static final int MAX_LENGTH_3DES_KEY	= 24;
	public static final String PASSWORD = "PARKING_ONLINE124A7BDF5C701B69B4BACD05F1538EA2";
	private static final int MAX_LENGTH_3DES_KEY_SMARTPHONE = 24;
	// http://www.random.org/bytes/
	private static final String KEY_CACHE_VO = 
			"39375213f3a3a8b8af90cb9a25bf2f52d1104d9f009a864f";
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	/*
	 * http://www.exampledepot.com/egs/java.util/Bits2Array.html Returns a
	 * bitset containing the values in bytes. The byte-ordering of bytes must be
	 * big-endian which means the most significant bit is in element 0
	 */
	public static BitSet fromByteArray(byte[] bytes) {
		BitSet bits = new BitSet();
		for (int i = 0; i < bytes.length * 8; i++) {
			if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
				bits.set(i);
			}
		}
		return bits;
	}

	/*
	 * http://www.exampledepot.com/egs/java.util/Bits2Array.html Returns a byte
	 * array of at least length 1. The most significant bit in the result is
	 * guaranteed not to be a 1 (since BitSet does not support sign extension).
	 * The byte-ordering of the result is big-endian which means the most
	 * significant bit is in element 0. The bit at index 0 of the bit set is
	 * assumed to be the least significant bit.
	 */
	public static byte[] toByteArray(BitSet bits) {
		// byte[] bytes = new byte[bits.length()/8+1];
		// WARNING !!! set fixed to 8 byte, only to be used for ISOMsg
		byte[] bytes = new byte[8];
		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
			}
		}
		return bytes;
	}
	
	public static String toHexString(byte[] data) {
		return new String(Hex.encode(data)).toUpperCase();
	}
	public static byte[] toHexByte(String input) {
		return Hex.decode(input);
	}
	
	public static String toHexString(BitSet bits) {
		return toHexString(toByteArray(bits));
	}
	public static BitSet toBitSet(String hexStr) {
		return fromByteArray(toHexByte(hexStr));
	}
	
	public static String passwordDigest(String userId, String password) {
		String upperUserId = userId.toUpperCase();
		String temp2 = (new StringBuilder(upperUserId)).reverse().toString() + password;
		
		RIPEMD256Digest digester = new RIPEMD256Digest();
		byte[] resBuf = new byte[digester.getDigestSize()];
		byte[] input = temp2.getBytes();
		digester.update(input, 0, input.length);
		digester.doFinal(resBuf, 0);

		String result = toHexString(resBuf);

		return result.toUpperCase();
	}
	
	private static byte[] hashSHA256(String input) {
		SHA256Digest digester = new SHA256Digest();
		byte[] resBuf = new byte[digester.getDigestSize()];
		byte[] resPass = input.getBytes();
		digester.update(resPass, 0, resPass.length);
		digester.doFinal(resBuf, 0);
		return resBuf;
	}
	
	private static byte[] hashRIPEMD256(String input) {
		RIPEMD256Digest digester = new RIPEMD256Digest();
		byte[] resBuf = new byte[digester.getDigestSize()];
		byte[] resPass = input.getBytes();
		digester.update(resPass, 0, resPass.length);
		digester.doFinal(resBuf, 0);
		return resBuf;
	}
	
	public static String encryptDESede(String input, String key) {
		byte[] hashPassword = hashSHA256(key);
		byte[] hashPasswordx = new byte[MAX_LENGTH_3DES_KEY];
		System.arraycopy(hashPassword, 0, hashPasswordx, 0, MAX_LENGTH_3DES_KEY);
		return toHexString(encryptDESede(input.getBytes(), hashPasswordx) );
	}
	
	private static byte[] encryptDESede(byte[] input, byte[] key) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/ZeroBytePadding", "BC");
			SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(input);
		}
		catch (Exception e) {
//			LOGGER.error("Failed to encrypt DES EDE. Input: {}, Key: {}. {}",new String[] { new String(input), Arrays.toString(key), e.getMessage() });
			return null;
		}
	}
	
	public static String decryptDESede(String input, String key) {
		byte[] hashPassword = hashSHA256(key);
		byte[] hashPasswordx = new byte[MAX_LENGTH_3DES_KEY];
		System.arraycopy(hashPassword, 0, hashPasswordx, 0, MAX_LENGTH_3DES_KEY);
		
		return new String(decryptDESede(toHexByte(input), hashPasswordx));
	}
	
	private static byte[] decryptDESede(byte[] input, byte[] key) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/ZeroBytePadding", "BC");
			SecretKeySpec secretKey = new SecretKeySpec(key, "DESEDE");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(input);
		}
		catch (Exception e) {
//			LOGGER.error("Failed to decrypt DES EDE. Input: {}, Key: {}. {}",new String[] { new String(input), Arrays.toString(key), e.getMessage() });
			return null;
		}
	}
	
	public static byte[] decryptDESedeWithPassword(String input, String password) {
		return decryptDESedeWithPassword(toHexByte(input), password);
	}

	public static byte[] decryptDESedeWithPassword(byte[] input, String password) {
		byte[] hashPassword = hashRIPEMD256(password);
		byte[] hashPasswordx = new byte[MAX_LENGTH_3DES_KEY];
		System.arraycopy(hashPassword, 0, hashPasswordx, 0, MAX_LENGTH_3DES_KEY);
		return decryptDESede(input, hashPasswordx);
	}
	
	public static byte[] encryptDESedeWithPassword(String input, String password) {
		return encryptDESedeWithPassword(input.getBytes(), password);
	}

	public static byte[] encryptDESedeWithPassword(byte[] input, String password) {
		byte[] hashPassword = hashRIPEMD256(password);
		byte[] hashPasswordx = new byte[MAX_LENGTH_3DES_KEY];
		System.arraycopy(hashPassword, 0, hashPasswordx, 0, MAX_LENGTH_3DES_KEY);
		return encryptDESede(input, hashPasswordx);
	}
	
	public static String encryptPass(String password) {
		byte[] key = toHexByte(KEY_CACHE_VO);
		byte[] input = password.getBytes();
		return toHexString(encryptDESede(input, key) );
	}
	public static String decryptPass(String password) {
		byte[] key = toHexByte(KEY_CACHE_VO);
		byte[] input = toHexByte(password);
		return new String(decryptDESede(input, key));
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
			Log.e(TAG,"Failed to encrypt DES EDE. Input: " + 
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
			Log.e(TAG,"Executing request: " + "Failed to decrypt DES EDE. Input: " + 
					", key: " + Arrays.toString(key) +". "  + e);
			return null;
		}
	}
	
}
