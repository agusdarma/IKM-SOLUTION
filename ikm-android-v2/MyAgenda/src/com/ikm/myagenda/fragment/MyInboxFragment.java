package com.ikm.myagenda.fragment;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.adapter.LinkedHashMapAdapter;
import com.ikm.myagenda.adapter.MessagesListAdapter;
import com.ikm.myagenda.data.Constants;
import com.ikm.myagenda.data.InboxVO;
import com.ikm.myagenda.data.ListRecepientMessageVO;
import com.ikm.myagenda.data.LoginData;
import com.ikm.myagenda.data.MessageVO;
import com.ikm.myagenda.data.ReqListInboxData;
import com.ikm.myagenda.data.ReqSendMessageData;
import com.ikm.myagenda.data.RespListInboxVO;
import com.ikm.myagenda.util.HttpClientUtil;
import com.ikm.myagenda.util.MessageUtils;
import com.ikm.myagenda.util.SharedPreferencesUtils;
import com.ikm.myagenda.view.FloatLabeledEditText;

public class MyInboxFragment extends Fragment {
	private static final String TAG = MyInboxFragment.class.getSimpleName();
//	private ImageView mImage;
//	private TextView mName;
//	private TextView mPlace;
	private LinkedHashMap<String, String> mapData;
	private LinkedHashMapAdapter<String, String> adapterRecepient;
	private Spinner spinRecepient;
	private Context ctx;
	View rootView;
	private FloatLabeledEditText inputMsg;
	private TextView btnSend;
	private MessagesListAdapter adapter;
	private ListView listViewMessages;
	private ReqListInboxTask reqListInboxTask = null;
	private ReqSendMessageTask reqSendMessageTask = null;
	private int recepientId;

	public static MyInboxFragment newInstance() {
		return new MyInboxFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_myinbox_list_views,
				container, false);
		ctx = this.getActivity().getBaseContext();
		
		
		
//		mImage = (ImageView) rootView.findViewById(R.id.expandable_lv_social_image);
//		mName = (TextView) rootView.findViewById(R.id.expandable_lv_social_name);
//		mPlace = (TextView) rootView.findViewById(R.id.expandable_lv_social_place);
//		ImageUtil.displayRoundImage(mImage,
//				"http://pengaja.com/uiapptemplate/newphotos/profileimages/2.jpg", null);
		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
		LinearLayout linSpinRecepient = (LinearLayout) rootView.findViewById(R.id.linSpinRecepient);
		if(loginData.getUserType()==Constants.TEACHER_KEY){
			mapData = new LinkedHashMap<String, String>();    
			mapData.put("-1", "Please Select Recepient");
			mapData.put("0", "Public");
	        List<ListRecepientMessageVO> listRecepientVO = SharedPreferencesUtils.getRecepientMessage(ctx);
	        for (ListRecepientMessageVO recepientMessage : listRecepientVO) {
	        	mapData.put(Integer.toString(recepientMessage.getId()), recepientMessage.getName());
			}
	        
	        adapterRecepient = new LinkedHashMapAdapter<String, String>(ctx, android.R.layout.simple_spinner_item, mapData);
	        adapterRecepient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinRecepient = (Spinner) rootView.findViewById(R.id.spinRecepient);
	        spinRecepient.setAdapter(adapterRecepient);	        
	        spinRecepient.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					spinRecepient.setSelection(position);
			        Map.Entry<String, String> item = (Map.Entry<String, String>) spinRecepient.getSelectedItem();
			        recepientId = Integer.parseInt(item.getKey());
				}

				@Override
				public void onNothingSelected(AdapterView<?> parentView) {
					// TODO Auto-generated method stub
					
				}
		    	 
			});
			linSpinRecepient.setVisibility(View.VISIBLE);
		}else if(loginData.getUserType()==Constants.PARENTS_KEY){
			linSpinRecepient.setVisibility(View.GONE);
		}
//		mName.setText("Agenda "+ loginData.getNama());
//		mPlace.setText(ctx.getResources().getString(R.string.school_name));
		inputMsg = (FloatLabeledEditText) rootView.findViewById(R.id.inputMsg);
		btnSend = (TextView) rootView.findViewById(R.id.btnSend);
		listViewMessages = (ListView) rootView.findViewById(R.id.list_view_messages);
		btnSend.setEnabled(true);
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/**
				 * Insert ke table message
				 * 
				 */
				if(recepientId <= 0 ){
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_recepient_empty));
				}else{
					if (!inputMsg.getText().toString().isEmpty()) {
						// send message
						btnSend.setEnabled(false);
						reqSendMessageTask = new ReqSendMessageTask();
						reqSendMessageTask.execute("");												
					} else {
						MessageUtils messageUtils = new MessageUtils(ctx);
		             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_empty));
					}
				}
								
			}
		});
		/**
		 *  get data inbox
		 */
        reqListInboxTask = new ReqListInboxTask();
        reqListInboxTask.execute("");
		return rootView;
		
	}
	
	public class ReqSendMessageTask  extends AsyncTask<String, Void, Boolean> {
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
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
    			reqSendMessageData.setRecepientId(recepientId);
           		  				    			
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
			 MessageUtils messageUtils = new MessageUtils(ctx);
          	 messageUtils.snackBarMessage(getActivity(),respString);
          	btnSend.setEnabled(true);
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
		               	        playBeep();
		               		}
		               		else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(getActivity(),messageVO.getMessageRc());
		               		}

						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_unexpected_error_server));
	               	}
             }else{
          	   MessageUtils messageUtils = new MessageUtils(ctx);
          	   messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_unexpected_error_server));
             }        
//             if(progressDialog.isShowing()){
//					progressDialog.dismiss();
//				}
             btnSend.setEnabled(true);
         }
	}
	
	public class ReqListInboxTask  extends AsyncTask<String, Void, Boolean> {
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {

    	}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
           		ReqListInboxData reqListInboxData = new ReqListInboxData();
    			reqListInboxData.setPassword(loginData.getPassword());
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
			 MessageUtils messageUtils = new MessageUtils(ctx);
          	 messageUtils.snackBarMessage(getActivity(),respString);
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
				             	messageUtils.snackBarMessage(getActivity(),messageVO.getMessageRc());
		               		}

						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_unexpected_error_server));
	               	}
             }else{
          	   MessageUtils messageUtils = new MessageUtils(ctx);
          	   messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_unexpected_error_server));
             }                     
         }
	}
	
	public List<InboxVO> constructDataInbox(String listJson) throws JsonParseException, JsonMappingException, IOException
    {
		
		RespListInboxVO respListInboxVO = HttpClientUtil.getObjectMapper(ctx).readValue(listJson, RespListInboxVO.class);	
		if(respListInboxVO.getListInboxVO()!=null){
			return respListInboxVO.getListInboxVO();
		}else{
			return new ArrayList<InboxVO>();
		}    
    }

	/**
	 * Plays device's default notification sound
	 * */
	public void playBeep() {

		try {
			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(ctx,
					notification);
			r.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
