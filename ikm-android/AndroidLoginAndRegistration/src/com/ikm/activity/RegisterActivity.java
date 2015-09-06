package com.ikm.activity;


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
import com.ikm.data.InqRegistrationRequest;
import com.ikm.data.MessageVO;
import com.ikm.utils.CipherUtil;
import com.ikm.utils.CustomLabelAnimator;
import com.ikm.utils.HttpClientUtil;
import com.ikm.utils.MessageUtils;
import com.ikm.R;

public class RegisterActivity extends Activity {
	private static final String TAG = RegisterActivity.class.getSimpleName();
	private ButtonRectangle btnRegister;
	private ButtonFlat btnLinkToLogin;
	private FloatLabel inputFullName;
	private FloatLabel inputEmail;
	private FloatLabel inputPassword;
	private FloatLabel inputLicenseNo;
	private FloatLabel inputPhoneNo;
	private Context ctx;
	private ReqRegistrationTask reqRegistrationTask = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		inputFullName = (FloatLabel) findViewById(R.id.name);
		inputEmail = (FloatLabel) findViewById(R.id.email);
		inputLicenseNo = (FloatLabel) findViewById(R.id.licenseNo);
		inputPhoneNo = (FloatLabel) findViewById(R.id.phoneNo);
		inputPassword = (FloatLabel) findViewById(R.id.password);
		btnRegister = (ButtonRectangle) findViewById(R.id.btnRegister);
		btnLinkToLogin = (ButtonFlat) findViewById(R.id.btnLinkToLoginScreen);
		
		// This is how you add a custom animator
		inputFullName.setLabelAnimator(new CustomLabelAnimator());
        inputEmail.setLabelAnimator(new CustomLabelAnimator());
        inputLicenseNo.setLabelAnimator(new CustomLabelAnimator());
        inputPhoneNo.setLabelAnimator(new CustomLabelAnimator());
        inputPassword.setLabelAnimator(new CustomLabelAnimator());
        
		
		ctx = RegisterActivity.this;		

		

		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String name = inputFullName.getEditText().getText().toString();
				String email = inputEmail.getEditText().getText().toString();
				String password = inputPassword.getEditText().getText().toString();
				String phoneNo = inputPhoneNo.getEditText().getText().toString();
				String licenseNo = inputLicenseNo.getEditText().getText().toString();
				

				if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()&& !phoneNo.isEmpty()&& !licenseNo.isEmpty()) {
					
					// register user
					reqRegistrationTask = new ReqRegistrationTask();
					reqRegistrationTask.execute("");
					
					
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(RegisterActivity.this,RegisterActivity.this.getResources().getString(R.string.message_detail_required));
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
	
	public class ReqRegistrationTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {       					
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_register),ctx.getResources().getString(R.string.progress_dialog));
    			progressDialog.show();
    		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		InqRegistrationRequest inqRegRequest = new InqRegistrationRequest();           		
				inqRegRequest.setName(inputFullName.getEditText().getText().toString());
				inqRegRequest.setLicenseNo(inputLicenseNo.getEditText().getText().toString());
				inqRegRequest.setEmail(inputEmail.getEditText().getText().toString());
				inqRegRequest.setPassword(inputPassword.getEditText().getText().toString());
				inqRegRequest.setPhoneNo(inputPhoneNo.getEditText().getText().toString());
           		String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(inqRegRequest);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_USER_REG);
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
         	 messageUtils.snackBarMessage(RegisterActivity.this,respString);
	     }
		
		 @Override
         protected void onPostExecute(final Boolean success) {
			 reqRegistrationTask = null;          
             if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		if(messageVO.getRc()==0){
		               			MessageUtils messageUtils = new MessageUtils(ctx);
					            messageUtils.showDialogInfoCallback(ctx.getResources().getString(R.string.message_register_title), ctx.getResources().getString(R.string.message_register_success), buttonCallback); 					             	
		               		}
		               		else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(RegisterActivity.this,messageVO.getMessageRc());
		               		}

						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(RegisterActivity.this,RegisterActivity.this.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(RegisterActivity.this,RegisterActivity.this.getResources().getString(R.string.message_unexpected_error_server));
	               	}
             }else{
          	   MessageUtils messageUtils = new MessageUtils(ctx);
          	   messageUtils.snackBarMessage(RegisterActivity.this,RegisterActivity.this.getResources().getString(R.string.message_unexpected_error_server));
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
