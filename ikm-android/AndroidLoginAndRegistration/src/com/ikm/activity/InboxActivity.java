package com.ikm.activity;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.ikm.R;
import com.ikm.data.Constants;
import com.ikm.data.InboxVO;
import com.ikm.data.LoginData;
import com.ikm.data.MessageVO;
import com.ikm.data.ReqListInboxData;
import com.ikm.data.ReqSendMessageData;
import com.ikm.data.RespListInboxVO;
import com.ikm.swipelistview.sample.adapters.MessagesListAdapter;
import com.ikm.utils.HttpClientUtil;
import com.ikm.utils.MessageUtils;
import com.ikm.utils.SharedPreferencesUtils;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class InboxActivity extends Activity {

	// LogCat tag
	private static final String TAG = InboxActivity.class.getSimpleName();
	private Context ctx;
	private Button btnSend;
	private EditText inputMsg;
	Shimmer shimmer;
	// private WebSocketClient client;

	// Chat messages list adapter
	private MessagesListAdapter adapter;
	private List<InboxVO> listMessages;
	private ListView listViewMessages;
	private ReqListInboxTask reqListInboxTask = null;
	private ReqSendMessageTask reqSendMessageTask = null;
	
	// private Utils utils;

	// Client name
	private String menuName = null;

	// JSON flags to identify the kind of JSON response
	private static final String TAG_SELF = "self", TAG_NEW = "new",
			TAG_MESSAGE = "message", TAG_EXIT = "exit";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inbox);
		ShimmerTextView tvTitle = (ShimmerTextView) findViewById(R.id.tvTitle);
		ShimmerTextView tvTitleSchool = (ShimmerTextView) findViewById(R.id.tvTitleSchool);
		btnSend = (Button) findViewById(R.id.btnSend);
		ButtonRectangle btnBack = (ButtonRectangle) findViewById(R.id.btnBack);
		inputMsg = (EditText) findViewById(R.id.inputMsg);
		ctx = InboxActivity.this;
		listViewMessages = (ListView) findViewById(R.id.list_view_messages);
		if (shimmer != null && shimmer.isAnimating()) {
			shimmer.cancel();
        } else {
        	shimmer = new Shimmer();
        	shimmer.start(tvTitle);
        	shimmer.start(tvTitleSchool);
        }
		// utils = new Utils(getApplicationContext());

		// Getting the person name from previous screen
		Intent i = getIntent();
		menuName = i.getStringExtra(Constants.FROM_MENU);
		
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Constants.PARENTS.equalsIgnoreCase(menuName)){
					// harus tau dari mana inbox ini berasal apakah dari parent apa dari teacher
					Intent i = new Intent(ctx, MenuParentActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  		       			
					startActivity(i);
				}else if(Constants.TEACHER.equalsIgnoreCase(menuName)){
					// harus tau dari mana inbox ini berasal apakah dari parent apa dari teacher
					Intent i = new Intent(ctx, MenuTeacherActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  		       			
					startActivity(i);
				}
					
				
			}
		});

		btnSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * Insert ke table message
				 * 
				 */
				if (!inputMsg.getText().toString().isEmpty() ) {
					// send message
					reqSendMessageTask = new ReqSendMessageTask();
					reqSendMessageTask.execute("");												
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(InboxActivity.this,ctx.getResources().getString(R.string.message_empty));
				}
			}
		});
		/**
		 *  get data inbox
		 */
        reqListInboxTask = new ReqListInboxTask();
        reqListInboxTask.execute("");
	}
	
	public class ReqSendMessageTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_send_message),ctx.getResources().getString(R.string.progress_dialog));
    			progressDialog.show();
    		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);         
    			
    			ReqSendMessageData reqSendMessageData = new ReqSendMessageData();
    			reqSendMessageData.setPassword(loginData.getPassword());
    			reqSendMessageData.setKodeSekolah(loginData.getKodeSekolah());
    			reqSendMessageData.setNoInduk(loginData.getNoInduk());
    			reqSendMessageData.setOriginRequest(Constants.ORIGIN_SOURCE);
    			reqSendMessageData.setUserType(loginData.getUserType());
    			reqSendMessageData.setIsiMessage(inputMsg.getText().toString());
           		  				    			
           		String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(reqSendMessageData);
           		s = URLEncoder.encode(s, "UTF-8");
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_SEND_MESSAGE);
    			post.setHeader(HttpClientUtil.CONTENT_TYPE, HttpClientUtil.JSON);
    			post.setEntity(entity);
    			// Execute HTTP request
    			Log.d(TAG,"Executing request: " + post.getURI());
                HttpResponse response = client.execute(post);
                HttpEntity respEntity = response.getEntity();
                respString = EntityUtils.toString(respEntity);
    			result = true;
    			} catch (ClientProtocolException e) {
    				Log.e(TAG, "ClientProtocolException : "+e);
    				respString = ctx.getResources().getString(R.string.message_unexpected_error_message_server);
    				cancel(true);    				
    			} catch (IOException e) {
    				Log.e(TAG, "IOException : "+e); 
    				respString = ctx.getResources().getString(R.string.message_no_internet_connection);
    				cancel(true);    				
    			} catch (Exception e) {
    				Log.e(TAG, "Exception : "+e);  
    				respString = ctx.getResources().getString(R.string.message_unexpected_error_message_server);
    				cancel(true);    				
    			}
           	return result;
           }
		
		 @Override
	     protected void onCancelled() {
			 if(progressDialog.isShowing()){
				progressDialog.dismiss();
			 }
			 MessageUtils messageUtils = new MessageUtils(ctx);
          	 messageUtils.snackBarMessage(InboxActivity.this,respString);
	     }
		
		 @Override
         protected void onPostExecute(final Boolean success) {
			 reqSendMessageTask = null;          
             if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = URLDecoder.decode(respString, "UTF-8");	               	
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		if(messageVO.getRc()==0){
		               			/**
		               			 *  get data inbox
		               			 */
		               	        reqListInboxTask = new ReqListInboxTask();
		               	        reqListInboxTask.execute("");
		               	        inputMsg.setText("");
		               		}
		               		else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(InboxActivity.this,messageVO.getMessageRc());
		               		}

						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(InboxActivity.this,InboxActivity.this.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(InboxActivity.this,InboxActivity.this.getResources().getString(R.string.message_unexpected_error_server));
	               	}
             }else{
          	   MessageUtils messageUtils = new MessageUtils(ctx);
          	   messageUtils.snackBarMessage(InboxActivity.this,InboxActivity.this.getResources().getString(R.string.message_unexpected_error_server));
             }        
             if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
         }
	}
	
	public class ReqListInboxTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_inbox),ctx.getResources().getString(R.string.progress_dialog));
