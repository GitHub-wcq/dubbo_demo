package com.youlanw.UCM.Consumer;

import java.io.IOException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.youlanw.UCM.Interfaces.DemoService;
import com.youlanw.UCM.Interfaces.ZookeeperService;

public class DemoTest {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		System.setProperty("dubboEnv", "classpath:spring/dubbo.properties");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/dubbo-consumer.xml"});
		context.start();
		DemoService demo = context.getBean(DemoService.class);
		String str = demo.helloWorld("admin");
		System.out.println(str);
		ZookeeperService zkService = context.getBean(ZookeeperService.class);
		List<String> list = zkService.getChilden("/dubbo","123456");
		for(String znode : list) {
			System.out.println(znode);
		}
		String getChildenAndData = JSONObject.toJSONString(zkService.getChildenAndData("/youlanw","123456"));
		System.out.println(getChildenAndData);
		
		System.in.read();
	}

}
