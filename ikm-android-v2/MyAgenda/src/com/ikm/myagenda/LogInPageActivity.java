package com.ikm.myagenda;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ikm.myagenda.data.Constants;
import com.ikm.myagenda.data.LoginData;
import com.ikm.myagenda.data.MessageVO;
import com.ikm.myagenda.gc.materialdesign.widgets.ProgressDialogParking;
import com.ikm.myagenda.util.HttpClientUtil;
import com.ikm.myagenda.util.MessageUtils;
import com.ikm.myagenda.util.SharedPreferencesUtils;
import com.ikm.myagenda.view.FloatLabeledEditText;

public class LogInPageActivity extends Activity implements OnClickListener {
	private static final String TAG = LogInPageActivity.class.getSimpleName();
	public static final String LOGIN_PAGE_AND_LOADERS_CATEGORY = "com.csform.android.uiapptemplate.LogInPageAndLoadersActivity";
	public static final String DARK = "Dark";
	public static final String LIGHT = "Light";
	public static final String TRAVEL = "Travel";
	public static final String SOCIAL = "Social";
	private FloatLabeledEditText kodeSekolah;
	private FloatLabeledEditText noInduk;
	private FloatLabeledEditText password;
	private LinearLayout linPassword;
	private RadioGroup radioTypeLogin;
	private Context ctx;
	String tipeLogin;
	private ReqLoginTask reqLoginTask = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
																// ActionBar
		setContentView(R.layout.activity_login_page_travel);
		ctx = LogInPageActivity.this;		
		TextView login;
		login = (TextView) findViewById(R.id.login);
		kodeSekolah = (FloatLabeledEditText) findViewById(R.id.kodeSekolah);
		noInduk = (FloatLabeledEditText) findViewById(R.id.noInduk);
		password = (FloatLabeledEditText) findViewById(R.id.password);
		radioTypeLogin = (RadioGroup) findViewById(R.id.radioTypeLogin);
		linPassword = (LinearLayout) findViewById(R.id.LinPassword);
		
		// get data from cache
		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
		if(loginData!=null){
			kodeSekolah.setText(loginData.getKodeSekolah());
			noInduk.setText(loginData.getNoInduk());
		}
		
		// default parent
		tipeLogin = Constants.PARENTS;
		linPassword.setVisibility(View.GONE);

		radioTypeLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
		    public void onCheckedChanged(RadioGroup rGroup, int checkedId)
		    {
		    	// get selected radio button from radioGroup
				int selectedId = radioTypeLogin.getCheckedRadioButtonId();
				// find the radiobutton by returned id
				RadioButton radioSelected = (RadioButton) findViewById(selectedId);
				if(String.valueOf(radioSelected.getText()).equalsIgnoreCase(ctx.getResources().getString(R.string.login_teacher))){
					tipeLogin = Constants.TEACHER;
					linPassword.setVisibility(View.VISIBLE);
				}else{
					tipeLogin = Constants.PARENTS;
					linPassword.setVisibility(View.GONE);
					password.setText("");
				}
		    }
		});
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(!tipeLogin.isEmpty()){
			if(Constants.PARENTS.equalsIgnoreCase(tipeLogin)){
				if (!kodeSekolah.getEditText().getText().toString().isEmpty() && !noInduk.getEditText().getText().toString().isEmpty() ) {
					// login user
					reqLoginTask = new ReqLoginTask();
					reqLoginTask.execute("");												
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(LogInPageActivity.this,ctx.getResources().getString(R.string.message_detail_required));
				}					
			}else if(Constants.TEACHER.equalsIgnoreCase(tipeLogin)){						
				if (!kodeSekolah.getEditText().getText().toString().isEmpty() && !noInduk.getEditText().getText().toString().isEmpty() && !password.getEditText().getText().toString().isEmpty()) {
					// login user
					reqLoginTask = new ReqLoginTask();
					reqLoginTask.execute("");
					
					
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(LogInPageActivity.this,ctx.getResources().getString(R.string.message_detail_required));
				}
				
			}
		}
	}
	
	public class ReqLoginTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_login),
    					ctx.getResources().getString(R.string.progress_dialog),ctx.getResources().getColor(R.color.main_color_500));
    			progressDialog.show();
    		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		LoginData loginData = new LoginData();
				loginData.setKodeSekolah(kodeSekolah.getText().toString());
				loginData.setNoInduk(noInduk.getText().toString());
				loginData.setOriginRequest(Constants.ORIGIN_SOURCE);
				loginData.setPassword("");
				loginData.setUserType(Constants.PARENTS_KEY);
				if (!password.getText().toString().isEmpty()) {
					loginData.setPassword(password.getText().toString());
					loginData.setUserType(Constants.TEACHER_KEY);
				}
				
           		String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(loginData);
           		s = URLEncoder.encode(s, "UTF-8");
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_LOGIN);
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
          	 messageUtils.snackBarMessage(LogInPageActivity.this,respString);
	     }
		
		 @Override
         protected void onPostExecute(final Boolean success) {
			 reqLoginTask = null;          
             if (success) {
	               	if(!respString.isEmpty()){
	               		try {	               
	               			String respons = URLDecoder.decode(respString, "UTF-8");
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		if(messageVO.getRc()==0){
		               			SharedPreferencesUtils.saveLoginData(messageVO.getOtherMessage(), ctx);
		               			LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
		               			Intent i = null;
		               			i = new Intent(ctx, LeftMenusSocialActivity.class);
		               			if(Constants.PARENTS_KEY==loginData.getUserType()){
		               				i.putExtra(Constants.KEY_LOGIN, Constants.PARENTS_KEY);
		               			}else if(Constants.TEACHER_KEY==loginData.getUserType()){
		               				i.putExtra(Constants.KEY_LOGIN, Constants.TEACHER_KEY);
		               			} 		       			
								startActivity(i);
								finish();
		               		}
		               		else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(LogInPageActivity.this,messageVO.getMessageRc());
		               		}

						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(LogInPageActivity.this,LogInPageActivity.this.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(LogInPageActivity.this,LogInPageActivity.this.getResources().getString(R.string.message_unexpected_error_server));
	               	}
             }else{
          	   MessageUtils messageUtils = new MessageUtils(ctx);
          	   messageUtils.snackBarMessage(LogInPageActivity.this,LogInPageActivity.this.getResources().getString(R.string.message_unexpected_error_server));
             }        
             if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
         }
	}
	
	@Override
    public void onBackPressed() { 
		this.finish();
	}
}
