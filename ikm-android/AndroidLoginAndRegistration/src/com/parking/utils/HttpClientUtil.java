package com.parking.utils;

import id.co.veritrans.android.api.VTUtil.VTConfig;

import java.security.KeyStore;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.util.Log;

public class HttpClientUtil {
	/**
	 * 
	 */
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String JSON = "application/json";
	
	/**
	 * veritrans
	 */
	public final static String CLIENT_KEY = "VT-client-Uz6HDzD8yYF0vhr-";
	public final static String PAYMENT_API = "https://api.veritrans.co.id/v2/token";

    public final static String PAYMENT_API_SANDBOX = "https://api.sandbox.veritrans.co.id/v2/token";

    public static String getPaymentApiUrl(){
        if(VTConfig.VT_IsProduction){
            return PAYMENT_API;
        }
        return PAYMENT_API_SANDBOX;
    }
	
	/**
	 * URL base to engine
	 */
	
//	public static final String URL_BASE = "http://54.191.189.219:8080/parking-trx";
    
//    public static final String URL_BASE = "http://192.168.1.102:8080/parking-trx";
//    public static final String URL_BASE = "http://192.168.0.217:8080/parking-trx";
    public static final String URL_BASE = "http://ec2-52-3-21-158.compute-1.amazonaws.com:8080/parking-trx";
//    public static final String URL_BASE = "http://192.168.0.203:8080/parking-trx";
	/**
	 * URL TRX
	 */
	public static final String URL_FORGOT_PASSWORD = "/trx/forgetPassword";
	public static final String URL_LOGIN = "/trx/loginUser";
	public static final String URL_CHANGE_PASSWORD = "/trx/changePassword";
	public static final String URL_LOGOUT = "/trx/logoutUser";
	public static final String URL_GET_ALL_MALL = "/trx/listMall";
	public static final String URL_USER_REG= "/trx/userRegistration"; // Registration
	public static final String URL_RECEIVE_TRX_VERITRANS= "/trx/receiveTrxFromVeriTrans"; // payment Confirm
	public static final String URL_FIND_SLOT_BY_MALL= "/trx/findSLotsByMall";
	public static final String URL_CHECK_ORDER_ALLOW_PAY= "/trx/checkOrderAllowPay";
	public static final String URL_CHECK_BOOKING_CODE= "/trx/checkBookingCode";
	public static final String URL_CONFIRM_BOOKING_CODE= "/trx/confirmCodeBooking";
	public static final String URL_RELEASE_SLOT_PARKING= "/trx/releaseSlotParking";
	public static final String URL_REFRESHING_LIST_MALL= "/trx/refreshCacheMall";
	public static final String URL_GET_HISTORY = "/trx/historyBooking";
	
	
	// list trx code engine
	//LOGIN
	public static final String HTTP_TRX_CODE_REQ_SESSION_ID		= "REQ_SESSION";
	public static final String HTTP_TRX_CODE_LOGIN		= "LOGIN";
	
	//FORGET PASS
	public static final String HTTP_TRX_CODE_FORGET_PASS		= "FORGET_PASS";
	
	
	
	// CHANGE PASS
	public static final String HTTP_TRX_CODE_CHANGE_PASS		= "CHG_PASS";
	
	//LOGOUT
	public static final String HTTP_TRX_CODE_LOGOUT		= "LOGOUT";

	

	public static final String HTTP_PARAM_MSG				= "m"; // message variable
	public static final String HTTP_PARAM_TRX_CODE			= "t"; // trx code variable
	public static final String HTTP_PARAM_USER				= "u"; // user variable
	
	
	public static final boolean IS_HTTP_MODE = true;
	public static final String CHAR_QUESTION_MARK = "?";
	public static final String CHAR_EQUALS = "=";
	public static final String CHAR_AND = "&";
	
	public static final int CONNECTION_TIMEOUT = 30000; // milisecond
	public static final int SO_TIMEOUT = 60000; // milisecond
	
	private static DefaultHttpClient client;
	private static KeyStore trustStore;
	private static MySSLSocketFactory sf;
	private static HttpParams params;
	private static SchemeRegistry registry;
	private static ClientConnectionManager ccm;
	private static SSLSocketFactory sslSocketFactory;
	private static SSLSessionCache sessionCache;
	
