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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.Switch;
import com.gc.materialdesign.views.Switch.OnCheckListener;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.ikm.R;
import com.ikm.data.AgendaVO;
import com.ikm.data.Constants;
import com.ikm.data.LoginData;
import com.ikm.data.MessageVO;
import com.ikm.data.ReqListAgendaData;
import com.ikm.data.RespListAgendaVO;
import com.ikm.swipelistview.sample.adapters.AgendaViewAdapter;
import com.ikm.swipelistview.sample.adapters.AgendaViewVO;
import com.ikm.swipelistview.sample.utils.SettingsManager;
import com.ikm.utils.HttpClientUtil;
import com.ikm.utils.MessageUtils;
import com.ikm.utils.SharedPreferencesUtils;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import fr.ganfra.materialspinner.MaterialSpinner;


public class MenuParentActivity extends Activity {
	private static final int REQUEST_CODE_SETTINGS = 0;
	private static final String TAG = MenuParentActivity.class.getSimpleName();
	private Context ctx;
	private ReqListAgendaTask reqListAgendaTask = null;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Shimmer shimmer;
	Switch switchView;
	SwipeListView listAgenda;
    private AgendaViewAdapter adapter;
    private List<AgendaViewVO> data;
	MaterialSpinner spinner1;
	private Button btnInbox;
	ShimmerTextView tvTitle;
	boolean typeAgenda; // false agenda true pengumuman lain
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent);
		tvTitle = (ShimmerTextView) findViewById(R.id.tvTitle);
		ShimmerTextView tvTitleSchool = (ShimmerTextView) findViewById(R.id.tvTitleSchool);
		ButtonRectangle btnBack = (ButtonRectangle) findViewById(R.id.btnBack);
		btnInbox = (Button) findViewById(R.id.btnInbox);
		ctx = MenuParentActivity.this;
		listAgenda = (SwipeListView) findViewById(R.id.listAgenda);
		switchView = (Switch) findViewById(R.id.switchView);
		final TextView lblSwitchView = (TextView) findViewById(R.id.LblswitchView);
		data = new ArrayList<AgendaViewVO>();
		
		btnInbox.setText(ctx.getResources().getString(R.string.inbox)); 
		if (shimmer != null && shimmer.isAnimating()) {
			shimmer.cancel();
        } else {
        	shimmer = new Shimmer();
        	shimmer.start(tvTitle);
        	shimmer.start(tvTitleSchool);
        }
        adapter = new AgendaViewAdapter(ctx,MenuParentActivity.this, data);
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
				i.putExtra(Constants.FROM_MENU, Constants.PARENTS);
				i.putExtra(Constants.AGENDA_NAME, tvTitle.getText().toString());
				startActivity(i);	
			}
		});
        switchView.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(Switch view, boolean check) {
				if(switchView.isCheck()){					
			        typeAgenda = true;
			     // get data agenda
			        reqListAgendaTask = new ReqListAgendaTask();
			        reqListAgendaTask.execute("");	
				}else{
			        typeAgenda = false;
			     // get data agenda
			        reqListAgendaTask = new ReqListAgendaTask();
			        reqListAgendaTask.execute("");	
				}				
			}
		});
		
		listAgenda.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            listAgenda.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    mode.setTitle("Selected (" + listAgenda.getCountSelected() + ")");
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            listAgenda.dismissSelected();
                            mode.finish();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.menu_choice_items, menu);
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    listAgenda.unselectedChoiceStates();
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

        listAgenda.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
                final View v = listAgenda.getChildAt(position).findViewWithTag("front");                
