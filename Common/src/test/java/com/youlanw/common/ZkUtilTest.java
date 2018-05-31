package com.youlanw.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.youlanw.common.Beans.RedisConnConfig;
import com.youlanw.common.utils.zookeeper.ZkACLType;
import com.youlanw.common.utils.zookeeper.ZooKeeperUtil;

public class ZkUtilTest {
	
	public static void main(String[] args) {
		ZooKeeperUtil zkUtil =ZooKeeperUtil.getInstance("10.0.11.47", null);
//		boolean f = zk.recursionCreateWithACL("/test/abc/cde", getInitACLList());
//		System.out.println(f);
//		boolean l = zk.setZnodeACL("/test/abc/cde", getInitACLList());
//		System.out.println(l);
		//zk.setZnodeACL(path, map);
//		boolean d = zk.recursionDelete("/youlanw/c/test");
//		System.out.println(d);
		System.out.println(zkUtil.getZnodeData("/youlanw/c/main/mysql"));
	}
	
	
	public static Map<String,Map<String,List<ZkACLType>>> getInitACLList(){
		Map<String,Map<String,List<ZkACLType>>> map = new HashMap<>();
		Map<String,List<ZkACLType>> ipMap = new HashMap<>();
		List<ZkACLType> ipAclList = new ArrayList<>();
		ipAclList.add(ZkACLType.ALL);
		InputStream in = null;
		try {
			File file = new File("D:\\workspace\\youlanwWorkspace_6\\youlan.java\\Common\\src\\test\\java\\com\\youlanw\\common\\znode.properties");
			in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);
			String ips = properties.getProperty("znode.acl.ip");
			String[] ipArr = ips.split(",");
			for(String ip : ipArr) {
				ipMap.put(ip, ipAclList);
			}
			map.put("ip", ipMap);
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
		Map<String,List<ZkACLType>> worldMap = new HashMap<>();
		List<ZkACLType> worldAclList = new ArrayList<>();
		worldAclList.add(ZkACLType.READ);
		worldMap.put("anyone", worldAclList);
		map.put("world", worldMap);
		return map;
	}
	@Test
	public void test() {
		String value = "{\"address\":\"10.0.11.65\",\"password\":\"\",\"port\":\"6379\",\"dbIndex\":\"0\"}";
		RedisConnConfig ccc = JSONObject.parseObject(value, RedisConnConfig.class);
		System.out.println(ccc.getRedisAddress());
	}
	
	@Test
	public void update() throws InterruptedException{
		ZooKeeperUtil zkUtil =ZooKeeperUtil.getInstance("10.0.11.47", null);
		boolean falg = true;
		for(int i = 0 ; i < 25; i++) {
			if(falg) {
				zkUtil.updateZnode("/youlanw/c/main/redis", "{\"redisAddress\":\"10.0.11.65\",\"redisPassword\":\"\",\"redisPort\":\"6379\",\"redisDbindex\":\"4\"}");
				falg = false;
			} else {
				zkUtil.updateZnode("/youlanw/c/main/redis", "{\"redisAddress\":\"10.0.11.65\",\"redisPassword\":\"\",\"redisPort\":\"6379\",\"redisDbindex\":\"5\"}");
				falg = true;
			}
			Thread.sleep(2000);
		}
	}


}
