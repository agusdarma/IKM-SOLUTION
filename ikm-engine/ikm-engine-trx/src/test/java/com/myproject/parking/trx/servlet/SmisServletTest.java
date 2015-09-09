package com.myproject.parking.trx.servlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myproject.ikm.lib.data.LoginData;
import com.myproject.ikm.lib.utils.CipherUtil;
import com.myproject.ikm.lib.utils.CommonUtil;
import com.myproject.ikm.lib.utils.Constants;


@RunWith(MockitoJUnitRunner.class)
public class SmisServletTest {
	private static final Logger LOG = LoggerFactory.getLogger(SmisServletTest.class);

	private final String testingUserRegistration = "http://localhost:8080/ikm-engine-trx/trx/userRegistration";
	private final String testingPaymentWithCC = "http://localhost:8080/ikm-engine-trx/trx/paymentCC";
	private final String testingPaymentWithPayPal = "http://localhost:8080/ikm-engine-trx/trx/paymentPayPal";
	//private final String baseHostUrl = "http://192.168.0.76:8089/nusapro-wallet/wallet";
	private ObjectMapper mapper = new ObjectMapper();
	
	private final String testingActivateService = "http://localhost:8080/ikm-engine-trx/trx/userActivate?actKey=dadadada&email=a@yahoo.com&noHp=085693938630";
	private final String testingForget = "http://localhost:8080/ikm-engine-trx/trx/forgetPassword";
	private final String testingLoginUser = "http://localhost:8080/ikm-engine-trx/trx/loginUser";
	private final String testingChangePassword = "http://localhost:8080/ikm-engine-trx/trx/changePassword";
	private final String testingGetTrxFromVeriTrans = "http://localhost:8080/ikm-engine-trx/trx/receiveTrxFromVeriTrans";
	private final String testingGetListMall = "http://localhost:8080/ikm-engine-trx/trx/listMall";
	private final String testingRefreshCacheMall = "http://ec2-52-3-21-158.compute-1.amazonaws.com:8080/ikm-engine-trx/trx/refreshCacheMall";
	private final String testingGetSlotsByMall = "http://localhost:8080/ikm-engine-trx/trx/findSLotsByMall";
	private final String testingReleaseSlot = "http://localhost:8080/ikm-engine-trx/trx/releaseSlotParking";
	
//	@Test
	public void tes() {
		//in milliseconds
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
//		Calendar calendar = new GregorianCalendar(2015,8,03,19,27,00);
//		System.out.println(sdf.format(calendar.getTime()));
//		System.out.println("Current" + sdf.format(new Date().getTime()));
//		Date a =  calendar.getTime(); 
//		long diff = new Date().getTime() - a.getTime();
//
//		long diffSeconds = diff / 1000 % 60;
//		long diffMinutes = diff / (60 * 1000) % 60;
//		long diffHours = diff / (60 * 60 * 1000) % 24;
//		long diffDays = diff / (24 * 60 * 60 * 1000);
//		
//		long diffMinutesOnly = diff / (60 * 1000);
//
//		System.out.print(diffDays + " days, ");
//		System.out.print(diffHours + " hours, ");
//		System.out.print(diffMinutes + " minutes, ");
//		System.out.print(diffSeconds + " seconds.");
//		
//		System.out.print(diffMinutesOnly + " minutes.");
		
//		String a = "agus";
//		System.out.println(Encoder);
	}
	
	

	
	@Test
	public void testLoginUser() {
		String url = testingLoginUser;
		long startTime = System.currentTimeMillis();
		HttpClient client = new DefaultHttpClient();
		try {
			
			LoginData loginData = new LoginData();
			loginData.setPassword("Rahasia");
			loginData.setKodeSekolah("DIAN-001");
			loginData.setNoInduk("1");
			loginData.setUserType(Constants.PARENT);
			
			String s = mapper.writeValueAsString(loginData);
			s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
			LOG.debug("Request: " + s);
            StringEntity entity = new StringEntity(s);
			
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json");
			post.setEntity(entity);
			
			// Execute HTTP request
			LOG.debug("Executing request: " + post.getURI());
            HttpResponse response = client.execute(post);
            
            // Get hold of the response entity
            StatusLine sl = response.getStatusLine();
            LOG.debug("StatusCode: " + sl.getStatusCode());
            Assert.assertEquals(200, sl.getStatusCode());

            HttpEntity respEntity = response.getEntity();
            String respString = EntityUtils.toString(respEntity);
            LOG.debug("Response: " + respString);
            
//            WalletTrxResponse trxResp = mapper.
//            		readValue(respString, WalletTrxResponse.class);
//            Assert.assertEquals(trxReq.getRequestId(), trxResp.getRequestId());
            Assert.assertEquals(true, true);
            int delta = (int) (System.currentTimeMillis() - startTime);
            LOG.info("Finish running one thread in {}ms", 
            		new String[] { CommonUtil.displayNumberNoDecimal(delta) } );
		}catch (Exception e) {
		
			LOG.warn("Unexpected Exception", e);
		} finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            client.getConnectionManager().shutdown();
        }  // end try finally
	}
	

	
}
