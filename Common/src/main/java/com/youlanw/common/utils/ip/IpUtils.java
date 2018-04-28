package com.youlanw.common.utils.ip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <p>Title: IpUtils</p>  
 * Description: <pre>IP地址工具类</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
public class IpUtils {
	
	/**
	 * 
	 * <p>Title: getIpAddrByRequest</p>  
	 * Description: <pre>获取远程IP地址</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param request 请求对象
	 * @return String 返回IP地址
	 */
	public static String getIpAddrByRequest(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 
	 * <p>Title: getRealIp</p>  
	 * Description: <pre>获取本机IP地址</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @return String 返回IP地址
	 * @throws SocketException
	 */
	public static String getRealIp() throws SocketException {
		// 本地IP，如果没有配置外网IP则返回它
		String localip = null;
		// 外网IP
		String netip = null;
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		// 是否找到外网IP
		boolean finded = false;
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

}
