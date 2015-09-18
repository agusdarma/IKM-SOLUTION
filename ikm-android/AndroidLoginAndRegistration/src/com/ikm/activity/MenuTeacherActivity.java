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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.ikm.R;
import com.ikm.data.Constants;
import com.ikm.data.Kelas;
import com.ikm.data.LoginData;
import com.ikm.data.MessageVO;
import com.ikm.data.ReqAddAgendaData;
import com.ikm.data.ReqListKelasData;
import com.ikm.data.RespListKelasVO;
import com.ikm.data.Subject;
import com.ikm.utils.HttpClientUtil;
import com.ikm.utils.MessageUtils;
import com.ikm.utils.SharedPreferencesUtils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import fr.ganfra.materialspinner.MaterialSpinner;


public class MenuTeacherActivity extends Activity {
	// LogCat tag
	private static final String TAG = MenuTeacherActivity.class.getSimpleName();
	private Context ctx;
	private ReqListKelasTask reqListKelasTask = null;
	private ReqAddAgendaTask reqAddAgendaTask = null;
	Shimmer shimmer;
	private Button btnInbox;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapterSubject;
	private ArrayAdapter<String> adapterJenisAgenda;
	private static final String[] ITEMS = {"Agenda", "Pengumuman Lain"};
	MaterialSpinner spinner1;
	MaterialSpinner spinJenisAgenda;
	MaterialSpinner spinSubject;
	ShimmerTextView tvTitle;
	MaterialCalendarView calendarView;
	MaterialEditText isiAgenda;
	ShimmerTextView tvTitleSchool;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher);
		ctx = MenuTeacherActivity.this;		
		ButtonRectangle btnBack = (ButtonRectangle) findViewById(R.id.btnBack);
		ButtonRectangle btnRefresh = (ButtonRectangle) findViewById(R.id.btnRefresh);
		tvTitle = (ShimmerTextView) findViewById(R.id.tvTitle);
		tvTitleSchool = (ShimmerTextView) findViewById(R.id.tvTitleSchool);
		calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
		ShimmerTextView tvVersion = (ShimmerTextView) findViewById(R.id.tvVersion);
		TextView lblWelcome = (TextView) findViewById(R.id.lblWelcome);
		ShimmerTextView tvFooter = (ShimmerTextView) findViewById(R.id.tvFooter);
		isiAgenda = (MaterialEditText) findViewById(R.id.isiAgenda);
		btnInbox = (Button) findViewById(R.id.btnInbox);
		ButtonRectangle btnSave = (ButtonRectangle) findViewById(R.id.btnSave);
		
        
		if (shimmer != null && shimmer.isAnimating()) {
			shimmer.cancel();
        } else {
        	shimmer = new Shimmer();
        	shimmer.start(tvTitle);
        	shimmer.start(tvTitleSchool);
        	shimmer.start(tvVersion);
        	shimmer.start(tvFooter);
        }
		
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = getIntent();
				overridePendingTransition(0, 0);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				finish();
				overridePendingTransition(0, 0);
				startActivity(intent);	
				
			}
		});
		

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
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!isiAgenda.getText().toString().isEmpty()
						&& spinner1.getAdapter()!=null && spinner1.getSelectedItem()!=null && calendarView.getSelectedDate()!=null
						&& spinJenisAgenda.getAdapter()!=null && spinJenisAgenda.getSelectedItem()!=null
						&& spinSubject.getAdapter()!=null && spinSubject.getSelectedItem()!=null) {
					if(spinner1.getSelectedItem().toString().equalsIgnoreCase("Kelas")||
							spinJenisAgenda.getSelectedItem().toString().equalsIgnoreCase("Jenis Agenda")||
							spinSubject.getSelectedItem().toString().equalsIgnoreCase("Subject")){
						MessageUtils messageUtils = new MessageUtils(ctx);
		             	messageUtils.snackBarMessage(MenuTeacherActivity.this,ctx.getResources().getString(R.string.message_detail_required));
					}else{
						// add agenda
						reqAddAgendaTask = new ReqAddAgendaTask();
						reqAddAgendaTask.execute("");
					}
														
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(MenuTeacherActivity.this,ctx.getResources().getString(R.string.message_detail_required));
				}					
			}
		});
		// get data kelas
        reqListKelasTask = new ReqListKelasTask();
        reqListKelasTask.execute("");
        
        adapterJenisAgenda = new ArrayAdapter<String>(MenuTeacherActivity.this, android.R.layout.simple_spinner_item, ITEMS);
        adapterJenisAgenda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        initSpinnerAgenda();
        
        /**
         * set welome label
         */
        LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
        lblWelcome.setText("Welcome, " + loginData.getNama());
	}
	
	@Override
    public void onBackPressed() { 
		Intent i = new Intent(ctx, LoginActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  		       			
		startActivity(i);	
		finish();
	}
	
	private void initSpinnerAgenda() {
		 spinJenisAgenda = (MaterialSpinner) findViewById(R.id.spinJenisAgenda);
	     spinJenisAgenda.setAdapter(adapterJenisAgenda);
	     spinJenisAgenda.setPaddingSafe(0,0,0,0);
	}
	
	private void initSpinnerHintAndFloatingLabel() {
        spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
        spinner1.setPaddingSafe(0,0,0,0);
        
    }
	
	private void initSpinnerSubject() {
		 spinSubject = (MaterialSpinner) findViewById(R.id.spinSubject);
		 spinSubject.setAdapter(adapterSubject);
		 spinSubject.setPaddingSafe(0,0,0,0);
	}
	
	public class ReqAddAgendaTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_add_agenda),ctx.getResources().getString(R.string.progress_dialog));
    			progressDialog.show();
    		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
           		ReqAddAgendaData reqAddAgendaData = new ReqAddAgendaData();
           		reqAddAgendaData.setPassword(loginData.getPassword());
           		reqAddAgendaData.setKodeSekolah(loginData.getKodeSekolah());
           		reqAddAgendaData.setNoInduk(loginData.getNoInduk());
           		reqAddAgendaData.setOriginRequest(Constants.ORIGIN_SOURCE);
           		reqAddAgendaData.setUserType(loginData.getUserType());
           		
           		if("Pengumuman Lain".equalsIgnoreCase(spinJenisAgenda.getSelectedItem().toString())){
           			reqAddAgendaData.setAgendaType(Constants.OTHER_AGENDA);
           		}else{
           			reqAddAgendaData.setAgendaType(Constants.GENERAL_AGENDA);
           		}
           		
    			reqAddAgendaData.setIsiAgenda(isiAgenda.getText().toString());
    			reqAddAgendaData.setKodeKelas(spinner1.getSelectedItem().toString());
    			reqAddAgendaData.setNamaKelas(spinner1.getSelectedItem().toString());
    			reqAddAgendaData.setSubject(spinSubject.getSelectedItem().toString());
    			reqAddAgendaData.setNamaSekolah(tvTitleSchool.getText().toString());
    			reqAddAgendaData.setKodeSekolah(loginData.getKodeSekolah());
    			reqAddAgendaData.setTanggalAgenda(calendarView.getSelectedDate().getDate());
           		
           		String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(reqAddAgendaData);
           		s = URLEncoder.encode(s, "UTF-8");
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_ADD_AGENDA);
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
			 reqAddAgendaTask = null;          
             if (success) {
	               	if(!respString.isEmpty()){
	               		try {	               			
	               			String respons = URLDecoder.decode(respString, "UTF-8");
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		if(messageVO.getRc()==0){			               			
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(MenuTeacherActivity.this,messageVO.getMessageRc());
				             	isiAgenda.setText("");
				             	spinner1.setSelection(0);
				             	spinJenisAgenda.setSelection(0);
				             	spinSubject.setSelection(0);
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
			               	        
			               	        adapterSubject = new ArrayAdapter<String>(MenuTeacherActivity.this, android.R.layout.simple_spinner_item, constructDataSubject(messageVO.getOtherMessage()));
			               	        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			               	        initSpinnerSubject();
		               				               			
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
		
		if(respListKelasVO.isWaliKelas()){
			btnInbox.setEnabled(true);
			btnInbox.setBackgroundColor(ctx.getResources().getColor(R.color.blue));
		}else{
			btnInbox.setEnabled(false);
			btnInbox.setBackgroundColor(ctx.getResources().getColor(R.color.grey));
		}
		
		String[] arrayKelas = new String[allKelas.size()];
		allKelas.toArray(arrayKelas );
        return  arrayKelas;
    }
	
	public String[] constructDataSubject(String listJson) throws JsonParseException, JsonMappingException, IOException{
		
		RespListKelasVO respListKelasVO = HttpClientUtil.getObjectMapper(ctx).readValue(listJson, RespListKelasVO.class);
		List<String> allSubject = new ArrayList<String>();
		if(respListKelasVO.getListSubjects()!=null){
			for (Subject temp : respListKelasVO.getListSubjects()) {
				allSubject.add(temp.getSubjectName());
				
			}
		}	 
		String[] arraySubject = new String[allSubject.size()];
		allSubject.toArray(arraySubject );
        return  arraySubject;
    }

}