//                View v = swipeListView.findViewWithTag("front");
                TextView tgl = (TextView) v.findViewById(R.id.txtTgl);
                Log.d("swipe get", tgl.getText().toString());
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    data.remove(position);
                }
                adapter.notifyDataSetChanged();
            }

        });
        
        listAgenda.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tgl = (TextView) view.findViewById(R.id.txtTgl);
				TextView isi = (TextView) view.findViewById(R.id.txtIsiAgenda);
                Log.d("swipe get aaa ", tgl.getText().toString());
                com.ikm.utils.MessageUtils messageUtils = new MessageUtils(ctx);
       			messageUtils.showDialogInfoCustomTheme(tgl.getText().toString(), isi.getText().toString());		             	       		            	            	            
				
			}
		});

        listAgenda.setAdapter(adapter);              
        reload();
        typeAgenda = false;
     // get data agenda
        reqListAgendaTask = new ReqListAgendaTask();
        reqListAgendaTask.execute("");

	}
	
	@Override
    public void onBackPressed() { 
		Intent i = new Intent(ctx, LoginActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  		       			
		startActivity(i);	
		finish();
	}
	
	 private void reload() {
   	SettingsManager settings = SettingsManager.getInstance();
       listAgenda.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
       listAgenda.setSwipeActionLeft(settings.getSwipeActionLeft());
       listAgenda.setSwipeActionRight(settings.getSwipeActionRight());
       listAgenda.setOffsetLeft(convertDpToPixel(settings.getSwipeOffsetLeft()));
       listAgenda.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
       listAgenda.setAnimationTime(750);
       listAgenda.setSwipeOpenOnLongPress(false);
	 }
	 public int convertDpToPixel(float dp) {
	        DisplayMetrics metrics = getResources().getDisplayMetrics();
	        float px = dp * (metrics.densityDpi / 160f);
	        return (int) px;
	    }        

	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        switch (requestCode) {
	            case REQUEST_CODE_SETTINGS:
	                reload();
	        }
	    }
	    
	    public interface OnDismissListener{
	        public void onDismiss(DialogInterface dialog);
	                // pass view as argument or whatever you want.
	    }
	
	
	public List<AgendaViewVO> constructDataAgenda(String listJson) throws JsonParseException, JsonMappingException, IOException
    {
		
		RespListAgendaVO respListAgendaVO = HttpClientUtil.getObjectMapper(ctx).readValue(listJson, RespListAgendaVO.class);
		List<AgendaViewVO> it = new ArrayList<AgendaViewVO>();
		if(respListAgendaVO.getListAgendaVo()!=null){
			for (AgendaVO temp : respListAgendaVO.getListAgendaVo()) {
				AgendaViewVO item = new AgendaViewVO();
				item.setAgendaType(temp.getAgendaType());
				item.setIsiAgenda(temp.getIsiAgenda());
				item.setTglAgenda(temp.getTanggalAgendaVal());
				tvTitle.setText(Constants.AGENDA+temp.getNamaKelas());
		        it.add(item);
			}
		}
		if(respListAgendaVO.getJumlahMessageUnread()>0){
			btnInbox.setText(respListAgendaVO.getJumlahMessageUnread()+ " " + ctx.getResources().getString(R.string.msg_unread));
		}else{
			btnInbox.setText(ctx.getResources().getString(R.string.inbox));
		}
				
		 
        return it;
    }
	
	
	
	
	
	public class ReqListAgendaTask  extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_agenda),ctx.getResources().getString(R.string.progress_dialog));
    			progressDialog.show();
    		}
		@Override
		protected Boolean doInBackground(String... arg0) {
			boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
           		ReqListAgendaData reqListAgendaData = new ReqListAgendaData();
    			reqListAgendaData.setPassword("");
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
          	 messageUtils.snackBarMessage(MenuParentActivity.this,respString);
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
		               			
		               				data.clear();
			               	        data.addAll(constructDataAgenda(messageVO.getOtherMessage()));
			               	        adapter.notifyDataSetChanged();	
		               			
		               			
		               		}
		               		else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(MenuParentActivity.this,messageVO.getMessageRc());
		               		}

						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(MenuParentActivity.this,MenuParentActivity.this.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(MenuParentActivity.this,MenuParentActivity.this.getResources().getString(R.string.message_unexpected_error_server));
	               	}
             }else{
          	   MessageUtils messageUtils = new MessageUtils(ctx);
          	   messageUtils.snackBarMessage(MenuParentActivity.this,MenuParentActivity.this.getResources().getString(R.string.message_unexpected_error_server));
             }        
             if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
         }
	}
	
	

}
