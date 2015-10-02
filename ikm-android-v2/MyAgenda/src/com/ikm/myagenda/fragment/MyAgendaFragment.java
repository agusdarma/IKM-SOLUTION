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
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ikm.myagenda.R;
import com.ikm.myagenda.adapter.AgendaViewExpandableAdapter;
import com.ikm.myagenda.data.AgendaDetailVO;
import com.ikm.myagenda.data.AgendaHeader;
import com.ikm.myagenda.data.AgendaHeaderVO;
import com.ikm.myagenda.data.AgendaItems;
import com.ikm.myagenda.data.Constants;
import com.ikm.myagenda.data.LoginData;
import com.ikm.myagenda.data.MessageVO;
import com.ikm.myagenda.data.ReqListAgendaData;
import com.ikm.myagenda.data.RespListAgendaVO;
import com.ikm.myagenda.gc.materialdesign.widgets.ProgressDialogParking;
import com.ikm.myagenda.util.HttpClientUtil;
import com.ikm.myagenda.util.MessageUtils;
import com.ikm.myagenda.util.SharedPreferencesUtils;
import com.ikm.myagenda.view.AnimatedExpandableListView;

public class MyAgendaFragment extends Fragment {
	private static final String TAG = MyAgendaFragment.class.getSimpleName();
	private AnimatedExpandableListView listView;
	private AgendaViewExpandableAdapter adapter;
	private ImageView mImage;
	private TextView mName;
	private TextView mPlace;
	private Context ctx;
	boolean typeAgenda; // false agenda true pengumuman lain
	View rootView;
	private ReqListAgendaTask reqListAgendaTask = null;

	public static MyAgendaFragment newInstance() {
		return new MyAgendaFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_myagenda_list_views,
				container, false);
		ctx = this.getActivity().getBaseContext();
		mImage = (ImageView) rootView.findViewById(R.id.expandable_lv_social_image);
		mName = (TextView) rootView.findViewById(R.id.expandable_lv_social_name);
		mPlace = (TextView) rootView.findViewById(R.id.expandable_lv_social_place);
		typeAgenda = false;
		final RadioGroup radioTypeAgenda = (RadioGroup) rootView.findViewById(R.id.radioTypeAgenda);
//		ImageUtil.displayRoundImage(mImage,
//				"http://pengaja.com/uiapptemplate/newphotos/profileimages/2.jpg", null);
		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
		mName.setText("Agenda "+ loginData.getNama());
		mPlace.setText(ctx.getResources().getString(R.string.school_name));
		

//		List<AgendaHeader> items = new ArrayList<AgendaHeader>();
//		items = fillData(items);
//
//		adapter = new AgendaViewExpandableAdapter(ctx,items);
//		adapter.setData(items);
		
		/**
	      * get data agenda 
	      */
		reqListAgendaTask = new ReqListAgendaTask();
		reqListAgendaTask.execute("");

		listView = (AnimatedExpandableListView) rootView.findViewById(R.id.expandable_lv_social_list_view);

		// In order to show animations, we need to use a custom click handler
		// for our ExpandableListView.
		listView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// We call collapseGroupWithAnimation(int) and
				// expandGroupWithAnimation(int) to animate group
				// expansion/collapse.
				if (listView.isGroupExpanded(groupPosition)) {
					listView.collapseGroupWithAnimation(groupPosition);
				} else {
					listView.expandGroupWithAnimation(groupPosition);
				}
				return true;
			}
		});
		
		radioTypeAgenda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
		    public void onCheckedChanged(RadioGroup rGroup, int checkedId)
		    {
		    	// get selected radio button from radioGroup
				int selectedId = radioTypeAgenda.getCheckedRadioButtonId();
				// find the radiobutton by returned id
				RadioButton radioSelected = (RadioButton) rootView.findViewById(selectedId);
				if(String.valueOf(radioSelected.getText()).equalsIgnoreCase(ctx.getResources().getString(R.string.type_pengumuman))){
					typeAgenda = true;
				}else{
					typeAgenda = false;
				}
				/**
			      * get data agenda 
			      */
				reqListAgendaTask = new ReqListAgendaTask();
				reqListAgendaTask.execute("");
		    }
		});
		
		return rootView;
	}
	
	private List<AgendaHeader> fillDataInterface(List<AgendaHeader> items,RespListAgendaVO respListAgendaVO) {
		
		for (AgendaHeaderVO headerVO : respListAgendaVO.getListAgendaHeaderVO()) {
			AgendaHeader item = new AgendaHeader();
			item.setTitle(headerVO.getTanggalAgendaVal());
				for (AgendaDetailVO agendaDetailVO : headerVO.getAgendaDetail()) {
					AgendaItems child = new AgendaItems();					
					child.setTitle(agendaDetailVO.getSubject()+" : "+agendaDetailVO.getIsiAgenda());
					item.getItemsDetail().add(child);					
				}
			items.add(item);
		}		
		return items;
	}
	
