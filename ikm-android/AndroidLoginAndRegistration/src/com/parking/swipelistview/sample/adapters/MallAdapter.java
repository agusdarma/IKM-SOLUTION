/*
 * Copyright (C) 2013 47 Degrees, LLC
 *  http://47deg.com
 *  hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.parking.swipelistview.sample.adapters;

import java.io.IOException;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.parking.R;
import com.parking.activity.InputCreditCardActivity;
import com.parking.data.Constants;
import com.parking.data.LoginData;
import com.parking.data.MessageVO;
import com.parking.data.SlotsParkingVO;
import com.parking.utils.CipherUtil;
import com.parking.utils.HttpClientUtil;
import com.parking.utils.MessageUtils;
import com.parking.utils.RedirectUtils;
import com.parking.utils.SharedPreferencesUtils;

public class MallAdapter extends BaseAdapter {
	private static final String TAG = MallAdapter.class.getSimpleName();
    private List<MallItem> data;
    private Context ctx;
    private Activity act;
    private LoginData login;
    private String mallName;
    private ReqSlotByMallTask reqSlotByMallTask = null;
    private ReqReleaseSlotByMallTask reqReleaseSlotByMallTask = null;

    public MallAdapter(Context context,Activity activity, List<MallItem> data) {
        this.ctx = context;
        this.data = data;
        this.act = activity;
    }
    
    public MallAdapter(Context context,Activity activity, List<MallItem> data,LoginData loginData) {
        this.ctx = context;
        this.data = data;
        this.act = activity;
        this.login = loginData;
    }
    
    public MallAdapter(Context context,List<MallItem> data) {
        this.ctx = context;
        this.data = data;        
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MallItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public boolean isEnabled(int position) {
//        if (position == 2) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MallItem item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.mall_row, parent, false);
            holder = new ViewHolder();
            holder.ivImage = (ImageView) convertView.findViewById(R.id.example_row_iv_image);
            holder.tvMallName = (TextView) convertView.findViewById(R.id.example_row_tv_title);
            holder.tvInformation = (TextView) convertView.findViewById(R.id.example_row_tv_description);
            holder.slotAvailable = (TextView) convertView.findViewById(R.id.slot_available);
            holder.bAction1 = (ButtonRectangle) convertView.findViewById(R.id.example_row_b_action_1);
            holder.bAction2 = (ButtonRectangle) convertView.findViewById(R.id.example_row_b_action_2);
            
            if(login.getGroupUser().equalsIgnoreCase(Constants.USER)){
            	holder.bAction2.setVisibility(View.GONE);
            }else if(login.getGroupUser().equalsIgnoreCase(Constants.STAFF)){
            	holder.bAction2.setVisibility(View.VISIBLE);
            	holder.bAction1.setVisibility(View.GONE);
            }else if(login.getGroupUser().equalsIgnoreCase(Constants.ADMIN)){
            	holder.bAction2.setVisibility(View.VISIBLE);
            	holder.bAction1.setVisibility(View.VISIBLE);
            }
            
//            holder.bAction3 = (ButtonRectangle) convertView.findViewById(R.id.example_row_b_action_3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView)parent).recycle(convertView, position);

        holder.ivImage.setImageDrawable(item.getIcon());
        holder.tvMallName.setText(item.getName());
        holder.tvInformation.setText(item.getInformation());
        holder.slotAvailable.setText(item.getSlotAvailable());


        holder.bAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  
            	mallName = item.getName();
            	MessageUtils messageUtils = new MessageUtils(ctx);
       			messageUtils.showDialogConfirmationCallback(ctx.getResources().getString(R.string.confirmation_dialog), ctx.getResources().getString(R.string.message_confirm_booking), buttonCallbackBooking);		             	       		            	            	
            }
        });

        holder.bAction2.setOnClickListener(new View.OnClickListener() {
        	 @Override
             public void onClick(View v) {
        		 mallName = item.getName();
        		 MessageUtils messageUtils = new MessageUtils(ctx);
        		 messageUtils.showDialogConfirmationCallback(ctx.getResources().getString(R.string.confirmation_dialog), ctx.getResources().getString(R.string.message_confirm_release), buttonCallbackRelease);		             	       		            	            	        
             }
        });

//        holder.bAction3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri packageUri = Uri.parse("package:" + item.getPackageName());
//                Intent uninstallIntent;
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                    uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
//                } else {
//                    uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
//                }
//                context.startActivity(uninstallIntent);
//            }
//        });


        return convertView;
    }

    static class ViewHolder {
        ImageView ivImage;
        TextView tvMallName;
        TextView tvInformation;
        TextView slotAvailable;
        ButtonRectangle bAction1;
        ButtonRectangle bAction2;
//        ButtonRectangle bAction3;
    }
    
    ButtonCallback buttonCallbackBooking = new ButtonCallback() {

		public void onPositive(MaterialDialog dialog) {
			reqSlotByMallTask = new ReqSlotByMallTask();
        	reqSlotByMallTask.execute(mallName);
        }
    };
    
    ButtonCallback buttonCallbackRelease = new ButtonCallback() {

		public void onPositive(MaterialDialog dialog) {
			reqReleaseSlotByMallTask = new ReqReleaseSlotByMallTask();
   		 	reqReleaseSlotByMallTask.execute(mallName);
        }
    };
    
    private void goToPayScreen(String mallName, long hargaParkir,String slotName,String bookingId) {
    	Intent i = new Intent(ctx, InputCreditCardActivity.class);            	
    	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
    	i.putExtra("mallName", mallName);
    	i.putExtra("hargaParkir", hargaParkir);
    	i.putExtra("slotName", slotName);
    	i.putExtra("bookingId", bookingId);
    	ctx.startActivity(i);
    }
    
    public class ReqSlotByMallTask extends AsyncTask<String, Void, Boolean> {
    	private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {       		
       		progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_find_slots),ctx.getResources().getString(R.string.progress_dialog));
			progressDialog.show();
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx); 
           		SlotsParkingVO slotsParkingVO = new SlotsParkingVO();        
           		slotsParkingVO.setEmail(loginData.getEmail());  
           		slotsParkingVO.setMallName(params[0]);
           		slotsParkingVO.setSessionKey(loginData.getSessionKey());
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(slotsParkingVO);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_FIND_SLOT_BY_MALL);
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
             	 messageUtils.snackBarMessage(act,respString);
   	     }

           @Override
           protected void onPostExecute(final Boolean success) {
        	   reqSlotByMallTask = null;          
               if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			final MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);		               	
		               		if(messageVO.getRc()==0){
				             	SlotsParkingVO slotsParkingVO = HttpClientUtil.getObjectMapper(ctx).readValue(messageVO.getOtherMessage(), SlotsParkingVO.class);
				             	goToPayScreen(slotsParkingVO.getMallName(), slotsParkingVO.getSlotsPrice(),slotsParkingVO.getSlotsName(),slotsParkingVO.getBookingId());
		               		}else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(act,messageVO.getMessageRc());		
				             	new Timer().schedule(new TimerTask() {          
				             	    @Override
				             	    public void run() {
				             	    	if(messageVO.getRc()==Constants.SESSION_EXPIRED||messageVO.getRc()==Constants.SESSION_DIFFERENT||messageVO.getRc()==Constants.USER_NOT_LOGIN){
						             		RedirectUtils redirectUtils = new RedirectUtils(ctx, act);
						             		redirectUtils.redirectToLogin();
				             	    	}  
				             	    }
				             	}, Constants.REDIRECT_DELAY_LOGIN);
		               		}
						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(act,ctx.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(act,ctx.getResources().getString(R.string.message_unexpected_error_server));
	               	}
               }else{
            	   MessageUtils messageUtils = new MessageUtils(ctx);
            	   messageUtils.snackBarMessage(act,ctx.getResources().getString(R.string.message_unexpected_error_server));
               } 
               if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
           }

       }
    
    
    public class ReqReleaseSlotByMallTask extends AsyncTask<String, Void, Boolean> {
    	private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {       		
       		progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_find_slots),ctx.getResources().getString(R.string.progress_dialog));
			progressDialog.show();
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx); 
           		SlotsParkingVO slotsParkingVO = new SlotsParkingVO();        
           		slotsParkingVO.setEmail(loginData.getEmail());  
           		slotsParkingVO.setMallName(params[0]);
           		slotsParkingVO.setSessionKey(loginData.getSessionKey());
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(slotsParkingVO);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_RELEASE_SLOT_PARKING);
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
             	 messageUtils.snackBarMessage(act,respString);
   	     }

           @Override
           protected void onPostExecute(final Boolean success) {
        	   reqSlotByMallTask = null;          
               if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			final MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);		               	
		               		if(messageVO.getRc()==0){
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(act,messageVO.getOtherMessage());
		               		}else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(act,messageVO.getMessageRc());		
				             	new Timer().schedule(new TimerTask() {          
				             	    @Override
				             	    public void run() {
				             	    	if(messageVO.getRc()==Constants.SESSION_EXPIRED||messageVO.getRc()==Constants.SESSION_DIFFERENT||messageVO.getRc()==Constants.USER_NOT_LOGIN){
						             		RedirectUtils redirectUtils = new RedirectUtils(ctx, act);
						             		redirectUtils.redirectToLogin();
				             	    	}  
				             	    }
				             	}, Constants.REDIRECT_DELAY_LOGIN);
		               		}
						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(act,ctx.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(act,ctx.getResources().getString(R.string.message_unexpected_error_server));
	               	}
               }else{
            	   MessageUtils messageUtils = new MessageUtils(ctx);
            	   messageUtils.snackBarMessage(act,ctx.getResources().getString(R.string.message_unexpected_error_server));
               }    
               if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
           }

       }

}
