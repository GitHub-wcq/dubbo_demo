package com.youlanw.common;

import java.util.Date;

import org.junit.Test;

import com.youlanw.common.utils.redis.RedisPoolUtil;

public class RedisTest {

	//region Item 操作测试
	@Test
	public void Get() {
		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.ItemGet("aa")); 
	}
	
	@Test
	public void Set() {
		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.ItemSet("a", "2")); 
	}
	
	@Test
	public void SetEx() {
		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.ItemSetEx("a", 20, "20")); 
	}
	
	@Test
	public void Append() {
		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.ItemAppend("cc11", "assafaf2")); 
	}
	
	@Test
	public void AppendAutoCreate() {
		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.ItemAppendAutoCreate("aa", "assafaf2")); 
	}
	
	@Test
	public void Delete() {
		RedisPoolUtil redis = new RedisPoolUtil();
		System.out.println(redis.ItemDelete(new String[] {"aa", "a", "cc1"})); 
	}
	
	@Test
	public void MSet() {
		RedisPoolUtil redis = new RedisPoolUtil();
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
}
