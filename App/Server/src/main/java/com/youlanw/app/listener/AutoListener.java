package com.youlanw.app.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.youlanw.app.utils.RedisUtil;
import com.youlanw.common.Beans.InitConnConfig;


public class AutoListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("销毁...");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("AutoListener初始化...");
		setSystemProperty();
		
	}
	
	/**
	 * <p>Title: setSystemProperty</p>  
	 * Description: <pre>通过dubboEnv指向的properties文件设置系统属性</pre>  
	 * @author wangchaoqun 
	 * @date 2018年5月12日
	 */
	private void setSystemProperty() {
		InputStream in = null;
		String dubboEnv = System.getProperty("dubboEnv");
		System.out.println("dubboEnv : " + dubboEnv);
		if(dubboEnv!= null && dubboEnv != "") {
			if(dubboEnv.startsWith("file")) {
				dubboEnv = dubboEnv.substring(7);
			}
		} else {
			throw new RuntimeException("系统属性dubboEnv为空");
		}
		File file = new File(dubboEnv);
		try {
			in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);
			
			InitConnConfig.init(properties.getProperty("dubbo.zookeeper.address"));
			RedisUtil.getRedisPoolUtil();
			//设置积分接口
			getIntegralUrl(properties);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in!= null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void getIntegralUrl(Properties properties) {
		String integralUrl = properties.getProperty("integral.api.url");
		if(integralUrl != null || integralUrl != "") {
			System.setProperty("integral.api.url", integralUrl);
		}
	}
	
}
