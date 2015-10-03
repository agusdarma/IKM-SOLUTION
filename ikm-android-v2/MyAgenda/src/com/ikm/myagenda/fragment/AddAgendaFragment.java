package com.ikm.myagenda.fragment;

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

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.data.Constants;
import com.ikm.myagenda.data.Kelas;
import com.ikm.myagenda.data.LoginData;
import com.ikm.myagenda.data.MessageVO;
import com.ikm.myagenda.data.ReqAddAgendaData;
import com.ikm.myagenda.data.ReqListKelasData;
import com.ikm.myagenda.data.RespListKelasVO;
import com.ikm.myagenda.data.Subject;
import com.ikm.myagenda.fr.ganfra.materialspinner.MaterialSpinner;
import com.ikm.myagenda.gc.materialdesign.widgets.ProgressDialogParking;
import com.ikm.myagenda.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.ikm.myagenda.util.HttpClientUtil;
import com.ikm.myagenda.util.MessageUtils;
import com.ikm.myagenda.util.SharedPreferencesUtils;
import com.ikm.myagenda.view.FloatLabeledEditText;

public class AddAgendaFragment extends Fragment {
	private static final String TAG = AddAgendaFragment.class.getSimpleName();
	private ImageView mImage;
	private TextView mName;
	private TextView mPlace;
	private Context ctx;
	View rootView;
	private ReqListKelasTask reqListKelasTask = null;
	private ReqAddAgendaTask reqAddAgendaTask = null;
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapterSubject;
	private ArrayAdapter<String> adapterJenisAgenda;
	private static final String[] ITEMS = {"Agenda", "Pengumuman Lain"};
	private MaterialSpinner spinner1;
	private MaterialSpinner spinJenisAgenda;
	private MaterialSpinner spinSubject;
	private MaterialCalendarView calendarView;
	private FloatLabeledEditText isiAgenda;
	private TextView btnSave;	

	public static AddAgendaFragment newInstance() {
		return new AddAgendaFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_add_myagenda,
				container, false);
		ctx = this.getActivity().getBaseContext();
		mImage = (ImageView) rootView.findViewById(R.id.expandable_lv_social_image);
		mName = (TextView) rootView.findViewById(R.id.expandable_lv_social_name);
		mPlace = (TextView) rootView.findViewById(R.id.expandable_lv_social_place);
//		ImageUtil.displayRoundImage(mImage,
//				"http://pengaja.com/uiapptemplate/newphotos/profileimages/2.jpg", null);
		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
		mName.setText("Agenda "+ loginData.getNama());
		mPlace.setText(ctx.getResources().getString(R.string.school_name));
		calendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
		isiAgenda = (FloatLabeledEditText) rootView.findViewById(R.id.isiAgenda);
		btnSave = (TextView) rootView.findViewById(R.id.btnSave);
		
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
		             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_detail_required));
					}else{
						// add agenda
						reqAddAgendaTask = new ReqAddAgendaTask();
						reqAddAgendaTask.execute("");
					}
														
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_detail_required));
				}					
			}
		});
		// get data kelas
        reqListKelasTask = new ReqListKelasTask();
        reqListKelasTask.execute("");
        
        adapterJenisAgenda = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, ITEMS);
        adapterJenisAgenda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        initSpinnerAgenda();
		
		return rootView;
	}
	
	private void initSpinnerAgenda() {
		 spinJenisAgenda = (MaterialSpinner) rootView.findViewById(R.id.spinJenisAgenda);
	     spinJenisAgenda.setAdapter(adapterJenisAgenda);
	     spinJenisAgenda.setPaddingSafe(0,0,0,0);
	     
	     spinJenisAgenda.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				String selected = parentView.getItemAtPosition(position).toString();

		        if("Pengumuman Lain".equals(selected)){
		        	spinSubject.setVisibility(View.GONE);
		        }else {
		        	spinSubject.setVisibility(View.VISIBLE);
		        }				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// TODO Auto-generated method stub
				
			}
	    	 
		});
	}
	
	private void initSpinnerHintAndFloatingLabel() {
       spinner1 = (MaterialSpinner) rootView.findViewById(R.id.spinner1);
       spinner1.setAdapter(adapter);
       spinner1.setPaddingSafe(0,0,0,0);
       
   }
	
	private void initSpinnerSubject() {
		 spinSubject = (MaterialSpinner) rootView.findViewById(R.id.spinSubject);
		 spinSubject.setAdapter(adapterSubject);
		 spinSubject.setPaddingSafe(0,0,0,0);
	}
	
	public class ReqAddAgendaTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
    			progressDialog = new ProgressDialogParking(getActivity(), ctx.getResources().getString(R.string.process_add_agenda),
    					ctx.getResources().getString(R.string.progress_dialog),ctx.getResources().getColor(R.color.main_color_500));
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
    			reqAddAgendaData.setNamaSekolah(mPlace.getText().toString());
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
          	 messageUtils.snackBarMessage(getActivity(),respString);
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
				             	messageUtils.snackBarMessage(getActivity(),messageVO.getMessageRc());
				             	isiAgenda.setText("");
				             	spinner1.setSelection(0);
				             	spinJenisAgenda.setSelection(0);
				             	spinSubject.setSelection(0);
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
	
	
	public class ReqListKelasTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {    			
    			progressDialog = new ProgressDialogParking(getActivity(), ctx.getResources().getString(R.string.process_kelas),
    					ctx.getResources().getString(R.string.progress_dialog),ctx.getResources().getColor(R.color.main_color_500));
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
          	 messageUtils.snackBarMessage(getActivity(),respString);
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
		               			
		               				adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, constructDataKelas(messageVO.getOtherMessage()));
			               	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			               	        initSpinnerHintAndFloatingLabel();
			               	        
			               	        adapterSubject = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, constructDataSubject(messageVO.getOtherMessage()));
			               	        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			               	        initSpinnerSubject();
		               				               			
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
	
	public String[] constructDataKelas(String listJson) throws JsonParseException, JsonMappingException, IOException{
		
		RespListKelasVO respListKelasVO = HttpClientUtil.getObjectMapper(ctx).readValue(listJson, RespListKelasVO.class);
		List<String> allKelas = new ArrayList<String>();
//		String agendaName = "";
		if(respListKelasVO.getListKelas()!=null){
			for (Kelas temp : respListKelasVO.getListKelas()) {
//				if(agendaName.isEmpty()){
//					agendaName = Constants.AGENDA+temp.getNamaKelas();
//					tvTitle.setText(agendaName);
//				}
				allKelas.add(temp.getNamaKelas());
				
			}
		}
//		if(respListKelasVO.getJumlahMessageUnread()>0){
//			btnInbox.setText(respListKelasVO.getJumlahMessageUnread()+ " " + ctx.getResources().getString(R.string.msg_unread));
//		}else{
//			btnInbox.setText(ctx.getResources().getString(R.string.inbox));
//		}	
		
//		if(respListKelasVO.isWaliKelas()){
//			btnInbox.setEnabled(true);
//			btnInbox.setBackgroundColor(ctx.getResources().getColor(R.color.blue));
//		}else{
//			btnInbox.setEnabled(false);
//			btnInbox.setBackgroundColor(ctx.getResources().getColor(R.color.grey));
//		}
		
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
