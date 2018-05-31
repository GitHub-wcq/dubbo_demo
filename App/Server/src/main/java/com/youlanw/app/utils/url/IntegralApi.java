package com.youlanw.app.utils.url;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class IntegralApi {

	
	/**
     * 请求编码，默认使用utf-8
     */
    private static String urlCharset = "UTF-8";
	/**
     * 调用积分接口
     *
     * @param code
     * @param uid   优蓝网用户Id
     * @param value
     * @return {"status":xxx, "msg":xxx}
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> postIntegralApi(String code, Integer uid, Integer value) {
        if (uid == null || uid <= 0) {
            System.err.println("用户Id为空，停止调用积分接口");
            return null;
        } else {
            Map<String, String> resultMap = new HashMap<String, String>();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String integralApiUrl = System.getProperty("integral.api.url");
            if(integralApiUrl == null || integralApiUrl == "") {
            	try {
					throw new Exception("未获取积分接口环境变量:integral.api.url");
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            HttpPost post = new HttpPost(integralApiUrl);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("userId", uid + ""));
            parameters.add(new BasicNameValuePair("code", code));
            parameters.add(new BasicNameValuePair("value", value == null ? "1" : value + ""));
            try {
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, urlCharset);
                post.setEntity(formEntity);
                CloseableHttpResponse response = httpClient.execute(post);
                String jsonStr = EntityUtils.toString(response.getEntity());
                if (StringUtils.isNotBlank(jsonStr) && !"forbidden".equals(jsonStr)) {
                    try {
                        resultMap = JSON.parseObject(jsonStr, Map.class);
                    } catch (Exception e) {
                        System.err.println("发生错误:" + "\nuserId:" + uid + "\ncode:" + code + "\nvalue:" + value + "\nmsg:" + e.getMessage() + "\njsonStr:" + jsonStr);
                    }
                } else {
                    System.err.println("积分接口api被屏蔽:forbidden,接口地址:" + integralApiUrl);
                }
                httpClient.close();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultMap;
        }
    }
	
}
