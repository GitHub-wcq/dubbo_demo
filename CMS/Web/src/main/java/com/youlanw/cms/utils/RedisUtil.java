package com.youlanw.cms.utils;

import org.apache.commons.lang.StringUtils;
import com.youlanw.common.utils.redis.RedisPoolUtil;
import redis.clients.jedis.JedisPoolConfig;
public class RedisUtil {
	
	private volatile static RedisPoolUtil redisPoolUtil;
	private static JedisPoolConfig config;
	private static String address;
	private static String password;
	private static Integer port;
	private static Integer dbIndex;
	
	public static RedisPoolUtil getRedisPoolUtil() {
		if(null == redisPoolUtil){
			synchronized(RedisPoolUtil.class){
				if(null == redisPoolUtil){ 
					if(!StringUtils.isBlank(System.getProperty("redisAddress"))) {
						address = System.getProperty("redisAddress");
					}
					if(!StringUtils.isBlank(System.getProperty("redisPassword"))) {
						password = System.getProperty("password");
					}
					if(!StringUtils.isBlank(System.getProperty("redisPort"))) {
						port = Integer.valueOf(System.getProperty("redisPort"));
					}
					if(!StringUtils.isBlank(System.getProperty("redisDbindex"))) {
						dbIndex = Integer.valueOf(System.getProperty("redisDbindex"));
					}
					redisPoolUtil = new RedisPoolUtil(config,address,password,port,dbIndex);
				}
			}
		}
		return redisPoolUtil;
	}
	
}
