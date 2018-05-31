package com.youlanw.app.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youlanw.app.utils.RedisUtil;
import com.youlanw.common.utils.redis.RedisPoolUtil;

@RestController
@RequestMapping("/demo")
public class DemoController {
	
	private static AtomicInteger count=new AtomicInteger(0);  
	
	private RedisPoolUtil redisPoolUtil = RedisUtil.getRedisPoolUtil();
	
	@RequestMapping(value = "/hello", method=RequestMethod.GET)
	public String hello(String name) {
		redisPoolUtil.ItemSetEx(name, 300, "hello " + name + "!");
		return count.getAndIncrement() + " : hello " + name + "!";
	}

}
