package com.ikm.myagenda.fragment;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.data.Constants;
import com.ikm.myagenda.data.InqChangePasswordRequest;
import com.ikm.myagenda.data.LoginData;
import com.ikm.myagenda.data.MessageVO;
import com.ikm.myagenda.gc.materialdesign.widgets.ProgressDialogParking;
import com.ikm.myagenda.util.CipherUtil;
import com.ikm.myagenda.util.HttpClientUtil;
import com.ikm.myagenda.util.MessageUtils;
import com.ikm.myagenda.util.RedirectUtils;
import com.ikm.myagenda.util.SharedPreferencesUtils;
import com.ikm.myagenda.view.FloatLabeledEditText;

public class ChangePasswordFragment extends Fragment {
	private static final String TAG = ChangePasswordFragment.class.getSimpleName();
	private Context ctx;
	View rootView;
	private FloatLabeledEditText txt_old_password;
	private FloatLabeledEditText txt_new_password;
	private FloatLabeledEditText txt_confirm_new_password;
	private TextView btnChangePassword;
	
	private ReqChangePasswordTask reqChangePasswordTask = null;

	public static ChangePasswordFragment newInstance() {
		return new ChangePasswordFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_change_password,
				container, false);
		ctx = this.getActivity().getBaseContext();
		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
		txt_old_password = (FloatLabeledEditText) rootView.findViewById(R.id.txt_old_password);
		txt_new_password = (FloatLabeledEditText) rootView.findViewById(R.id.txt_new_password);
		txt_confirm_new_password = (FloatLabeledEditText) rootView.findViewById(R.id.txt_confirm_new_password);
		btnChangePassword = (TextView) rootView.findViewById(R.id.change_password);
		
		btnChangePassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!txt_old_password.getEditText().getText().toString().isEmpty() && !txt_new_password.getEditText().getText().toString().isEmpty()
						&& !txt_confirm_new_password.getEditText().getText().toString().isEmpty()) {
							if(!txt_new_password.getEditText().getText().toString().equals(txt_confirm_new_password.getEditText().getText().toString())){
								MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_new_confirm_password));
							}else{
								// Change Password
								reqChangePasswordTask = new ReqChangePasswordTask();
			 					reqChangePasswordTask.execute("");
							}
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_detail_required));
				}
			}
		});
		
		return rootView;
	}
	
	public class ReqChangePasswordTask extends AsyncTask<String, Void, Boolean> {
    	private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {       		
       		progressDialog = new ProgressDialogParking(getActivity(), ctx.getResources().getString(R.string.process_change_pass),
					ctx.getResources().getString(R.string.progress_dialog),ctx.getResources().getColor(R.color.main_color_500));
    			progressDialog.show();
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
           		InqChangePasswordRequest inqChangePasswordRequest = new InqChangePasswordRequest();           		
           		inqChangePasswordRequest.setPassword(loginData.getPassword());
           		inqChangePasswordRequest.setKodeSekolah(loginData.getKodeSekolah());
           		inqChangePasswordRequest.setNoInduk(loginData.getNoInduk());
           		inqChangePasswordRequest.setOriginRequest(Constants.ORIGIN_SOURCE);
           		inqChangePasswordRequest.setUserType(loginData.getUserType());
           		inqChangePasswordRequest.setId(loginData.getId()); 				    			
           		inqChangePasswordRequest.setOldPassword(txt_old_password.getEditText().getText().toString());
           		inqChangePasswordRequest.setNewPassword(txt_new_password.getEditText().getText().toString());           		
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(inqChangePasswordRequest);
				s = URLEncoder.encode(s, "UTF-8");
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_CHANGE_PASSWORD);
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
             	 messageUtils.snackBarMessage(getActivity(),respString);
   	     }

           @Override
           protected void onPostExecute(final Boolean success) {
        	   reqChangePasswordTask = null;          
               if (success) {
	               	if(!respString.isEmpty()){
	               		try {	               			
	               			String respons = URLDecoder.decode(respString, "UTF-8");
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		if(messageVO.getRc()==0){			               			
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(getActivity(),messageVO.getOtherMessage());
				             	clearInput();
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
               if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
           }

       }
    
    private void clearInput(){
    	txt_old_password.setText("");
    	txt_new_password.setText("");
    	txt_confirm_new_password.setText("");
    }
	
}
