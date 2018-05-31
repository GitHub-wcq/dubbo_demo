package com.youlanw.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtils {
	
	/**
	 * 保存cookie
	 * @param cookieKey
	 * @param cookieValue
	 * @param response
	 * @param time 秒
	 */
	public static void setCookie(String cookieKey, String cookieValue, HttpServletResponse response,int time) {
		try {
 			if (response != null) {
				Cookie cookie = new Cookie(cookieKey, cookieValue);
                cookie.setMaxAge(time);
                cookie.setPath("/");
				response.addCookie(cookie);
			}
		} catch (Exception e) {
			System.out.println("cookieKey:"+cookieKey+",cookieValue:"+cookieValue+"保存失败！");
		}
	}


    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name){
        Map<String,Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }   
    }
    
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}