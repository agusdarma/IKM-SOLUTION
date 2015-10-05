package com.ikm.myagenda.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ikm.myagenda.data.Constants;
import com.ikm.myagenda.data.ListRecepientMessageVO;
import com.ikm.myagenda.data.LoginData;

public class SharedPreferencesUtils {
	private static final String TAG = "Share Preferences Utils";
	private static SharedPreferences prefs;
	
	private static SharedPreferences getPreferences(Context context) {
	    return context.getSharedPreferences(Constants.PARKING_PREFERENCE, 
	    		Context.MODE_MULTI_PROCESS); //4
	}
	
	public static void saveLoginData(String loginData,Context ctx){    	
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.putString(Constants.LOGIN_DATA_PREF, loginData);
		editor.commit();
    }
	
	public static void saveNumberNotification(int numberNotification,Context ctx){    	
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.putInt(Constants.NUMBER_NOTIFICATION_DATA_PREF, numberNotification);
		editor.commit();
    }
	
	public static void saveUserWaliKelas(Boolean isWaliKelas,Context ctx){    	
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.putBoolean(Constants.WALI_KELAS_DATA_PREF, isWaliKelas);
		editor.commit();
    }
	
	public static boolean getUserWaliKelas(Context ctx){
		boolean isWaliKelas = false;
		isWaliKelas = getPreferences(ctx).getBoolean(Constants.WALI_KELAS_DATA_PREF, false);
		return isWaliKelas;
    }
	
	public static int getNumberNotification(Context ctx){
		int notif = 0;
		notif = getPreferences(ctx).getInt(Constants.NUMBER_NOTIFICATION_DATA_PREF, 0);
		return notif;
    }
	
	public static LoginData getLoginData(Context ctx) {
		LoginData loginData = null;
		try {		   	
		String ld = getPreferences(ctx).getString(Constants.LOGIN_DATA_PREF, "");
		loginData = HttpClientUtil.getObjectMapper(ctx).readValue(ld, new TypeReference<LoginData>(){});
		} catch (JsonGenerationException e) {
			Log.e(TAG, "JsonGenerationException  getLoginData: " + e);	
		} catch (JsonMappingException e) {
			Log.e(TAG, "JsonMappingException getLoginData: " + e);			
		} catch (IOException e) {
			Log.e(TAG, "IOException getLoginData: " + e);
		}
		return loginData;   
    }
	
	public static void saveRecepientMessage(String recepientMessage,Context ctx) {	   	
			SharedPreferences.Editor editor = getPreferences(ctx).edit();
			editor.putString(Constants.RECEPIENT_MESSAGE_DATA_PREF, recepientMessage);
			editor.commit();
    }
	
	public static List<ListRecepientMessageVO> getRecepientMessage(Context ctx) {
		List<ListRecepientMessageVO> listRecepientMessageVOs = new ArrayList<ListRecepientMessageVO>();
		try {		   	
			String ld = getPreferences(ctx).getString(Constants.RECEPIENT_MESSAGE_DATA_PREF, "");
			if(!ld.isEmpty()){
				listRecepientMessageVOs = HttpClientUtil.getObjectMapper(ctx).readValue(ld, new TypeReference<List<ListRecepientMessageVO>>(){});
			}		
		} catch (JsonGenerationException e) {
			Log.e(TAG, "JsonGenerationException  getRecepientMessage: " + e);	
		} catch (JsonMappingException e) {
			Log.e(TAG, "JsonMappingException getRecepientMessage: " + e);			
		} catch (IOException e) {
			Log.e(TAG, "IOException getRecepientMessage: " + e);
		}
		return listRecepientMessageVOs;   
    }
	
	public static String objectToJson(List<ListRecepientMessageVO> recepientsMessage,Context ctx){
		String json = "";
		try {
			json = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(recepientsMessage);
		} catch (JsonGenerationException e) {
			Log.e(TAG, "JsonGenerationException  objectToJson: " + e);	
		} catch (JsonMappingException e) {
			Log.e(TAG, "JsonMappingException objectToJson: " + e);			
		} catch (IOException e) {
			Log.e(TAG, "IOException objectToJson: " + e);
		}
		return json;
	}
}
