package com.wcq.Z.Consumer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.Z.Interfaces.service.DemoService;

import com.wcq.Z.Consumer.spring.SpringUtils;

public class AutoListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("销毁...");
		SpringUtils.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("初始化...");
		DemoService demo = SpringUtils.getBean(DemoService.class);
		String str = demo.hello(" hello ");
		System.out.println(" === " + str);
	}
	
}
