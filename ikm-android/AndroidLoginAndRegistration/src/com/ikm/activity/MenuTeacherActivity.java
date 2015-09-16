package com.ikm.activity;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.ikm.R;
import com.ikm.data.Constants;
import com.ikm.data.Kelas;
import com.ikm.data.LoginData;
import com.ikm.data.MessageVO;
import com.ikm.data.ReqListKelasData;
import com.ikm.data.RespListKelasVO;
import com.ikm.utils.HttpClientUtil;
import com.ikm.utils.MessageUtils;
import com.ikm.utils.SharedPreferencesUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import fr.ganfra.materialspinner.MaterialSpinner;


public class MenuTeacherActivity extends Activity {
	// LogCat tag
	private static final String TAG = MenuTeacherActivity.class.getSimpleName();
	private ButtonRectangle btnSave;
	private Context ctx;
	private ReqListKelasTask reqListKelasTask = null;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Shimmer shimmer;
	private Button btnInbox;
	private ArrayAdapter<String> adapter;
	private static final String[] ITEMS = {"Science 6A", "Science 6B", "BI 6A", "Inggris 3C", "Inggris 3A", "Inggris 3E","PENGUMUMAN LAIN"};
	MaterialSpinner spinner1;
	ShimmerTextView tvTitle;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher);
		ctx = MenuTeacherActivity.this;		
		ButtonRectangle btnBack = (ButtonRectangle) findViewById(R.id.btnBack);
		tvTitle = (ShimmerTextView) findViewById(R.id.tvTitle);
		ShimmerTextView tvTitleSchool = (ShimmerTextView) findViewById(R.id.tvTitleSchool);
		ShimmerTextView tvVersion = (ShimmerTextView) findViewById(R.id.tvVersion);
		ShimmerTextView tvFooter = (ShimmerTextView) findViewById(R.id.tvFooter);
		MaterialEditText isiAgenda = (MaterialEditText) findViewById(R.id.isiAgenda);
		btnInbox = (Button) findViewById(R.id.btnInbox);
		btnSave = (ButtonRectangle) findViewById(R.id.btnSave);
		
        
		if (shimmer != null && shimmer.isAnimating()) {
			shimmer.cancel();
        } else {
        	shimmer = new Shimmer();
        	shimmer.start(tvTitle);
        	shimmer.start(tvTitleSchool);
        	shimmer.start(tvVersion);
        	shimmer.start(tvFooter);
        }
		

		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx, LoginActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  		       			
				startActivity(i);	
				finish();
			}
		});
		btnInbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx, InboxActivity.class);	
				i.putExtra(Constants.FROM_MENU, Constants.TEACHER);
				i.putExtra(Constants.AGENDA_NAME, tvTitle.getText().toString());
				startActivity(i);	
			}
		});
		// get data kelas
        reqListKelasTask = new ReqListKelasTask();
        reqListKelasTask.execute("");

	}
	
	@Override
    public void onBackPressed() { 
		Intent i = new Intent(ctx, LoginActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  		       			
		startActivity(i);	
		finish();
	}
	
	private void initSpinnerHintAndFloatingLabel() {
        spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
        spinner1.setPaddingSafe(0,0,0,0);
    }
	
	
	public class ReqListKelasTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_kelas),ctx.getResources().getString(R.string.progress_dialog));
    			progressDialog.show();
    		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
           		ReqListKelasData reqListKelasData = new ReqListKelasData();
    			reqListKelasData.setPassword(loginData.getPassword());
    			reqListKelasData.setKodeSekolah(loginData.getKodeSekolah());
    			reqListKelasData.setNoInduk(loginData.getNoInduk());
    			reqListKelasData.setOriginRequest(Constants.ORIGIN_SOURCE);
    			reqListKelasData.setUserType(loginData.getUserType());
           		String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(reqListKelasData);
           		s = URLEncoder.encode(s, "UTF-8");
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_KELAS);
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
          	 messageUtils.snackBarMessage(MenuTeacherActivity.this,respString);
	     }
		
		 @Override
         protected void onPostExecute(final Boolean success) {
			 reqListKelasTask = null;          
             if (success) {
	               	if(!respString.isEmpty()){
	               		try {	               			
	               			String respons = URLDecoder.decode(respString, "UTF-8");
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		if(messageVO.getRc()==0){	
		               			
		               				adapter = new ArrayAdapter<String>(MenuTeacherActivity.this, android.R.layout.simple_spinner_item, constructDataKelas(messageVO.getOtherMessage()));
			               	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			               	        initSpinnerHintAndFloatingLabel();
		               				               			
		               		}
		               		else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(MenuTeacherActivity.this,messageVO.getMessageRc());
		               		}

						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(MenuTeacherActivity.this,MenuTeacherActivity.this.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(MenuTeacherActivity.this,MenuTeacherActivity.this.getResources().getString(R.string.message_unexpected_error_server));
	               	}
             }else{
          	   MessageUtils messageUtils = new MessageUtils(ctx);
          	   messageUtils.snackBarMessage(MenuTeacherActivity.this,MenuTeacherActivity.this.getResources().getString(R.string.message_unexpected_error_server));
             }        
             if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
         }
	}
	
	public String[] constructDataKelas(String listJson) throws JsonParseException, JsonMappingException, IOException{
		
		RespListKelasVO respListKelasVO = HttpClientUtil.getObjectMapper(ctx).readValue(listJson, RespListKelasVO.class);
		List<String> allKelas = new ArrayList<String>();
		String agendaName = "";
		if(respListKelasVO.getListKelas()!=null){
			for (Kelas temp : respListKelasVO.getListKelas()) {
				if(agendaName.isEmpty()){
					agendaName = Constants.AGENDA+temp.getNamaKelas();
					tvTitle.setText(agendaName);
				}
				allKelas.add(temp.getNamaKelas());
				
			}
		}
		if(respListKelasVO.getJumlahMessageUnread()>0){
			btnInbox.setText(respListKelasVO.getJumlahMessageUnread()+ " " + ctx.getResources().getString(R.string.msg_unread));
		}else{
			btnInbox.setText(ctx.getResources().getString(R.string.inbox));
		}		 
		String[] arrayKelas = new String[allKelas.size()];
		allKelas.toArray(arrayKelas );
        return  arrayKelas;
    }

}
