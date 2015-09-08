package com.ikm.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.gc.materialdesign.views.Switch;
import com.gc.materialdesign.views.Switch.OnCheckListener;
import com.ikm.R;
import com.ikm.swipelistview.sample.adapters.AgendaAdapter;
import com.ikm.swipelistview.sample.adapters.AgendaVO;
import com.ikm.swipelistview.sample.utils.SettingsManager;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import fr.ganfra.materialspinner.MaterialSpinner;


public class MenuParentActivity extends Activity {
	private static final int REQUEST_CODE_SETTINGS = 0;
	private static final String TAG = MenuParentActivity.class.getSimpleName();
	private Context ctx;
//	private ReqLoginTask reqLoginTask = null;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Shimmer shimmer;
	Switch switchView;
	SwipeListView listAgenda;
    private AgendaAdapter adapter;
    private List<AgendaVO> data;
	MaterialSpinner spinner1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent);
		ShimmerTextView tvTitle = (ShimmerTextView) findViewById(R.id.tvTitle);
		ShimmerTextView tvTitleSchool = (ShimmerTextView) findViewById(R.id.tvTitleSchool);
		ShimmerTextView tvVersion = (ShimmerTextView) findViewById(R.id.tvVersion);
		ShimmerTextView tvFooter = (ShimmerTextView) findViewById(R.id.tvFooter);
		ctx = MenuParentActivity.this;
		listAgenda = (SwipeListView) findViewById(R.id.listAgenda);
		switchView = (Switch) findViewById(R.id.switchView);
		final TextView lblSwitchView = (TextView) findViewById(R.id.LblswitchView);
		data = new ArrayList<AgendaVO>();
		if (shimmer != null && shimmer.isAnimating()) {
			shimmer.cancel();
        } else {
        	shimmer = new Shimmer();
        	shimmer.start(tvTitle);
        	shimmer.start(tvTitleSchool);
        	shimmer.start(tvVersion);
        	shimmer.start(tvFooter);
        }
        adapter = new AgendaAdapter(ctx,MenuParentActivity.this, data);
        
        switchView.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(Switch view, boolean check) {
				if(switchView.isCheck()){					
					lblSwitchView.setText("List Pengumuman");
					data.clear();
			        data.addAll(getDataPengumuman());
			        adapter.notifyDataSetChanged();	
				}else{
					lblSwitchView.setText("List Agenda");
					data.clear();
			        data.addAll(getData());
			        adapter.notifyDataSetChanged();	
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
                Log.d("swipe get aaa ", tgl.getText().toString());
				
			}
		});

        listAgenda.setAdapter(adapter);
                               
        reload();
		
        data.clear();
        data.addAll(getData());
        adapter.notifyDataSetChanged();	

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
	
	
	public List<AgendaVO> getData()
    {
		List<AgendaVO> it = new ArrayList<AgendaVO>();
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
        AgendaVO items5 = new AgendaVO();
        items5.setAgendaType(1);
        items5.setIsiAgenda("Sejarah PR halaman 20");
        items5.setTglAgenda("03 September 2015");
        it.add(items5);
        return it;
    }
	
	public List<AgendaVO> getDataPengumuman()
    {
		List<AgendaVO> it = new ArrayList<AgendaVO>();
        AgendaVO items1 = new AgendaVO();
        items1.setAgendaType(1);
        items1.setIsiAgenda("Bahasa Indonesia PR halaman 17");
        items1.setTglAgenda("Pengumuman 07 September 2015");
        it.add(items1);
        AgendaVO items2 = new AgendaVO();
        items2.setAgendaType(1);
        items2.setIsiAgenda("Science PR halaman 18");
        items2.setTglAgenda("Pengumuman 06 September 2015");
        it.add(items2);
        AgendaVO items3 = new AgendaVO();
        items3.setAgendaType(1);
        items3.setIsiAgenda("Inggris PR halaman 19");
        items3.setTglAgenda("Pengumuman 05 September 2015");
        it.add(items3);
        AgendaVO items4 = new AgendaVO();
        items4.setAgendaType(1);
        items4.setIsiAgenda("Sejarah PR halaman 20");
        items4.setTglAgenda("Pengumuman 04 September 2015");
        it.add(items4);
        AgendaVO items5 = new AgendaVO();
        items5.setAgendaType(1);
        items5.setIsiAgenda("Sejarah PR halaman 20");
        items5.setTglAgenda("Pengumuman 03 September 2015");
        it.add(items5);
        AgendaVO items6 = new AgendaVO();
        items6.setAgendaType(1);
        items6.setIsiAgenda("Sejarah PR halaman 20");
        items6.setTglAgenda("Pengumuman 02 September 2015");
        it.add(items6);
        AgendaVO items7 = new AgendaVO();
        items7.setAgendaType(1);
        items7.setIsiAgenda("Sejarah PR halaman 20");
        items7.setTglAgenda("Pengumuman 01 September 2015");
        it.add(items7);
        AgendaVO items8 = new AgendaVO();
        items8.setAgendaType(1);
        items8.setIsiAgenda("Sejarah PR halaman 20");
        items8.setTglAgenda("Pengumuman 31 Agustus 2015");
        it.add(items8);
        AgendaVO items9 = new AgendaVO();
        items9.setAgendaType(1);
        items9.setIsiAgenda("Sejarah PR halaman 20");
        items9.setTglAgenda("Pengumuman 03 September 2015");
        it.add(items9);
        AgendaVO items10 = new AgendaVO();
        items10.setAgendaType(1);
        items10.setIsiAgenda("Sejarah PR halaman 20");
        items10.setTglAgenda("Pengumuman 03 September 2015");
        it.add(items10);
        AgendaVO items11 = new AgendaVO();
        items11.setAgendaType(1);
        items11.setIsiAgenda("Sejarah PR halaman 20");
        items11.setTglAgenda("Pengumuman 03 September 2015");
        it.add(items11);
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
