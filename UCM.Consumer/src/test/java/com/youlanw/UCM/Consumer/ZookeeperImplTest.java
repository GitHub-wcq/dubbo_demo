package com.youlanw.UCM.Consumer;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.youlanw.UCM.Interfaces.ZookeeperService;
import com.youlanw.UCM.Interfaces.model.Znode;

public class ZookeeperImplTest {
	
	private static final String SECRET_KEY = "123456";
	private ZookeeperService zookeeperService;
	public ClassPathXmlApplicationContext getContext() {
		System.setProperty("dubboEnv", "classpath:spring/dubbo.properties");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/dubbo-consumer.xml"});
		return context;
	}
	public ZookeeperService getZkService() {
		getContext().start();
		return getContext().getBean(ZookeeperService.class);
	}

	@Test
	public void getChilden() {
		zookeeperService = getZkService();
		List<String> list = zookeeperService.getChilden("/youlanw", SECRET_KEY);
		if(list != null) {
			list.forEach(e -> System.out.println(e));
		}
	}
	@Test
	public void getZnodeDate() {
		zookeeperService = getZkService();
		String str = zookeeperService.getZnodeDate("/youlanw/c/redis", SECRET_KEY);
		System.out.println(str);
	}
	@Test
	public void addPersistentZnode() {
		zookeeperService = getZkService();
		boolean flag = zookeeperService.addPersistentZnode("/youlanw/c/EHR", null, SECRET_KEY);
		System.out.println(flag);
	}
	@Test
	public void addEphemeralZnode() {
		zookeeperService = getZkService();
		boolean flag = zookeeperService.addEphemeralZnode("/youlanw/c/HRO", null, SECRET_KEY);
		System.out.println(flag);
	}
	@Test
	public void isExits() {
		zookeeperService = getZkService();
		
	}
	@Test
	public void updateZnodeData() {
		zookeeperService = getZkService();
		boolean flag = zookeeperService.updateZnodeData("/youlanw/c/EHR", "ehr=-fdsfijsodgijsdgji", SECRET_KEY);
		System.out.println(flag);
	}
	@Test
	public void deleteZnode() {
		zookeeperService = getZkService();
		boolean flag = zookeeperService.deleteZnode("", SECRET_KEY);
		System.out.println(flag);
	}
	@Test
	public void recursionDeleteNodes() {
		zookeeperService = getZkService();
		boolean flag = zookeeperService.recursionDeleteNodes("/youlanw", SECRET_KEY);
		System.out.println(flag);
	}
	@Test
	public void recursionCreateNodes() {
		zookeeperService = getZkService();
		boolean flag = zookeeperService.recursionCreateNodes("/youlanw/b/rabbitmq/EHR", SECRET_KEY);
		System.out.println(flag);
		
	}
	@Test
	public void getChildenAndData() {
		zookeeperService = getZkService();
		Znode node = zookeeperService.getChildenAndData("/youlanw", SECRET_KEY);
		System.out.println(JSONObject.toJSONString(node));
	}
	@Test
	public void saveZkSecretKey() {
		zookeeperService = getZkService();
		
	}
	@Test
	public void test() {
		System.out.println("test");
	}
}
