package com.parking.activity;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.parking.R;
import com.parking.data.InqForgotPasswordRequest;
import com.parking.data.InqForgotPasswordResponse;
import com.parking.data.MessageVO;
import com.parking.utils.CipherUtil;
import com.parking.utils.CustomLabelAnimator;
import com.parking.utils.HttpClientUtil;
import com.parking.utils.MessageUtils;

public class ForgetPasswordActivity extends Activity {
	private static final String TAG = ForgetPasswordActivity.class.getSimpleName();
	private ButtonRectangle btnForgotPassword;
	private ButtonFlat btnLinkToLogin;
	private FloatLabel inputEmail;
	private Context ctx;
	private ReqForgotPasswordTask reqForgotPasswordTask = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		ctx = ForgetPasswordActivity.this;
		
		inputEmail = (FloatLabel) findViewById(R.id.email);
		btnForgotPassword = (ButtonRectangle) findViewById(R.id.btnForgotPassword);
		btnLinkToLogin = (ButtonFlat) findViewById(R.id.btnLinkToLoginScreen);

		// This is how you add a custom animator     
        inputEmail.setLabelAnimator(new CustomLabelAnimator());

		// Forgot Pass Button Click event
		btnForgotPassword.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String email = inputEmail.getEditText().getText().toString();
				if (!email.isEmpty()) {	
					reqForgotPasswordTask = new ReqForgotPasswordTask();
					reqForgotPasswordTask.execute("");       
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(ForgetPasswordActivity.this,ForgetPasswordActivity.this.getResources().getString(R.string.message_email_required));
				}
			}
		});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), PreLoginActivity.class);
				startActivity(i);
				finish();
			}
		});

	}
	
	public class ReqForgotPasswordTask extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
       		progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_forgot_password),ctx.getResources().getString(R.string.progress_dialog));
			progressDialog.show();			
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		InqForgotPasswordRequest inqForgotPasswordRequest = new InqForgotPasswordRequest();
				inqForgotPasswordRequest.setEmail(inputEmail.getEditText().getText().toString());
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(inqForgotPasswordRequest);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_FORGOT_PASSWORD);
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
	             messageUtils.snackBarMessage(ForgetPasswordActivity.this,respString);
	   	   }

           @Override
           protected void onPostExecute(final Boolean success) {
        	   reqForgotPasswordTask = null;          
               if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		InqForgotPasswordResponse inqForgotPasswordResponse = new InqForgotPasswordResponse();
		               		inqForgotPasswordResponse.setMessageVO(messageVO);
		               		if(inqForgotPasswordResponse.getMessageVO().getRc()==0){
		               			MessageUtils messageUtils = new MessageUtils(ctx);
		               			messageUtils.showDialogInfoCallback(ctx.getResources().getString(R.string.message_forgetPass_title), ctx.getResources().getString(R.string.message_forgetPass_success), buttonCallback);		             	
		               		}else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(ForgetPasswordActivity.this,messageVO.getMessageRc());
		               		}
						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(ForgetPasswordActivity.this,ForgetPasswordActivity.this.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(ForgetPasswordActivity.this,ForgetPasswordActivity.this.getResources().getString(R.string.message_unexpected_error_server));
	               	}
               }else{
            	   MessageUtils messageUtils = new MessageUtils(ctx);
            	   messageUtils.snackBarMessage(ForgetPasswordActivity.this,ForgetPasswordActivity.this.getResources().getString(R.string.message_unexpected_error_server));
               }  
               if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
           }

       }
	
	ButtonCallback buttonCallback = new ButtonCallback() {

		public void onPositive(MaterialDialog dialog) {
			Intent i = new Intent(ctx, PreLoginActivity.class);
			startActivity(i);
			finish();
        }
    };

}