//	private List<AgendaHeader> fillData(List<AgendaHeader> items) {
//		AgendaHeader item = new AgendaHeader();
//		item.setTitle("30 September 2015");
//		AgendaItems child;
//		child = new AgendaItems();
//		child.setTitle("- Science: kumpulkan PR dari halaman 30-35");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("- Science: Hari ini terakhir mengumpulkan Project Alam Semesta");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("- Bahasa Inggris: Ulangan tentang Past Tense");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("- Bahasa Indonesia: Ulangan ");
//		item.getItemsDetail().add(child);
//
//		items.add(item);
//
//		item = new AgendaHeader();
//		item.setTitle("27 September 2015");
//		
//		child = new AgendaItems();
//		child.setTitle("- Agama: Ulangan ");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("- Olahraga: Ulangan ");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("- Bahasa Indonesia: Ulangan ");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("- Bahasa Indonesia: Ulangan ");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("- Bahasa Indonesia: Ulangan ");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("- Bahasa Indonesia: Ulangan ");
//		item.getItemsDetail().add(child);
//
//		items.add(item);
//		
//		item = new AgendaHeader();
//		item.setTitle("25 September 2015");
//		
//		child = new AgendaItems();
//		child.setTitle("- Bahasa Indonesia: Ulangan ssssssssssssssssssssssssssssssssss"
//				+ "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("John Doe");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("John Doe");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("John Doe");
//		item.getItemsDetail().add(child);
//
//		child = new AgendaItems();
//		child.setTitle("John Doe");
//		item.getItemsDetail().add(child);
//
//		items.add(item);
//
//		return items;
//	}
	
	public RespListAgendaVO constructDataAgendaFromEngine(String listJson) throws JsonParseException, JsonMappingException, IOException
    {
		
		RespListAgendaVO respListAgendaVO = HttpClientUtil.getObjectMapper(ctx).readValue(listJson, RespListAgendaVO.class);
//		List<AgendaViewVO> it = new ArrayList<AgendaViewVO>();
//		if(respListAgendaVO.getListAgendaVo()!=null){
//			for (AgendaVO temp : respListAgendaVO.getListAgendaVo()) {
//				AgendaViewVO item = new AgendaViewVO();
//				item.setAgendaType(temp.getAgendaType());
//				item.setIsiAgenda(temp.getIsiAgenda());
//				item.setTglAgenda(temp.getTanggalAgendaVal());
//				item.setSubject(temp.getSubject());
////				tvTitle.setText(Constants.AGENDA+temp.getNamaKelas());
//		        it.add(item);
//			}
//		}
//		if(respListAgendaVO.getJumlahMessageUnread()>0){
////			btnInbox.setText(respListAgendaVO.getJumlahMessageUnread()+ " " + ctx.getResources().getString(R.string.msg_unread));
//		}else{
////			btnInbox.setText(ctx.getResources().getString(R.string.inbox));
//		}
				
		 
        return respListAgendaVO;
    }
	
	public class ReqListAgendaTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
       		progressDialog = new ProgressDialogParking(getActivity(), ctx.getResources().getString(R.string.process_agenda),
					ctx.getResources().getString(R.string.progress_dialog),ctx.getResources().getColor(R.color.main_color_500));
    			progressDialog.show();
    		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
           		ReqListAgendaData reqListAgendaData = new ReqListAgendaData();
    			reqListAgendaData.setPassword(loginData.getPassword());
    			reqListAgendaData.setKodeSekolah(loginData.getKodeSekolah());
    			reqListAgendaData.setNoInduk(loginData.getNoInduk());
    			reqListAgendaData.setOriginRequest(Constants.ORIGIN_SOURCE);
    			reqListAgendaData.setUserType(loginData.getUserType());
    			if(typeAgenda){
    				reqListAgendaData.setAgendaType(Constants.OTHER_AGENDA);
    			}else{
    				reqListAgendaData.setAgendaType(Constants.GENERAL_AGENDA);
    			}    				    			
           		String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(reqListAgendaData);
           		s = URLEncoder.encode(s, "UTF-8");
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_AGENDA);
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
			 reqListAgendaTask = null;          
             if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = URLDecoder.decode(respString, "UTF-8");	               	
	               			MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);
		               		if(messageVO.getRc()==0){
		               				RespListAgendaVO respListAgendaVO = constructDataAgendaFromEngine(messageVO.getOtherMessage());			               	        
			               	        // new interface fill data
			               	        List<AgendaHeader> items = new ArrayList<AgendaHeader>();
			               	        if(respListAgendaVO.getListAgendaHeaderVO()!=null){
			               	        	items = fillDataInterface(items,respListAgendaVO);
			               	        }			               	        
			               	        adapter = new AgendaViewExpandableAdapter(ctx,items);
			               	        adapter.setData(items);			               	        
			               	        listView.setAdapter(adapter);
			               	        adapter.notifyDataSetChanged();			               					               			
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
}
