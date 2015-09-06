package com.parking.menu;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.parking.R;
import com.parking.data.BookingVO;
import com.parking.data.Constants;
import com.parking.data.LoginData;
import com.parking.data.MessageVO;
import com.parking.utils.CipherUtil;
import com.parking.utils.CustomLabelAnimator;
import com.parking.utils.HttpClientUtil;
import com.parking.utils.MessageUtils;
import com.parking.utils.RedirectUtils;
import com.parking.utils.SharedPreferencesUtils;

/**
 * User: special
 * Date: 13-12-22
 * Time: ä¸‹å?ˆ3:28
 * Mail: specialcyci@gmail.com
 */
public class CheckInFragment extends Fragment {
	private static final String TAG = CheckInFragment.class.getSimpleName();
	private Context ctx;
	private CheckInAllowTask checkInAllowTask = null;
	private CheckConfirmTask checkConfirmTask = null;
	private FloatLabel bookingCode;
	private ButtonRectangle btnCheckIn;
	private ButtonRectangle btnCheckInOk;
	private ScrollView resultScrollView;
	private TextView bookingName;
	private TextView bookingPhone;
	private TextView bookingEmail;
	private TextView bookingMall;
	private TextView bookingId;
	private TextView bookingDate;
	private TextView bookingStatus;
	private String email;
	private String sessionkey;
	private String bookingCodeInput;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.activity_check_in, container, false);
    	ctx = container.getContext();
    	btnCheckIn = (ButtonRectangle) rootView.findViewById(R.id.btnCheckIn);
    	btnCheckInOk = (ButtonRectangle) rootView.findViewById(R.id.btnCheckInOk);
    	bookingCode = (FloatLabel) rootView.findViewById(R.id.bookingCode);
    	resultScrollView = (ScrollView) rootView.findViewById(R.id.resultScrollView);
    	bookingName = (TextView) rootView.findViewById(R.id.bookingName);
    	bookingPhone = (TextView) rootView.findViewById(R.id.bookingPhone);
    	bookingEmail = (TextView) rootView.findViewById(R.id.bookingEmail);
    	bookingMall = (TextView) rootView.findViewById(R.id.bookingMall);
    	bookingId = (TextView) rootView.findViewById(R.id.bookingId);
    	bookingDate = (TextView) rootView.findViewById(R.id.bookingDate);
    	bookingStatus = (TextView) rootView.findViewById(R.id.bookingStatus);
    	
    	// This is how you add a custom animator
    	bookingCode.setLabelAnimator(new CustomLabelAnimator());       
    	
    	btnCheckIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
           	 bookingCodeInput = bookingCode.getEditText().getText().toString();       
           	 LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);            	 
           	// ambil dari session untuk email, session key
           	 email = loginData.getEmail();
           	 sessionkey = loginData.getSessionKey();		 
				if (!bookingCodeInput.isEmpty()) {	
					MessageUtils messageUtils = new MessageUtils(ctx);
	        		messageUtils.showDialogConfirmationCallback(ctx.getResources().getString(R.string.confirmation_dialog), ctx.getResources().getString(R.string.message_confirm_check_kode_booking), buttonCallbackCheckIn);		             	       		            	            	        	             
					       
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_booking_code_required));
				}


            }
        });
    	
    	btnCheckInOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
           	 bookingCodeInput = bookingCode.getEditText().getText().toString();       
           	 LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);            	 
           	// ambil dari session untuk email, session key
           	 email = loginData.getEmail();
           	 sessionkey = loginData.getSessionKey();		 
				if (!bookingCodeInput.isEmpty()) {	
					MessageUtils messageUtils = new MessageUtils(ctx);
	        		messageUtils.showDialogConfirmationCallback(ctx.getResources().getString(R.string.confirmation_dialog), ctx.getResources().getString(R.string.message_confirm_check_in), buttonCallbackConfirm);		             	       		            	            	        	             					       									
				} else {
					MessageUtils messageUtils = new MessageUtils(ctx);
	             	messageUtils.snackBarMessage(getActivity(),ctx.getResources().getString(R.string.message_booking_code_required));
				}


            }
        });
        return rootView;
    }
    
    ButtonCallback buttonCallbackCheckIn = new ButtonCallback() {

		public void onPositive(MaterialDialog dialog) {
			checkInAllowTask = new CheckInAllowTask();
			checkInAllowTask.execute("");
        }
    };
    
    ButtonCallback buttonCallbackConfirm = new ButtonCallback() {

		public void onPositive(MaterialDialog dialog) {
			checkConfirmTask = new CheckConfirmTask();
			checkConfirmTask.execute("");
        }
    };
    
    public class CheckInAllowTask extends AsyncTask<String, Void, Boolean> {
    	private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {       		
       		progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_login),ctx.getResources().getString(R.string.progress_dialog));
			progressDialog.show();
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		
           		BookingVO bookingVO = new BookingVO();
           		bookingVO.setEmail(email);
           		bookingVO.setSessionKey(sessionkey);
           		bookingVO.setBookingCode(bookingCodeInput);
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(bookingVO);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_CHECK_BOOKING_CODE);
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
        	   checkInAllowTask = null;          
               if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			final MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);		               	
		               		if(messageVO.getRc()==0){
		               			BookingVO bookingVO = HttpClientUtil.getObjectMapper(ctx).readValue(messageVO.getOtherMessage(), BookingVO.class);	
		               			bookingName.setText(bookingVO.getName());
		               			bookingPhone.setText(bookingVO.getPhoneNo());
		               			bookingEmail.setText(bookingVO.getEmail());
		               			bookingMall.setText(bookingVO.getMallName());
		               			bookingId.setText(bookingVO.getBookingId());
		               			bookingDate.setText(bookingVO.getBookingDateValue());
		               			bookingStatus.setText(bookingVO.getBookingStatusValue());	
		               			resultScrollView.setVisibility(View.VISIBLE);
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
    
    public class CheckConfirmTask extends AsyncTask<String, Void, Boolean> {
    	private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {       		
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_confirm),ctx.getResources().getString(R.string.progress_dialog));
    			progressDialog.show();
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		
           		BookingVO bookingVO = new BookingVO();
           		bookingVO.setEmail(email);
           		bookingVO.setSessionKey(sessionkey);
           		bookingVO.setBookingCode(bookingCodeInput);
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(bookingVO);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_CONFIRM_BOOKING_CODE);
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
        	   checkConfirmTask = null;          
               if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			final MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);		               	
		               		if(messageVO.getRc()==0){
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(getActivity(),messageVO.getOtherMessage());	
				             	resultScrollView.setVisibility(View.GONE);
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
