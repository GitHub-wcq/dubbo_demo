package com.youlanw.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.youlanw.common.utils.redis.RedisPoolUtil;

public class RedisTest {

	//region Item 操作测试
	@Test
	public void Get() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"ylredis","10.0.15.139",null,null);
		System.out.println(redis.ItemGet("a")); 
	}
	
	@Test
	public void Set() {
		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.ItemSet("a", "2")); 
	}
	
	@Test
	public void SetEx() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
//		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.ItemSetEx("a", 20, "ababab")); 
	}
	
	@Test
	public void Append() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
		System.out.println(redis.ItemAppend("a", "assafaf2")); 
	}
	
	@Test
	public void AppendAutoCreate() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
		System.out.println(redis.ItemAppendAutoCreate("aa", "assafaf2")); 
	}
	
	@Test
	public void Delete() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
		System.out.println(redis.ItemDelete(new String[] {"aa", "a", "cc1"})); 
	}
	
	@Test
	public void MSet() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
		System.out.println(redis.ItemMSet("a","1","b","2")); 
	}
	
	//end
	
	// region 时间过期测试
	
	@Test
	public void Expire() {
		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.Expire("b", 30)); 
	}
	
	@Test
	public void ExpireAt() {
		RedisPoolUtil redis = new RedisPoolUtil();
		
		long curren = System.currentTimeMillis();
        curren += 1 * 60 * 1000;
        Date da = new Date(curren);
		
		System.out.println(redis.ExpireAt("a", da)); 
	}
	// end
	
	@Test
	public void hashSet() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
		String key = "hashTest001key";
		String field = "hashTest001.field001";
		String value = "hashTest001.field001.value";
		System.out.println(redis.hashSet(key, field, value));
	}
	@Test
	public void hashGet() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
		String key = "hashTest001key";
		String field = "hashTest001.field001";
		System.out.println(redis.hashGet(key, field));
	}
	@Test
	public void hashMset() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
		String key = "hashTest002key";
		Map<String,String> map = new HashMap<>();
		map.put("hashTest002.field001", "hashTest002.field001.value===1");
		map.put("hashTest002.field002", "hashTest002.field002.value===2");
		map.put("hashTest002.field003", "hashTest002.field003.value===3");
		System.out.println(redis.hashMset(key, map));
	}
	@Test
	public void hashMget() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
		String key = "hashTest001key";
		String field001 = "hashTest001.field001";
		String field002 = "hashTest001.field002";
		List<String> list = redis.hashMget(key, field001 , field002);
		for(String str : list) {
			System.out.println(str);
		}
	}
	@Test
	public void hashGetAll() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
	}
	@Test
	public void hashKeys() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
	}
	@Test
	public void hashVals() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
	}
	@Test
	public void hashExists() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
	}
	@Test
	public void hashLength() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
	}
	@Test
	public void hashDelete() {
		RedisPoolUtil redis = new RedisPoolUtil(null,"123456","192.168.80.130",null,null);
	}
	
}
