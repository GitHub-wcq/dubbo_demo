package com.youlanw.app.controller;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.youlanw.common.utils.redis.RedisPoolUtil;

public class CountdownLatchTest {
	
	
	public static void main(String[] args) {
		RedisPoolUtil redisPoolUtil = new RedisPoolUtil(null,"10.0.15.225","ylredis",6379,0);
		for(int i =0; i<2000;i++) {
			CountdownLatchTest.test(redisPoolUtil, "tom"+i, 120);
		}
		
	}

	public static void test(RedisPoolUtil redisPoolUtil,String key, int seconds) {
		ExecutorService service = Executors.newCachedThreadPool();
        //指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务
        final CountDownLatch cdOrder = new CountDownLatch(1);
        //因为有三个战士，所以初始值为3，每一个战士执行任务完毕则cutDown一次，当三个都执行完毕，变为0，则指挥官停止等待。
        final CountDownLatch cdAnswer= new CountDownLatch(2000);
        for (int i = 0; i < 3000; i++){
        	final String name = key+i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程" + Thread.currentThread().getName() + "正在准备接收命令");
                    try {
                        //战士们都处于等待命令状态
                        cdOrder.await();
//                        Singleton001 sl = Singleton001.getInstance();
//                        System.out.println(sl);
                        redisPoolUtil.ItemSetEx(name , seconds, "hello " + name + "!");
                        System.out.println("线程" + Thread.currentThread().getName() + "执行方法完毕" + " : hello " + key + "!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} finally {
                        //任务执行完毕，返回给指挥官，cdAnswer 减一
                        cdAnswer.countDown();
                    }
                }
            };
            //为线程池添加任务
            service.execute(runnable);
        }
        try {
            Thread.sleep(50);
            System.out.println("线程" + Thread.currentThread().getName() +  "即将发布命令");
            //发送命令，cdOrder减1，处于等待的战士们停止等待转去执行任务。
            cdOrder.countDown();
            System.out.println("线程" + Thread.currentThread().getName() + "已发送命令，正在等待结果");
            //命令发送后指挥官处于等待状态，一旦cdAnswer为0时停止等待继续往下执行
            cdAnswer.await();
            System.out.println("线程" + Thread.currentThread().getName() +  "已收到所有响应结果");
        } catch (Exception e){
            e.printStackTrace();
        }
        //任务结束，停止线程池的所有线程
        service.shutdown();
	}
	
}
