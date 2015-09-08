package com.myproject.parking.trx.servlet;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myproject.parking.lib.utils.CommonUtil;

public class SmisHttpPostThread extends Thread {
	private static final Logger LOG = LoggerFactory.getLogger(SmisHttpPostThread.class);

	private final HttpClient httpClient;
    private final HttpContext context;
    private final HttpPost httpPost;
    private final int id;
    
    private ObjectMapper mapper;

    public SmisHttpPostThread(HttpClient httpClient, String urlPost, int id, 
    		String request, ObjectMapper mapper) 
    		throws UnsupportedEncodingException {
        this.httpClient = httpClient;
        this.context = new BasicHttpContext();
        this.httpPost = new HttpPost(urlPost);
        this.httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(request);
		this.httpPost.setEntity(entity);		
		this.mapper = mapper;
		
        this.id = id;
        
        LOG.debug("[{}] Get request: {}", new String[] { 
				"" + id, request });
		
    }
    
	@Override
	public void run() {
		try {
			long startTime = System.currentTimeMillis();
			// Execute HTTP request
			LOG.debug("[{}] Executing request: {}", new String[] { 
					"" + id, httpPost.getURI().toString() });
			
            // execute the method
            HttpResponse response = httpClient.execute(httpPost, context);

            StatusLine sl = response.getStatusLine();
            LOG.debug("[{}] StatusCode: {}", new String[] {
            		"" + id, "" + sl.getStatusCode() });
            Assert.assertEquals(200, sl.getStatusCode());
            
            // get the response body as an array of bytes
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String respString = EntityUtils.toString(entity);
                LOG.debug("[{}] Response: {}", new String[] {
                		"" + id, respString} );
                
//                UserData userData = mapper.
//                		readValue(respString, UserData.class);
                
                
                LOG.debug("[{}] Response Object: {}", new String[] {
                		"" + id, null} );
            }
            
            int delta = (int) (System.currentTimeMillis() - startTime);
            LOG.debug("[{}] Finish in {}ms", new String[] { 
            		"" + id, CommonUtil.displayNumberNoDecimal(delta) });
			
        } catch (Exception e) {
        	httpPost.abort();
            System.out.println(id + " - error: " + e);
        }
	}

}
