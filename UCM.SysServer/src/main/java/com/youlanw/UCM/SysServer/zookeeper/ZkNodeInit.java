package com.youlanw.UCM.SysServer.zookeeper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ZkNodeInit {
	
	public void init() {
		System.out.println("ZkNodeInit初始化..");
		InputStream in = null;
		try {
			in = ZkNodeInit.class.getClassLoader().getResourceAsStream("dubbo/znode.properties");
			Properties properties = new Properties();
			properties.load(in);
			String zkCNode = properties.getProperty("zk.c.node");
			
	        
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
	public void destroy() {
		System.out.println("ZkNodeInit销毁");
	}
}
