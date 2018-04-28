package org.Z.Provider.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.youlanw.common.utils.zookeeper.ZooKeeperUtil;

/**  
 * <p>Title: ZookeeperNodeUtil</p>  
 * Description: <pre>zookeeper初始化连接，及部分节点操作</pre>   
 * @author wangchaoqun 
 * @date 2018年4月19日  
 */
public class ZookeeperNodeUtil {
	
    private static final int SESSION_TIMEOUT=3000;
	private static ZooKeeperUtil zk;
	private static String address;
	private static int sessionTimeout;

	static {
		String dubboEnv = System.getProperty("dubboEnv");
		System.out.println(dubboEnv);
		String dubboProPertiesPath = "dubbo/dubbo.properties";
		if(dubboEnv == null || dubboEnv == "") {
			dubboProPertiesPath = dubboEnv;
		}
		InputStream in = null;
		try {
			in = ZookeeperNodeUtil.class.getClassLoader().getResourceAsStream(dubboProPertiesPath);
			Properties properties = new Properties();
			properties.load(in);
			address = properties.getProperty("dubbo.zookeeper.address");
			if(properties.get("dubbo.zookeeper.sessionTimeout") != null) {
				try {
					sessionTimeout = (int) properties.get("dubbo.zookeeper.sessionTimeout");
				} catch (Exception e) {
					sessionTimeout = SESSION_TIMEOUT;
				}
			} else {
				sessionTimeout = SESSION_TIMEOUT;
			}
			zk = new ZooKeeperUtil(address,sessionTimeout);
			System.out.println("zookeeper连接成功...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void init() {

	}

	public static ZooKeeperUtil getZooKeeperUtil() {
		return zk;
	}

	
	
}