	public static ObjectMapper getObjectMapper(Context ctx) {
		ObjectMapper mapper = new ObjectMapper();
		// faster this way, not default 
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
	
	public synchronized static HttpClient getNewHttpClientCache(Context ctx) {
		if(IS_HTTP_MODE){
			 if (client == null){
				 client = new DefaultHttpClient();
				 HttpParams params = client.getParams();
				 params = setParamHttp(params);
				 ClientConnectionManager mgr = client.getConnectionManager();
				 client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,mgr.getSchemeRegistry()), params);
			 }
			 return client;   	
		}else{
			try {
				if (client == null){
					Log.i("HttpClientUtil","DefaultHttpClient : null");
					 //client = new DefaultHttpClient();
						if (trustStore == null){
							 trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
						     trustStore.load(null, null);
						}
					   
						// Use a session cache for SSL sockets
						if(sessionCache == null){
							sessionCache = ctx == null ? null : new SSLSessionCache(ctx);
						}
						
						if (sslSocketFactory == null){
							sslSocketFactory = SSLCertificateSocketFactory.getHttpSocketFactory(10000, sessionCache);
						}
				       
						if (params == null){
							params = new BasicHttpParams();
							params = setParamHttp(params);
						}
				        
						if (registry == null){
							registry = new SchemeRegistry();
					        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
					        registry.register(new Scheme("https", sslSocketFactory, 443));
						}
						if (ccm == null){
							ccm = new ThreadSafeClientConnManager(params, registry);							
						}

						client = new DefaultHttpClient(ccm,params);
				 }
				Log.i("HttpClientUtil","DefaultHttpClient : exist");
				 return client;
		    } catch (Exception e) {
		    	Log.e("HttpClientUtil", e.getMessage(),e);
		    	if (client == null){
					 client = new DefaultHttpClient();
				 }
		        return client;
		    }
		}
		
	    
	}
	
	
	public synchronized static HttpClient getNewHttpClient() {
		if(IS_HTTP_MODE){
			 if (client == null){
				 client = new DefaultHttpClient();
				 HttpParams params = client.getParams();
				 params = setParamHttp(params);
				 ClientConnectionManager mgr = client.getConnectionManager();
				 client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,mgr.getSchemeRegistry()), params);
			 }
			 return client;   	
		}else{
			try {
				if (client == null){
					Log.i("HttpClientUtil","DefaultHttpClient : null");
					 //client = new DefaultHttpClient();
						if (trustStore == null){
							 trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
						     trustStore.load(null, null);
						}
					   
						if (sf == null){
							 sf = new MySSLSocketFactory(trustStore);
						     sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
						}
				       
//						if (params == null){
//							params = new BasicHttpParams();
//					        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//					        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//						}
						if (params == null){
							params = new BasicHttpParams();
//							params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//						    params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, HTTP.UTF_8);
//						    params.setParameter(CoreProtocolPNames.USER_AGENT, "Apache-HttpClient/Android");
//						    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0); // no timeout
//						    params.setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);
//						    params.setParameter(CoreConnectionPNames.TCP_NODELAY, true);
//						    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 0);
							params = setParamHttp(params);
						}
				        
						if (registry == null){
							registry = new SchemeRegistry();
					        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
					        registry.register(new Scheme("https", sf, 443));
						}
						if (ccm == null){
							ccm = new ThreadSafeClientConnManager(params, registry);							
						}

						client = new DefaultHttpClient(ccm,params);
				 }
				Log.i("HttpClientUtil","DefaultHttpClient : exist");
				 return client;
		    } catch (Exception e) {
		    	Log.e("HttpClientUtil", e.getMessage(),e);
		    	if (client == null){
					 client = new DefaultHttpClient();
				 }
		        return client;
		    }
		}
		
	    
	}
	
	public static HttpParams setParamHttp(HttpParams params){
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	    params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, HTTP.UTF_8);
	    params.setParameter(CoreProtocolPNames.USER_AGENT, "Apache-HttpClient/Android");
	    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
	    params.setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);
	    params.setParameter(CoreConnectionPNames.TCP_NODELAY, true);
	    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
		return params;
	}


}
