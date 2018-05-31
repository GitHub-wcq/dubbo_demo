package com.youlanw.common.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {

	/**
	 * 
	 * <p>Title: toMD5Str</p>  
	 * Description: <pre>MD5加密</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月20日  
	 * @param value 被加密值
	 * @return
	 */
	public static String toMD5Str(String value) {
		return encrypt(value, "MD5");
	}
	/**
	 * 
	 * <p>Title: toSHAStr</p>  
	 * Description: <pre>SHA加密</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月20日  
	 * @param value 被加密值
	 * @return
	 */
	public static String toSHAStr(String value) {
		return encrypt(value, "SHA");
	}
	/**
	 * 
	 * <p>Title: encrypt</p>  
	 * Description: <pre></pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月20日  
	 * @param value 被加密值
	 * @param encryptType 加密方式
	 * @return
	 */
	private static String encrypt(String value, String encryptType) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(encryptType);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		byte[] byteArray;
		try {
			byteArray = value.getBytes("UTF-8");
			byte[] bytes = messageDigest.digest(byteArray);
	        StringBuffer hexValue = new StringBuffer();
	        for (int i = 0; i < bytes.length; i++) {
	            int val = ((int) bytes[i]) & 0xff;
	            if (val < 16) { 
	                hexValue.append("0");
	            }
	            hexValue.append(Integer.toHexString(val));
	        }
	        return hexValue.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * <p>Title: urlEncode</p>  
	 * Description: <pre>UrlEncode处理字符串</pre>  
	 * @author Sean.Wang 
	 * @date 2018年5月26日  
	 * @param value 被处理的值
	 * @param format 编码格式，例如 “GBK”
	 * @return encode后的值
	 * @throws UnsupportedEncodingException 
	 */
	public static String urlEncode(String value, String format) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, format);
	}
	
	/**
	 * 
	 * <p>Title: urlDecoder</p>  
	 * Description: <pre>UrlDecode处理字符串</pre>  
	 * @author Sean.Wang 
	 * @date 2018年5月26日  
	 * @param value 被处理的值
	 * @param format 编码格式，例如 “GBK”
	 * @return decode后的值
	 * @throws UnsupportedEncodingException 
	 */
	public static String urlDecode(String value, String format) throws UnsupportedEncodingException {
		return URLDecoder.decode(value, format);
	}
}
