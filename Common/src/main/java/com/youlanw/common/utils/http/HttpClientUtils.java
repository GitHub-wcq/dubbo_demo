package com.youlanw.common.utils.http;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * <p>
 * Title: HttpClientUtils
 * </p>
 * Description:
 * 
 * <pre>
 * HttpClient工具类
 * </pre>
 * 
 * @author wangchaoqun
 * @date 2018年4月13日
 */
@SuppressWarnings("unused")
public class HttpClientUtils {

	private static boolean showHeaderInfo = true;
	private static Object CONNECTION_TIMEOUT = 10 * 1000;// 连接超时10s
	private static Object SO_TIMEOUT = 20 * 1000;// 读取超时20s

	private static String getForm(String url, Map<String, Object> params, Object headers) {
		// 响应内容
		String resContent = "";
		// 创建默认的httpClient实例

		return url;
	}

	/*
	 * <p>Title: post</p> Description: <pre>同步Post方法</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-08
	 * 
	 * @param url (String) : 访问地址
	 * 
	 * @param data (String) : 提交的Json格式参数
	 * 
	 * @return String : Response的结果
	 */
	public String post(String url, String data) {
		String response = null;

		try {
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse httpresponse = null;
			try {
				httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost(url);
				StringEntity stringentity = new StringEntity(data, ContentType.create("text/json", "UTF-8"));
				httppost.setEntity(stringentity);
				httpresponse = httpclient.execute(httppost);
				response = EntityUtils.toString(httpresponse.getEntity());

			} finally {
				if (httpclient != null) {
					httpclient.close();
				}
				if (httpresponse != null) {
					httpresponse.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}


}
