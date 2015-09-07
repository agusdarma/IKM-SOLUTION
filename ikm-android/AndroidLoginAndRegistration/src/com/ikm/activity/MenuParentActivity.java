package com.ikm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.ikm.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import fr.ganfra.materialspinner.MaterialSpinner;


public class MenuParentActivity extends Activity {
	// LogCat tag
	private static final String TAG = MenuParentActivity.class.getSimpleName();
	private Context ctx;
//	private ReqLoginTask reqLoginTask = null;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Shimmer shimmer;
	
	private ArrayAdapter<String> adapter;
	private static final String[] ITEMS = {"Science 6A", "Science 6B", "BI 6A", "Inggris 3C", "Inggris 3A", "Inggris 3E"};
	MaterialSpinner spinner1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent);
		ctx = MenuParentActivity.this;
		
		
		ShimmerTextView tvTitle = (ShimmerTextView) findViewById(R.id.tvTitle);
		ShimmerTextView tvTitleSchool = (ShimmerTextView) findViewById(R.id.tvTitleSchool);
		ShimmerTextView tvTitleClass = (ShimmerTextView) findViewById(R.id.tvTitleClass);		
		ShimmerTextView tvVersion = (ShimmerTextView) findViewById(R.id.tvVersion);
		ShimmerTextView tvFooter = (ShimmerTextView) findViewById(R.id.tvFooter);
		
		

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		if (shimmer != null && shimmer.isAnimating()) {
			shimmer.cancel();
        } else {
        	shimmer = new Shimmer();
        	shimmer.start(tvTitle);
        	shimmer.start(tvTitleSchool);
        	shimmer.start(tvVersion);
        	shimmer.start(tvFooter);
        	shimmer.start(tvTitleClass);
        }

		// This is how you add a custom animator
//		inputPassword.setLabelAnimator(new CustomLabelAnimator());
//		inputNoInduk.setLabelAnimator(new CustomLabelAnimator());
//		inputKodeSekolah.setLabelAnimator(new CustomLabelAnimator());
        
		
		// Teacher button Click Event
//		btnLogin.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View view) {
//
//			}
//
//		});
//		
//		
//				btnBack.setOnClickListener(new View.OnClickListener() {
//
//					public void onClick(View view) {
//						finish();
//					}
//
//				});


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
	
	// validating email id
	private boolean isValidEmail(String email) {
		if (email == null) {
	        return false;
	    } else {
	    	return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	    }
	}

}
