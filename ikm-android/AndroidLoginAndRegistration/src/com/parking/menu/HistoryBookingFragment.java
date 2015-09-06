package com.parking.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.parking.R;
import com.parking.data.Constants;
import com.parking.data.InqGetMallRequest;
import com.parking.data.LoginData;
import com.parking.data.MessageVO;
import com.parking.swipelistview.sample.adapters.HistoryBookingAdapter;
import com.parking.swipelistview.sample.adapters.HistoryBookingVO;
import com.parking.swipelistview.sample.utils.SettingsManager;
import com.parking.utils.CipherUtil;
import com.parking.utils.HttpClientUtil;
import com.parking.utils.MessageUtils;
import com.parking.utils.RedirectUtils;
import com.parking.utils.SharedPreferencesUtils;

/**
 * User: special
 * Date: 13-12-22
 * Time: ä¸‹å?ˆ3:26
 * Mail: specialcyci@gmail.com
 */
public class HistoryBookingFragment extends Fragment {
	private static final String TAG = HistoryBookingFragment.class.getSimpleName();
	private static final int REQUEST_CODE_SETTINGS = 0;
    private HistoryBookingAdapter adapter;
    private List<HistoryBookingVO> data;
    private Context ctx;
    private SwipeListView swipeListView;
    private ReqGetHistoryBookingTask reqGetHistoryBookingTask = null;
    private String email;
	private String sessionkey;
    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.swipe_list_view_activity, container, false);
        ctx = container.getContext();
        LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);            	 
    	// ambil dari session untuk email, session key
        email = loginData.getEmail();
        sessionkey = loginData.getSessionKey();
        data = new ArrayList<HistoryBookingVO>();

        adapter = new HistoryBookingAdapter(ctx,getActivity(), data,loginData);

        swipeListView = (SwipeListView) parentView.findViewById(R.id.example_lv_list);

        swipeListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            swipeListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    mode.setTitle("Selected (" + swipeListView.getCountSelected() + ")");
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            swipeListView.dismissSelected();
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
                    swipeListView.unselectedChoiceStates();
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

        swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
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

        swipeListView.setAdapter(adapter);

        reload();
        
//        new ListAppTask().execute();
        reqGetHistoryBookingTask = new ReqGetHistoryBookingTask();
        reqGetHistoryBookingTask.execute("");
        
        return parentView;
    }
    
    private void reload() {
//        SettingsManager settings = SettingsManager.getInstance();
//        swipeListView.setSwipeMode(settings.getSwipeMode());
//        swipeListView.setSwipeActionLeft(settings.getSwipeActionLeft());
//        swipeListView.setSwipeActionRight(settings.getSwipeActionRight());
//        swipeListView.setOffsetLeft(convertDpToPixel(settings.getSwipeOffsetLeft()));
//        swipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
//        swipeListView.setAnimationTime(settings.getSwipeAnimationTime());
//        swipeListView.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
    	SettingsManager settings = SettingsManager.getInstance();
        swipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_RIGHT);
        swipeListView.setSwipeActionLeft(settings.getSwipeActionLeft());
        swipeListView.setSwipeActionRight(settings.getSwipeActionRight());
        swipeListView.setOffsetLeft(convertDpToPixel(settings.getSwipeOffsetLeft()));
        swipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
        swipeListView.setAnimationTime(750);
        swipeListView.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
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
    
    public class ReqGetHistoryBookingTask extends AsyncTask<String, Void, Boolean> {
    	private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {       		    			
        			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_get_history_booking),ctx.getResources().getString(R.string.progress_dialog));
        			progressDialog.show();
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		
           		InqGetMallRequest inqGetMallRequest = new InqGetMallRequest();
           		inqGetMallRequest.setEmail(email);           		
           		inqGetMallRequest.setSessionKey(sessionkey);
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(inqGetMallRequest);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_GET_HISTORY);
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
        	   reqGetHistoryBookingTask = null;          
               if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			final MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);		               	
		               		if(messageVO.getRc()==0){		               					               				
		               			List<HistoryBookingVO> listHistory = HttpClientUtil.getObjectMapper(ctx).readValue(messageVO.getOtherMessage(), new TypeReference<List<HistoryBookingVO>>(){});		               					               			
		               			data.clear();
		                        data.addAll(listHistory);
		                        adapter.notifyDataSetChanged();		                        	                        		                        		                        
		               		}else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(getActivity(),messageVO.getMessageRc());
				             	new Timer().schedule(new TimerTask() {          
				             	    @Override
				             	    public void run() {
				             	    	if(messageVO.getRc()==Constants.SESSION_EXPIRED||messageVO.getRc()==Constants.SESSION_DIFFERENT||messageVO.getRc()==Constants.USER_NOT_LOGIN){
						             		RedirectUtils redirectUtils = new RedirectUtils(ctx, getActivity());
						             		redirectUtils.redirectToLogin();
				             	    	}  
				             	    }
				             	}, Constants.REDIRECT_DELAY_LOGIN);
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