//    			progressDialog.show();
    		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
           		ReqListInboxData reqListInboxData = new ReqListInboxData();
    			reqListInboxData.setPassword("");
    			reqListInboxData.setKodeSekolah(loginData.getKodeSekolah());
    			reqListInboxData.setNoInduk(loginData.getNoInduk());
    			reqListInboxData.setOriginRequest(Constants.ORIGIN_SOURCE);
    			reqListInboxData.setUserType(loginData.getUserType());
           		  				    			
           		String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(reqListInboxData);
           		s = URLEncoder.encode(s, "UTF-8");
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_INBOX);
    			post.setHeader(HttpClientUtil.CONTENT_TYPE, HttpClientUtil.JSON);
    			post.setEntity(entity);
    			// Execute HTTP request
    			Log.d(TAG,"Executing request: " + post.getURI());
                HttpResponse response = client.execute(post);
                HttpEntity respEntity = response.getEntity();
                respString = EntityUtils.toString(respEntity);
    			result = true;
    			} catch (ClientProtocolException e) {
    				Log.e(TAG, "ClientProtocolException : "+e);
    				respString = ctx.getResources().getString(R.string.message_unexpected_error_message_server);
    				cancel(true);    				
    			} catch (IOException e) {
    				Log.e(TAG, "IOException : "+e); 
    				respString = ctx.getResources().getString(R.string.message_no_internet_connection);
    				cancel(true);    				
    			} catch (Exception e) {
    				Log.e(TAG, "Exception : "+e);  
    				respString = ctx.getResources().getString(R.string.message_unexpected_error_message_server);
    				cancel(true);    				
    			}
           	return result;
           }
		
		 @Override
	     protected void onCancelled() {
			 if(progressDialog.isShowing()){
				progressDialog.dismiss();
			 }
			 MessageUtils messageUtils = new MessageUtils(ctx);
          	 messageUtils.snackBarMessage(InboxActivity.this,respString);
	     }
		
		 @Override
         protected void onPostExecute(final Boolean success) {
			 reqListInboxTask = null;          
             if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = URLDecoder.decode(respString, "UTF-8");	               	
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		if(messageVO.getRc()==0){
		               			List<InboxVO> listInbox = constructDataInbox(messageVO.getOtherMessage());

		               			adapter = new MessagesListAdapter(ctx, listInbox);
		               			listViewMessages.setAdapter(adapter);
		               		}
		               		else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(InboxActivity.this,messageVO.getMessageRc());
		               		}

						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(InboxActivity.this,InboxActivity.this.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(InboxActivity.this,InboxActivity.this.getResources().getString(R.string.message_unexpected_error_server));
	               	}
             }else{
          	   MessageUtils messageUtils = new MessageUtils(ctx);
          	   messageUtils.snackBarMessage(InboxActivity.this,InboxActivity.this.getResources().getString(R.string.message_unexpected_error_server));
             }        
             if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
         }
	}
	
	public List<InboxVO> constructDataInbox(String listJson) throws JsonParseException, JsonMappingException, IOException
    {
		
		RespListInboxVO respListInboxVO = HttpClientUtil.getObjectMapper(ctx).readValue(listJson, RespListInboxVO.class);		 
        return respListInboxVO.getListInboxVO();
    }

//	/**
//	 * Appending message to list view
//	 * */
//	private void appendMessage(final InboxVO m) {
//		runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				listMessages.add(m);
//
//				adapter.notifyDataSetChanged();
//
//				// Playing device's notification
//				playBeep();
//			}
//		});
//	}

//	private void showToast(final String message) {
//
//		runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				Toast.makeText(getApplicationContext(), message,
//						Toast.LENGTH_LONG).show();
//			}
//		});
//
//	}

	/**
	 * Plays device's default notification sound
	 * */
	public void playBeep() {

		try {
			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
					notification);
			r.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
//
//	public static String bytesToHex(byte[] bytes) {
//		char[] hexChars = new char[bytes.length * 2];
//		for (int j = 0; j < bytes.length; j++) {
//			int v = bytes[j] & 0xFF;
//			hexChars[j * 2] = hexArray[v >>> 4];
//			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//		}
//		return new String(hexChars);
//	}

}
