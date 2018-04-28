package com.youlanw.common.utils.http;

import java.util.Map;

/**
 * 
 * <p>Title: HttpClientUtils</p>  
 * Description: <pre>HttpClient工具类</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
@SuppressWarnings("unused")
public class HttpClientUtils {
	
	private static boolean showHeaderInfo = true;
	private static Object CONNECTION_TIMEOUT = 10 * 1000;// 连接超时10s
	private static Object SO_TIMEOUT = 20 * 1000;// 读取超时20s
	
	private static String getForm(String url, Map<String, Object> params, Object headers) {
		//响应内容
		String resContent = "";
		//创建默认的httpClient实例
		
		return url;
	}

}
