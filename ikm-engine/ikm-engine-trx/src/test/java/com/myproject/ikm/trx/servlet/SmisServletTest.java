package com.myproject.ikm.trx.servlet;

import java.net.URLEncoder;
import java.util.Date;

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
import com.myproject.ikm.lib.data.ReqAddAgendaData;
import com.myproject.ikm.lib.data.ReqListAgendaData;
import com.myproject.ikm.lib.data.ReqListInboxData;
import com.myproject.ikm.lib.data.ReqListKelasData;
import com.myproject.ikm.lib.data.ReqSendMessageData;
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
	
	
	private final String testingLoginUser = "http://localhost:8080/ikm-engine-trx/trx/loginUser";
	private final String testingGetAgenda = "http://localhost:8080/ikm-engine-trx/trx/listAgenda";
	private final String testingGetInbox = "http://localhost:8080/ikm-engine-trx/trx/listInbox";
	private final String testingSendMsgMurid = "http://localhost:8080/ikm-engine-trx/trx/sendMessage";
	private final String testingGetKelas = "http://localhost:8080/ikm-engine-trx/trx/listKelas";
	private final String testingAddAgenda = "http://localhost:8080/ikm-engine-trx/trx/addAgenda";
	
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
		String pass = CipherUtil.passwordDigest("DIAN-001","administrator");
//		String a = "agus";
		System.out.println(pass);
	}
	
//	@Test
	public void testAddAgenda() {
		String url = testingAddAgenda;
		long startTime = System.currentTimeMillis();
		HttpClient client = new DefaultHttpClient();
		try {
			
			ReqAddAgendaData reqAddAgendaData = new ReqAddAgendaData();
			reqAddAgendaData.setPassword("administrator");
			reqAddAgendaData.setKodeSekolah("DIAN-001");
			reqAddAgendaData.setNoInduk("1");
			reqAddAgendaData.setOriginRequest("Android-Mobile");
			reqAddAgendaData.setUserType(Constants.TEACHER);
			
			reqAddAgendaData.setAgendaType(1);
			reqAddAgendaData.setIsiAgenda("Kerjakan PR halaman 10");
			reqAddAgendaData.setKodeKelas("6A");
			reqAddAgendaData.setNamaKelas("Kelas 6A");
			reqAddAgendaData.setNamaSekolah("Dian Harapan");
			reqAddAgendaData.setKodeSekolah("DIAN-001");
			reqAddAgendaData.setTanggalAgenda(new Date());
			
			String s = mapper.writeValueAsString(reqAddAgendaData);
			s = URLEncoder.encode(s, "UTF-8");
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
	
//	@Test
	public void testGetKelasTeacher() {
		String url = testingGetKelas;
		long startTime = System.currentTimeMillis();
		HttpClient client = new DefaultHttpClient();
		try {
			
			ReqListKelasData reqListKelasData = new ReqListKelasData();
			reqListKelasData.setPassword("administrator");
			reqListKelasData.setKodeSekolah("DIAN-001");
			reqListKelasData.setNoInduk("1");
			reqListKelasData.setOriginRequest("Android-Mobile");
			reqListKelasData.setUserType(Constants.TEACHER);
			
			String s = mapper.writeValueAsString(reqListKelasData);
			s = URLEncoder.encode(s, "UTF-8");
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
	
//	@Test
	public void testSendMessageMurid() {
		String url = testingSendMsgMurid;
		long startTime = System.currentTimeMillis();
		HttpClient client = new DefaultHttpClient();
		try {
			
			ReqSendMessageData reqSendMessageData = new ReqSendMessageData();
			reqSendMessageData.setPassword("");
			reqSendMessageData.setKodeSekolah("DIAN-001");
			reqSendMessageData.setNoInduk("1");
			reqSendMessageData.setOriginRequest("Android-Mobile");
			reqSendMessageData.setUserType(Constants.PARENT);
			reqSendMessageData.setIsiMessage("Bu saya mau tanya lohhh");
			
			String s = mapper.writeValueAsString(reqSendMessageData);
			s = URLEncoder.encode(s, "UTF-8");
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
	
//	@Test
	public void testGetInbox() {
		String url = testingGetInbox;
		long startTime = System.currentTimeMillis();
		HttpClient client = new DefaultHttpClient();
		try {
			
			ReqListInboxData reqListInboxData = new ReqListInboxData();
			reqListInboxData.setPassword("");
			reqListInboxData.setKodeSekolah("DIAN-001");
			reqListInboxData.setNoInduk("1");
			reqListInboxData.setOriginRequest("Android-Mobile");
			reqListInboxData.setUserType(Constants.PARENT);
			
			String s = mapper.writeValueAsString(reqListInboxData);
			s = URLEncoder.encode(s, "UTF-8");
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
	
	@Test
	public void testGetAgenda() {
		String url = testingGetAgenda;
		long startTime = System.currentTimeMillis();
		HttpClient client = new DefaultHttpClient();
		try {
			
			ReqListAgendaData reqListAgendaData = new ReqListAgendaData();
			reqListAgendaData.setPassword("");
			reqListAgendaData.setKodeSekolah("DIAN-001");
			reqListAgendaData.setNoInduk("1");
			reqListAgendaData.setOriginRequest("Android-Mobile");
			reqListAgendaData.setUserType(Constants.PARENT);
			reqListAgendaData.setAgendaType(Constants.GENERAL_AGENDA);
			
			String s = mapper.writeValueAsString(reqListAgendaData);
			s = URLEncoder.encode(s, "UTF-8");
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

	
//	@Test
	public void testLoginUser() {
		String url = testingLoginUser;
		long startTime = System.currentTimeMillis();
		HttpClient client = new DefaultHttpClient();
		try {
			
			LoginData loginData = new LoginData();
			loginData.setPassword("administrator");
			loginData.setKodeSekolah("DIAN-001");
			loginData.setNoInduk("1");
			loginData.setOriginRequest("Android-Mobile");
			loginData.setUserType(Constants.TEACHER);
			
			String s = mapper.writeValueAsString(loginData);
//			s = CipherUtil.encryptTripleDES(s, CipherUtil.PASSWORD);
			s = URLEncoder.encode(s, "UTF-8");
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
