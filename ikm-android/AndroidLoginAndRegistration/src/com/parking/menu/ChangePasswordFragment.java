package com.parking.menu;

import java.io.IOException;
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

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.parking.R;
import com.parking.data.Constants;
import com.parking.data.InqChangePasswordRequest;
import com.parking.data.LoginData;
import com.parking.data.MessageVO;
import com.parking.utils.CipherUtil;
import com.parking.utils.CustomLabelAnimator;
import com.parking.utils.HttpClientUtil;
import com.parking.utils.MessageUtils;
import com.parking.utils.RedirectUtils;
import com.parking.utils.SharedPreferencesUtils;

/**
 * User: special
 * Date: 13-12-22
 * Time: ä¸‹å?ˆ1:31
 * Mail: specialcyci@gmail.com
 */
public class ChangePasswordFragment extends Fragment {
	private static final String TAG = ChangePasswordFragment.class.getSimpleName();
	private Context ctx;
	private ReqChangePasswordTask reqChangePasswordTask = null;
	private FloatLabel oldPassword;
	private FloatLabel newPassword;
	private String email;
	private String sessionkey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	 View rootView = inflater.inflate(R.layout.activity_change_password, container, false);
    	 ctx = container.getContext();
    	 ButtonRectangle btnChangePassword = (ButtonRectangle) rootView.findViewById(R.id.btnChangePassword);
    	 oldPassword = (FloatLabel) rootView.findViewById(R.id.oldPassword);
    	 newPassword = (FloatLabel) rootView.findViewById(R.id.newPassword); 
    	// This is how you add a custom animator
    	 oldPassword.setLabelAnimator(new CustomLabelAnimator());
    	 newPassword.setLabelAnimator(new CustomLabelAnimator());
    	 
    	 btnChangePassword.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View arg0) {
            	 String passLama = oldPassword.getEditText().getText().toString();
            	 String passBaru = newPassword.getEditText().getText().toString();
            	 LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);            	 
            	// ambil dari session untuk email, session key
            	 email = loginData.getEmail();
            	 sessionkey = loginData.getSessionKey();		 
 				if (!passLama.isEmpty() && !passBaru.isEmpty()&& !email.isEmpty()&& !sessionkey.isEmpty()) {	
 					reqChangePasswordTask = new ReqChangePasswordTask();
 					reqChangePasswordTask.execute("");       
 				} else {
 					MessageUtils messageUtils = new MessageUtils(ctx);
 	             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_password_required));
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
       		progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_change_password),ctx.getResources().getString(R.string.progress_dialog));
			progressDialog.show();
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		
           		InqChangePasswordRequest inqChangePasswordRequest = new InqChangePasswordRequest();
           		inqChangePasswordRequest.setEmail(email);
           		inqChangePasswordRequest.setPassword(oldPassword.getEditText().getText().toString());
           		inqChangePasswordRequest.setNewPassword(newPassword.getEditText().getText().toString());
           		inqChangePasswordRequest.setSessionKey(sessionkey);
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(inqChangePasswordRequest);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
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
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			final MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);		               	
		               		if(messageVO.getRc()==0){
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(getActivity(),messageVO.getOtherMessage());
				             	clearInput();
		               		}else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(getActivity(),messageVO.getMessageRc());
				             	new Timer().schedule(new TimerTask() {          
				             	    @Override
				             	    public void run() {
				             	    	if(messageVO.getRc()==Constants.SESSION_EXPIRED||messageVO.getRc()==Constants.SESSION_DIFFERENT||messageVO.getRc()==Constants.USER_NOT_LOGIN){
						             		RedirectUtils redirectUtils = new RedirectUtils(ctx, getActivity());
						             		redirectUtils.redirectToLogin();
				             	    	}  
				             	    }
				             	}, Constants.REDIRECT_DELAY_LOGIN);				             					             	
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
    	oldPassword.setText("");
    	newPassword.setText("");
    }

}
