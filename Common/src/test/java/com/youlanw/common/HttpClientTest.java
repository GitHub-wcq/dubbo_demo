package com.youlanw.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.youlanw.common.utils.http.HttpClientAsync;
import com.youlanw.common.utils.http.HttpClientSync;

public class HttpClientTest {

	// region 同步测试
	@Test
	public void SyncGetHttp() {
		HttpClientSync client = new HttpClientSync();
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("jobId", "497947"));
		parameters.add(new BasicNameValuePair("mark", "报名"));

		System.out.println(client.httpGet("http://m.youlanw.com/signup/applyOrderSuccess", null, parameters, false)); 
	}
	
	@Test
	public void SyncGetHttps() {
		HttpClientSync client = new HttpClientSync();

		System.out.println(client.httpGet("https://www.baidu.com", null, null, false)); 
	}
	
	@Test
	public void SyncPostHttp() {
		HttpClientSync client = new HttpClientSync();
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("positionJson", "daile"));
		//parameters.add(new BasicNameValuePair("password", "daile123"));
		//parameters.add(new BasicNameValuePair("captha", "love"));
		//String data = "{\"a\": \"1\"}";
		System.out.println("result ==>" + client.httpPost("http://cms.ysj.youlanw.com:18894/api/syn/synPosition", null, parameters, null, false)); 
	}
	
	// end
	
	// region 异步测试
	@Test
	public void AsyncGetHttp() {
		HttpClientAsync client = new HttpClientAsync();
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("jobId", "497947"));
		parameters.add(new BasicNameValuePair("mark", "报名"));
		client.httpAsyncGet(
				"http://m.youlanw.com/signup/applyOrderSuccess", 
				null, parameters, false, 
				new HttpClientAsync.AsyncResponseCompletedCallback() {
					
					@Override
					public void handle(org.apache.http.HttpResponse response) {
						// TODO Auto-generated method stub
						int statusCode = response.getStatusLine().getStatusCode();

						if (statusCode == 200) {
							HttpEntity he = response.getEntity();
							String returnValue = "";
							try {
								returnValue = new String(EntityUtils.toByteArray(he), "UTF-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								System.out.println(returnValue);
							}
						}
					}
				},
				new HttpClientAsync.AsyncResponseFailedCallback() {
					
					@Override
					public void handle(Exception e) {
						// TODO Auto-generated method stub
						
					}
				},
				null); 
	}


	@Test
	public void AsyncPostHttp() {
		HttpClientAsync client = new HttpClientAsync();
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("positionJson", "daile"));
		client.httpAsyncPost(
				"http://cms.ysj.youlanw.com:18894/api/syn/synPosition", 
				null, parameters, false, 
				new HttpClientAsync.AsyncResponseCompletedCallback() {
					
					@Override
					public void handle(org.apache.http.HttpResponse response) {
						// TODO Auto-generated method stub
						int statusCode = response.getStatusLine().getStatusCode();
						System.out.println("response statusCode ==> " + statusCode);
						
						if (statusCode == 200) {
							HttpEntity he = response.getEntity();
							String returnValue = "";
							try {
								returnValue = new String(EntityUtils.toByteArray(he), "UTF-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								System.out.println(returnValue);
							}
						}
					}
				},
				new HttpClientAsync.AsyncResponseFailedCallback() {
					
					@Override
					public void handle(Exception e) {
						// TODO Auto-generated method stub
						
					}
				},
				null); 	}
	// end

}
