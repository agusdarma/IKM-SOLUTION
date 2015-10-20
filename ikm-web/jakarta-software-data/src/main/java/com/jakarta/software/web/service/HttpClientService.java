package com.jakarta.software.web.service;

import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientService {
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientService.class);
	
	private HttpClient httpClient;
	private String destinationUrl = "";
	private int timeout = 30000;  // wait for 30s

	public void start() {
		ClientConnectionManager cm = new PoolingClientConnectionManager();
		httpClient = new DefaultHttpClient(cm);
		
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		HttpConnectionParams.setSoTimeout(params, timeout);
		HttpConnectionParams.setSoKeepalive(params, false);
		
		LOG.info("HttpClientService is started with destUrl: {}, timeout: {}", 
				new String[] { destinationUrl, "" + timeout });
	}
	
	public void stop() {
		httpClient.getConnectionManager().shutdown();
		LOG.info("HttpClientService has been stopped");
	}
	
	public String connectToEngine(List<NameValuePair> params) throws MmbsWebException {
		HttpContext context = new BasicHttpContext();
        HttpGet httpGet = null;
		try {
			StringBuilder sb = new  StringBuilder();
			URIBuilder builder = new URIBuilder(destinationUrl);			
			for (NameValuePair param: params) {
				builder.setParameter(param.getName(), param.getValue());
				
				/* for log only */
				if(param.getName().equalsIgnoreCase("pin") || param.getName().equalsIgnoreCase("ConfirmPin") 
						|| param.getName().equalsIgnoreCase("OldPin") || param.getName().equalsIgnoreCase("NewPin")){
					sb.append(param.getName()).append("=").append(param.getValue().replaceAll("[0-9]{6}", "******")).append("&");
				}else{
					sb.append(param.getName()).append("=").append(param.getValue()).append("&");
				}
				/* end for log only */
				
			}
			httpGet = new HttpGet(builder.build());
//			LOG.debug("Execute: " + builder.toString());
			LOG.debug("Execute: " + sb.toString());
			
			HttpResponse response = httpClient.execute(httpGet, context);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				String respString = EntityUtils.toString(response.getEntity());
                LOG.debug("Response: {}", new String[] {
                		respString} );
                
                return respString;
			} else {
				LOG.warn("Invalid statusCode: {}", new String[] {
						"" + response.getStatusLine().getStatusCode()} );
                
				throw new MmbsWebException(MmbsWebException.NE_ERROR_RESPONSE_HOST);
			}  // end if statusCode != 200
		} catch (MmbsWebException me) {
			throw me;
		} catch (URISyntaxException us) {
			LOG.warn("URL [" + destinationUrl + "] is not valid");
			throw new MmbsWebException(MmbsWebException.NE_INVALID_URI);
		} catch (Exception e) {
			if (httpGet != null)
				httpGet.abort();
            LOG.warn("Unknown Error", e);
            throw new MmbsWebException(MmbsWebException.NE_UNKNOWN_ERROR);
        }   finally {
	    	if (httpGet != null)
	    		httpGet.releaseConnection();
	    } // end try catch
	}
	
	public String sendToEnginePostMethod(String s,String trxCode) throws MmbsWebException {
		HttpPost post = null;
		try {
			s = URLEncoder.encode(s, "UTF-8");
			LOG.debug("Request: " + s);
            StringEntity entity = new StringEntity(s);
			
			post = new HttpPost(destinationUrl+trxCode);
			LOG.debug("Server Url: " + destinationUrl+trxCode);
			post.setHeader("Content-Type", "application/json");
			post.setEntity(entity);
			
			HttpResponse response = httpClient.execute(post);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				String respString = EntityUtils.toString(response.getEntity());
				respString = URLDecoder.decode(respString, "UTF-8");
                LOG.debug("Response: {}", new String[] {respString} );                
                return respString;
			} else {
				LOG.warn("Invalid statusCode: {}", new String[] {"" + response.getStatusLine().getStatusCode()} );                
				throw new MmbsWebException(MmbsWebException.NE_ERROR_RESPONSE_HOST);
			}  // end if statusCode != 200
		} catch (MmbsWebException me) {
			throw me;
		} catch (Exception e) {
			if (post != null)
				post.abort();
            LOG.warn("Unknown Error", e);
            throw new MmbsWebException(MmbsWebException.NE_UNKNOWN_ERROR);
        }   finally {
	    	if (post != null)
	    		post.releaseConnection();
	    } // end try catch
	}
	
	public String sendToEnginePostMethod(List<NameValuePair> params) throws MmbsWebException {
		HttpContext context = new BasicHttpContext();
        HttpPost httpPost = null;
		try {
			StringBuilder sb = new  StringBuilder();
			URIBuilder builder = new URIBuilder(destinationUrl);			
			for (NameValuePair param: params) {
				builder.setParameter(param.getName(), param.getValue());
							
				
			}
			httpPost = new HttpPost(builder.build());
//			LOG.debug("Execute: " + builder.toString());
			LOG.debug("Execute: " + sb.toString());
			
			HttpResponse response = httpClient.execute(httpPost, context);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				String respString = EntityUtils.toString(response.getEntity());
                LOG.debug("Response: {}", new String[] {
                		respString} );
                
                return respString;
			} else {
				LOG.warn("Invalid statusCode: {}", new String[] {
						"" + response.getStatusLine().getStatusCode()} );
                
				throw new MmbsWebException(MmbsWebException.NE_ERROR_RESPONSE_HOST);
			}  // end if statusCode != 200
		} catch (MmbsWebException me) {
			throw me;
		} catch (URISyntaxException us) {
			LOG.warn("URL [" + destinationUrl + "] is not valid");
			throw new MmbsWebException(MmbsWebException.NE_INVALID_URI);
		} catch (Exception e) {
			if (httpPost != null)
				httpPost.abort();
            LOG.warn("Unknown Error", e);
            throw new MmbsWebException(MmbsWebException.NE_UNKNOWN_ERROR);
        }   finally {
	    	if (httpPost != null)
	    		httpPost.releaseConnection();
	    } // end try catch
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setDestinationUrl(String destinationUrl) {
		this.destinationUrl = destinationUrl;
	}

}
