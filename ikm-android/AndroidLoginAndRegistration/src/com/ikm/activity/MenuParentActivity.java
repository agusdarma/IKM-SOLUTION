package com.ikm.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.ikm.R;
import com.ikm.swipelistview.sample.adapters.AgendaAdapter;
import com.ikm.swipelistview.sample.adapters.AgendaVO;
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
	
	RecyclerView recyclerView;
    ArrayList<AgendaVO> itemsList = new ArrayList<AgendaVO>();
    AgendaAdapter adapter;
	MaterialSpinner spinner1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent);
		ctx = MenuParentActivity.this;
		recyclerView = (RecyclerView) findViewById(R.id.listAgenda);
		LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);        
        adapter = new AgendaAdapter(ctx, getData());
        recyclerView.setAdapter(adapter);
        
//		ShimmerTextView tvTitle = (ShimmerTextView) findViewById(R.id.tvTitle);
//		ShimmerTextView tvTitleSchool = (ShimmerTextView) findViewById(R.id.tvTitleSchool);
//		ShimmerTextView tvTitleClass = (ShimmerTextView) findViewById(R.id.tvTitleClass);		
//		ShimmerTextView tvVersion = (ShimmerTextView) findViewById(R.id.tvVersion);
//		ShimmerTextView tvFooter = (ShimmerTextView) findViewById(R.id.tvFooter);
//
//		if (shimmer != null && shimmer.isAnimating()) {
//			shimmer.cancel();
//        } else {
//        	shimmer = new Shimmer();
//        	shimmer.start(tvTitle);        	
//        	shimmer.start(tvVersion);
//        	shimmer.start(tvFooter);        	
//        }
		
		

	}
	
	
	public ArrayList<AgendaVO> getData()
    {
        ArrayList<AgendaVO> it = new ArrayList<AgendaVO>();
        AgendaVO items1 = new AgendaVO();
        items1.setAgendaType(1);
        items1.setIsiAgenda("Bahasa Indonesia PR halaman 17");
        items1.setTglAgenda("07 September 2015");
        it.add(items1);
        AgendaVO items2 = new AgendaVO();
        items2.setAgendaType(1);
        items2.setIsiAgenda("Science PR halaman 18");
        items2.setTglAgenda("06 September 2015");
        it.add(items2);
        AgendaVO items3 = new AgendaVO();
        items3.setAgendaType(1);
        items3.setIsiAgenda("Inggris PR halaman 19");
        items3.setTglAgenda("05 September 2015");
        it.add(items3);
        AgendaVO items4 = new AgendaVO();
        items4.setAgendaType(1);
        items4.setIsiAgenda("Sejarah PR halaman 20");
        items4.setTglAgenda("04 September 2015");
        it.add(items4);
        return it;
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
