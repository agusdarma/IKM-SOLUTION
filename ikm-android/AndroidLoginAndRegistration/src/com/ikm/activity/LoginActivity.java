package com.ikm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.Switch;
import com.gc.materialdesign.views.Switch.OnCheckListener;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.ikm.R;
import com.ikm.data.Constants;
import com.ikm.utils.CustomLabelAnimator;
import com.ikm.utils.MessageUtils;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


public class LoginActivity extends Activity {
	// LogCat tag
	private static final String TAG = LoginActivity.class.getSimpleName();
	private ButtonRectangle btnLogin;	
	private FloatLabel inputKodeSekolah;
	private FloatLabel inputNoInduk;
	private FloatLabel inputPassword;
	Switch switchView;
	private Context ctx;
//	private ReqLoginTask reqLoginTask = null;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Shimmer shimmer;
	String tipeLogin;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ctx = LoginActivity.this;
		ShimmerTextView tvFancy = (ShimmerTextView) findViewById(R.id.tvFancy);
		ShimmerTextView tvFooter = (ShimmerTextView) findViewById(R.id.tvFooter);
		ShimmerTextView tvVersion = (ShimmerTextView) findViewById(R.id.tvVersion);
		switchView = (Switch) findViewById(R.id.switchView);
		final TextView lblSwitchView = (TextView) findViewById(R.id.LblswitchView);	
		inputKodeSekolah = (FloatLabel) findViewById(R.id.kodeSekolah);
		inputNoInduk = (FloatLabel) findViewById(R.id.noInduk);
		inputPassword = (FloatLabel) findViewById(R.id.password);
		btnLogin = (ButtonRectangle) findViewById(R.id.btnLogin);
		
		
		// default parent
		tipeLogin = Constants.PARENTS;
		inputPassword.setVisibility(View.GONE);
		lblSwitchView.setText("Login "+ Constants.PARENTS);
		switchView.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(Switch view, boolean check) {
				if(switchView.isCheck()){
					tipeLogin = Constants.TEACHER;
					inputPassword.setVisibility(View.VISIBLE);
					lblSwitchView.setText("Login "+ Constants.TEACHER);
				}else{
					tipeLogin = Constants.PARENTS;
					inputPassword.setVisibility(View.GONE);
					lblSwitchView.setText("Login "+ Constants.PARENTS);
				}				
			}
		});

		if (shimmer != null && shimmer.isAnimating()) {
			shimmer.cancel();
        } else {
        	shimmer = new Shimmer();
        	shimmer.start(tvFancy);
        	shimmer.start(tvFooter);
        	shimmer.start(tvVersion);
        	
        }

		// This is how you add a custom animator
		inputPassword.setLabelAnimator(new CustomLabelAnimator());
		inputNoInduk.setLabelAnimator(new CustomLabelAnimator());
		inputKodeSekolah.setLabelAnimator(new CustomLabelAnimator());
        
		
		// Teacher button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if(!tipeLogin.isEmpty()){
					if(Constants.PARENTS.equalsIgnoreCase(tipeLogin)){
						if (!inputKodeSekolah.getEditText().getText().toString().isEmpty() && !inputNoInduk.getEditText().getText().toString().isEmpty() ) {							
							Intent i = new Intent(ctx, MenuParentActivity.class);
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  		       			
							startActivity(i);														
						} else {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(LoginActivity.this,ctx.getResources().getString(R.string.message_detail_required));
						}					
					}else if(Constants.TEACHER.equalsIgnoreCase(tipeLogin)){						
						if (!inputKodeSekolah.getEditText().getText().toString().isEmpty() && !inputNoInduk.getEditText().getText().toString().isEmpty() && !inputPassword.getEditText().getText().toString().isEmpty()) {
							
							Intent i = new Intent(ctx, MenuTeacherActivity.class);
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  		       			
							startActivity(i);
							
							
						} else {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(LoginActivity.this,ctx.getResources().getString(R.string.message_detail_required));
						}
						
					}
				}
			}

		});
		


	}
	
	
