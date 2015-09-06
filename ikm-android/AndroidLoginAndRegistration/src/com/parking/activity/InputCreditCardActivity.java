package com.parking.activity;


import id.co.veritrans.android.api.VTDirect;
import id.co.veritrans.android.api.VTInterface.ITokenCallback;
import id.co.veritrans.android.api.VTModel.VTCardDetails;
import id.co.veritrans.android.api.VTModel.VTToken;
import id.co.veritrans.android.api.VTUtil.VTConfig;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.ProgressDialogParking;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.parking.R;
import com.parking.data.Address;
import com.parking.data.BookingVO;
import com.parking.data.Constants;
import com.parking.data.CustomerDetail;
import com.parking.data.InqCreditCardRequest;
import com.parking.data.LoginData;
import com.parking.data.MessageVO;
import com.parking.data.Product;
import com.parking.data.TransactionDetails;
import com.parking.data.VeriTransVO;
import com.parking.utils.CipherUtil;
import com.parking.utils.CustomLabelAnimator;
import com.parking.utils.HttpClientUtil;
import com.parking.utils.MessageUtils;
import com.parking.utils.RedirectUtils;
import com.parking.utils.SharedPreferencesUtils;
import com.parking.view.CustomWebView;


public class InputCreditCardActivity extends Activity {
	private static final String TAG = InputCreditCardActivity.class.getSimpleName();
	private ButtonRectangle btnPay;
	private FloatLabel noCC;
	private FloatLabel cardExpireMonth;
	private FloatLabel cardExpireYear;
	private FloatLabel cardCvv;
	private TextView paymentFor;
	private TextView total;
	private TextView txtSlotName;
	
	private Context ctx;
	private String mallName;
	private String slotName;
	private String bookingId;
	AlertDialog dialog3ds;
    ProgressDialogParking sendServerProgress;
    long totalPrice;
    private CheckOrderAllowPayTask checkOrderAllowPayTask = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_card);
		ctx = InputCreditCardActivity.this;
		paymentFor = (TextView) findViewById(R.id.paymentFor);
		txtSlotName = (TextView) findViewById(R.id.txtSlotName);
		total = (TextView) findViewById(R.id.total);
		noCC = (FloatLabel) findViewById(R.id.noCC);
		cardExpireMonth = (FloatLabel) findViewById(R.id.card_expire_month);
		cardCvv = (FloatLabel) findViewById(R.id.card_cvv);
		cardExpireYear = (FloatLabel) findViewById(R.id.card_expire_year);
		btnPay = (ButtonRectangle) findViewById(R.id.btnPay);
		Intent intent = getIntent();
		mallName = intent.getStringExtra("mallName");
		slotName = intent.getStringExtra("slotName");
		totalPrice = intent.getLongExtra("hargaParkir", 10000);
		// Progress dialog