//	public class ReqLoginTask  extends AsyncTask<String, Void, Boolean> {
//		private ProgressDialogParking progressDialog = null;
//       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
//       	String respString = null;
//       	protected void onPreExecute() {
//    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_login),ctx.getResources().getString(R.string.progress_dialog));
//    			progressDialog.show();
//    		}
//		@Override
//		protected Boolean doInBackground(String... arg0) {
//			boolean result = false;
//           	try {
//           		InqLoginRequest inqLoginRequest = new InqLoginRequest();
//           		inqLoginRequest.setEmail(inputEmail.getEditText().getText().toString());
//           		inqLoginRequest.setPassword(inputPassword.getEditText().getText().toString());
//           		String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(inqLoginRequest);
//				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
//           		Log.d(TAG,"Request: " + s);
//                StringEntity entity = new StringEntity(s);    			
//    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_LOGIN);
//    			post.setHeader(HttpClientUtil.CONTENT_TYPE, HttpClientUtil.JSON);
//    			post.setEntity(entity);
//    			// Execute HTTP request
//    			Log.d(TAG,"Executing request: " + post.getURI());
//                HttpResponse response = client.execute(post);
//                HttpEntity respEntity = response.getEntity();
//                respString = EntityUtils.toString(respEntity);
//    			result = true;
//    			} catch (ClientProtocolException e) {
//    				Log.e(TAG, "ClientProtocolException : "+e);
//    				respString = ctx.getResources().getString(R.string.message_unexpected_error_message_server);
//    				cancel(true);    				
//    			} catch (IOException e) {
//    				Log.e(TAG, "IOException : "+e); 
//    				respString = ctx.getResources().getString(R.string.message_no_internet_connection);
//    				cancel(true);    				
//    			} catch (Exception e) {
//    				Log.e(TAG, "Exception : "+e);  
//    				respString = ctx.getResources().getString(R.string.message_unexpected_error_message_server);
//    				cancel(true);    				
//    			}
//           	return result;
//           }
//		
//		 @Override
//	     protected void onCancelled() {
//			 if(progressDialog.isShowing()){
//				progressDialog.dismiss();
//			 }
//			 MessageUtils messageUtils = new MessageUtils(ctx);
//          	 messageUtils.snackBarMessage(LoginActivity.this,respString);
//	     }
//		
//		 @Override
//         protected void onPostExecute(final Boolean success) {
//			 reqLoginTask = null;          
//             if (success) {
//	               	if(!respString.isEmpty()){
//	               		try {
//	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
//	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
//		               		if(messageVO.getRc()==0){
//		               			SharedPreferencesUtils.saveLoginData(messageVO.getOtherMessage(), ctx);
//		               			LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);		               					           
//		               			Intent i = new Intent(ctx, MenuActivity.class);
//								startActivity(i);
//								finish();
//		               		}
//		               		else{
//		               			MessageUtils messageUtils = new MessageUtils(ctx);
//				             	messageUtils.snackBarMessage(LoginActivity.this,messageVO.getMessageRc());
//		               		}
//
//						} catch (Exception e) {
//							MessageUtils messageUtils = new MessageUtils(ctx);
//			             	messageUtils.snackBarMessage(LoginActivity.this,LoginActivity.this.getResources().getString(R.string.message_unexpected_error_message_server));
//						}	            
//	               	}else{
//	               	   MessageUtils messageUtils = new MessageUtils(ctx);
//	             	   messageUtils.snackBarMessage(LoginActivity.this,LoginActivity.this.getResources().getString(R.string.message_unexpected_error_server));
//	               	}
//             }else{
//          	   MessageUtils messageUtils = new MessageUtils(ctx);
//          	   messageUtils.snackBarMessage(LoginActivity.this,LoginActivity.this.getResources().getString(R.string.message_unexpected_error_server));
//             }        
//             if(progressDialog.isShowing()){
//					progressDialog.dismiss();
//				}
//         }
//	}
	
	

}