//		pDialog = new ProgressDialog(this);
//		pDialog.setCancelable(false);
		
		paymentFor.setText("V-Mobile "+ mallName);
		total.setText("GRAND TOTAL: "+ totalPrice);
		txtSlotName.setText("Area name : " + slotName);
		bookingId = intent.getStringExtra("bookingId");
		MessageUtils messageUtils = new MessageUtils(ctx);
        messageUtils.showDialogInfo(ctx.getResources().getString(R.string.info_expired_payment), ctx.getResources().getString(R.string.info_expired_payment_message));
		
		// This is how you add a custom animator
		noCC.setLabelAnimator(new CustomLabelAnimator());
		cardExpireMonth.setLabelAnimator(new CustomLabelAnimator());
		cardCvv.setLabelAnimator(new CustomLabelAnimator());
		cardExpireYear.setLabelAnimator(new CustomLabelAnimator());        
		
		btnPay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				MessageUtils messageUtils = new MessageUtils(ctx);
       			messageUtils.showDialogConfirmationCallback(ctx.getResources().getString(R.string.confirmation_dialog), ctx.getResources().getString(R.string.message_confirm_pay), buttonCallbackPay);		             	       		            	            	            				
			}
		});

	}
	
	ButtonCallback buttonCallbackPay = new ButtonCallback() {

		public void onPositive(MaterialDialog dialog) {
			checkOrderAllowPayTask = new CheckOrderAllowPayTask();
			checkOrderAllowPayTask.execute("");
        }
    };
	
	private void pay(){
		String noCCInput = noCC.getEditText().getText().toString();
		String cardExpireMonthInput = cardExpireMonth.getEditText().getText().toString();
		String cardExpireYearInput = cardExpireYear.getEditText().getText().toString();				
		String cardCvvInput = cardCvv.getEditText().getText().toString();
						

		if (!noCCInput.isEmpty() && !cardExpireMonthInput.isEmpty() && !cardExpireYearInput.isEmpty()&& !cardCvvInput.isEmpty()) {
			InqCreditCardRequest inqCreditCardRequest = new InqCreditCardRequest();
			inqCreditCardRequest.setNoCC(noCCInput);
			inqCreditCardRequest.setCardExpireMonth(Integer.parseInt(cardExpireMonthInput));
			inqCreditCardRequest.setCardExpireYear(Integer.parseInt(cardExpireYearInput));
			inqCreditCardRequest.setCardCvv(cardCvvInput);					
			inqCreditCardRequest.setGross_amount(Long.toString(totalPrice));					
			//set environment
            VTConfig.VT_IsProduction = false;
            //set client key
            VTConfig.CLIENT_KEY = HttpClientUtil.CLIENT_KEY; //agus	                

            VTDirect vtDirect = new VTDirect();
            //set using 3dsecure or not	             
            VTCardDetails cardDetails = null;
            
            cardDetails = CardFactory(true,inqCreditCardRequest);
           
            vtDirect.setCard_details(cardDetails);

            //set loading dialog
            final ProgressDialogParking loadingDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_authenticating_credit_card),ctx.getResources().getString(R.string.progress_dialog));
            loadingDialog.show();
            vtDirect.getToken(new ITokenCallback() {
                @Override
                public void onSuccess(VTToken token) {
                    loadingDialog.cancel();
                    if(token.getRedirect_url() != null){
                        //using 3d secure
                        //show it to user using webview
                        Log.d("VtLog",token.getToken_id());

                        CustomWebView webView = new CustomWebView(ctx);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                    case MotionEvent.ACTION_UP:
                                        if (!v.hasFocus()) {
                                            v.requestFocus();
                                        }
                                        break;
                                }
                                return false;
                            }
                        });
                        webView.setWebChromeClient(new WebChromeClient());
                        webView.setWebViewClient(new VtWebViewClient(token.getToken_id(),totalPrice+""));
                        webView.loadUrl(token.getRedirect_url());

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);
                        dialog3ds = alertBuilder.create();


                        dialog3ds.setTitle("3D Secure");
                        dialog3ds.setView(webView);
                        webView.requestFocus(View.FOCUS_DOWN);
                        alertBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        dialog3ds.show();

                    }
//                    //print token
//                    TextView tokenText = (TextView) getView().findViewById(R.id.txt_token);
//                    tokenText.setText(token.getToken_id());

                }

                @Override
                public void onError(Exception e) {
                    loadingDialog.cancel();                    
                    MessageUtils messageUtils = new MessageUtils(ctx);
                 	messageUtils.snackBarMessage(InputCreditCardActivity.this,e.getMessage());
                }
            });
			
		} else {
			MessageUtils messageUtils = new MessageUtils(ctx);
         	messageUtils.snackBarMessage(InputCreditCardActivity.this,ctx.getResources().getString(R.string.message_detail_required));
		}
	}
	
	private class SendTokenAsync extends AsyncTask<String, Void, Boolean>{		
		private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;       	   		
		
		@Override
		protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);            	 
           		// ambil dari session untuk email, session key	         	
           		VeriTransVO veriTransVO = new VeriTransVO();
           		veriTransVO.setBookingId(bookingId);
    			veriTransVO.setEmail(loginData.getEmail());
    			veriTransVO.setSessionKey(loginData.getSessionKey());
    			veriTransVO.setTotalPriceIdr(new Long(params[1]));
    			veriTransVO.setTokenId(URLEncoder.encode(params[0],"UTF-8"));
    			veriTransVO.setPaymentMethod("Credit Card");
    			CustomerDetail customerDetail = new CustomerDetail();
    			customerDetail.setFirstName(loginData.getName());
    			customerDetail.setLastName(loginData.getName());
    			customerDetail.setEmail(loginData.getEmail());
    			customerDetail.setPhone(loginData.getPhoneNo());
    			Address billAddress = new Address();
    			billAddress.setFirstName(loginData.getName());
    			billAddress.setLastName(loginData.getName());
    			billAddress.setAddress("");
    			billAddress.setCity("");
    			billAddress.setPhone(loginData.getPhoneNo());
    			billAddress.setPostalCode("15210");
    			
    			Address shipAddress = new Address();
    			shipAddress.setFirstName(loginData.getName());
    			shipAddress.setLastName(loginData.getName());
    			shipAddress.setAddress("");
    			shipAddress.setCity("");
    			shipAddress.setPhone(loginData.getPhoneNo());
    			shipAddress.setPostalCode("15210");
    			customerDetail.setBillingAddress(billAddress);
    			customerDetail.setShippingAddress(shipAddress);
    			
    			TransactionDetails transactionDetails = new  TransactionDetails();
    			transactionDetails.setOrderId(UUID.randomUUID().toString());
    			transactionDetails.setGrossAmount(new Long(totalPrice));
    			
    			Product product = new Product();
    			product.setId(new Long(1));
    			product.setLongName(slotName);
    			product.setPriceIdr(new Long(totalPrice));
    			product.setShortName("V-Mobile "+ mallName);
    			product.setThumbnailFilePath("");
    			List<Product> listProducts = new ArrayList<Product>();
    			listProducts.add(product);
    			veriTransVO.setCustomerDetail(customerDetail);
    			veriTransVO.setTransactionDetails(transactionDetails);
    			veriTransVO.setListProducts(listProducts);
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(veriTransVO);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
//           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_RECEIVE_TRX_VERITRANS);
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
			 if(sendServerProgress.isShowing()){
				sendServerProgress.dismiss();
			 }
			 MessageUtils messageUtils = new MessageUtils(ctx);
         	 messageUtils.snackBarMessage(InputCreditCardActivity.this,respString);
	     }
        
        @Override
        protected void onPostExecute(final Boolean success) {     	        
            if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			final MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);		               	
		               		if(messageVO.getRc()==0){				             	
				             	MessageUtils messageUtils = new MessageUtils(ctx);
				                messageUtils.showDialogInfoCallback(ctx.getResources().getString(R.string.info_payment), messageVO.getOtherMessage(),buttonCallback);
		               		}else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(InputCreditCardActivity.this,messageVO.getMessageRc());
				             	new Timer().schedule(new TimerTask() {          
				             	    @Override
				             	    public void run() {
				             	    	if(messageVO.getRc()==Constants.SESSION_EXPIRED||messageVO.getRc()==Constants.SESSION_DIFFERENT||messageVO.getRc()==Constants.USER_NOT_LOGIN){
						             		RedirectUtils redirectUtils = new RedirectUtils(ctx, InputCreditCardActivity.this);
						             		redirectUtils.redirectToLogin();
				             	    	}  
				             	    }
				             	}, Constants.REDIRECT_DELAY_LOGIN);
		               		}
						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(InputCreditCardActivity.this,ctx.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(InputCreditCardActivity.this,ctx.getResources().getString(R.string.message_unexpected_error_server));
	               	}
            }else{
         	   MessageUtils messageUtils = new MessageUtils(ctx);
         	   messageUtils.snackBarMessage(InputCreditCardActivity.this,ctx.getResources().getString(R.string.message_unexpected_error_server));
            }
            if(sendServerProgress.isShowing()){
            	sendServerProgress.dismiss();
			}
                     
            
        }
    }
	
	ButtonCallback buttonCallback = new ButtonCallback() {

		public void onPositive(MaterialDialog dialog) {
			finish();
        }
    };
	
	private class VtWebViewClient extends WebViewClient {

        String token;
        String price;

        public VtWebViewClient(String token, String price){
            this.token = token;
            this.price = price;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            Log.d("VtLog", url);

            if (url.startsWith(HttpClientUtil.getPaymentApiUrl() + "/callback/")) {
                //send token to server
                SendTokenAsync sendTokenAsync = new SendTokenAsync();
                sendTokenAsync.execute(token,price);
                //close web dialog
                dialog3ds.dismiss();
                //show loading dialog
                sendServerProgress = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_verification_to_server),ctx.getResources().getString(R.string.progress_dialog));
                sendServerProgress.show();
            } else if (url.startsWith(HttpClientUtil.getPaymentApiUrl() + "/redirect/") || url.contains("3dsecure")) {
                /* Do nothing */
            } else {
                if(dialog3ds != null){
                    dialog3ds.dismiss();
                }
            }
        }

    }
	
	private VTCardDetails CardFactory(boolean secure,InqCreditCardRequest inqCreditCardRequest){
        VTCardDetails cardDetails = new VTCardDetails();
//        cardDetails.setCard_number("4811111111111114");
//        cardDetails.setCard_cvv("123");
//        cardDetails.setCard_exp_month(12);
//        cardDetails.setCard_exp_year(2020);
//        cardDetails.setSecure(secure);
//        cardDetails.setGross_amount(Integer.toString(totalPrice));
        cardDetails.setCard_number(inqCreditCardRequest.getNoCC());
        cardDetails.setCard_cvv(inqCreditCardRequest.getCardCvv());
        cardDetails.setCard_exp_month(inqCreditCardRequest.getCardExpireMonth());
        cardDetails.setCard_exp_year(inqCreditCardRequest.getCardExpireYear());
        cardDetails.setSecure(secure);
        cardDetails.setGross_amount(inqCreditCardRequest.getGross_amount());
        return cardDetails;
    }
	
	public class CheckOrderAllowPayTask extends AsyncTask<String, Void, Boolean> {
		private ProgressDialogParking progressDialog = null;
       	private final HttpClient client = HttpClientUtil.getNewHttpClient();
       	String respString = null;
       	protected void onPreExecute() {
    			progressDialog = new ProgressDialogParking(ctx, ctx.getResources().getString(R.string.process_check_booking_id_allowed_pay),ctx.getResources().getString(R.string.progress_dialog));
    			progressDialog.show();
    		}
    		@Override
           protected Boolean doInBackground(String... params) {
           	boolean result = false;
           	try {
           		LoginData loginData = SharedPreferencesUtils.getLoginData(ctx);
           		BookingVO bookingVO = new BookingVO();
           		bookingVO.setEmail(loginData.getEmail());           		
           		bookingVO.setSessionKey(loginData.getSessionKey());
           		bookingVO.setBookingId(bookingId);
				String s = HttpClientUtil.getObjectMapper(ctx).writeValueAsString(bookingVO);
				s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
           		Log.d(TAG,"Request: " + s);
                StringEntity entity = new StringEntity(s);    			
    			HttpPost post = new HttpPost(HttpClientUtil.URL_BASE+HttpClientUtil.URL_CHECK_ORDER_ALLOW_PAY);
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
	             	 messageUtils.snackBarMessage(InputCreditCardActivity.this,respString);
	   	   }

           @Override
           protected void onPostExecute(final Boolean success) {
        	   checkOrderAllowPayTask = null;          
               if (success) {
	               	if(!respString.isEmpty()){
	               		try {
	               			String respons = CipherUtil.decryptTripleDES(respString, CipherUtil.PASSWORD);
	               			final MessageVO messageVO = HttpClientUtil.getObjectMapper(ctx).readValue(respons, MessageVO.class);		               	
		               		if(messageVO.getRc()==0){
		               			pay();
		               		}else{
		               			MessageUtils messageUtils = new MessageUtils(ctx);
				             	messageUtils.snackBarMessage(InputCreditCardActivity.this,messageVO.getMessageRc());
				             	new Timer().schedule(new TimerTask() {          
				             	    @Override
				             	    public void run() {
				             	    	if(messageVO.getRc()==Constants.SESSION_EXPIRED||messageVO.getRc()==Constants.SESSION_DIFFERENT||messageVO.getRc()==Constants.USER_NOT_LOGIN){
						             		RedirectUtils redirectUtils = new RedirectUtils(ctx, InputCreditCardActivity.this);
						             		redirectUtils.redirectToLogin();
				             	    	}  
				             	    }
				             	}, Constants.REDIRECT_DELAY_LOGIN);
		               		}
						} catch (Exception e) {
							MessageUtils messageUtils = new MessageUtils(ctx);
			             	messageUtils.snackBarMessage(InputCreditCardActivity.this,ctx.getResources().getString(R.string.message_unexpected_error_message_server));
						}	            
	               	}else{
	               	   MessageUtils messageUtils = new MessageUtils(ctx);
	             	   messageUtils.snackBarMessage(InputCreditCardActivity.this,ctx.getResources().getString(R.string.message_unexpected_error_server));
	               	}
               }else{
            	   MessageUtils messageUtils = new MessageUtils(ctx);
            	   messageUtils.snackBarMessage(InputCreditCardActivity.this,ctx.getResources().getString(R.string.message_unexpected_error_server));
               }
               if(progressDialog.isShowing()){
					progressDialog.dismiss();
				}
           }

       }
	

}
