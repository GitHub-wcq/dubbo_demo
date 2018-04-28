package com.youlanw.UCM.Consumer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * 
 * <p>Title: ZkNodeInit</p>  
 * Description: <pre>这个类应该放到consumer中使用,初始化创建zookeeperNode.properties里的节点</pre>   
 * @author wangchaoqun 
 * @date 2018年4月19日
 */
public class ZkNodeInit {
	
	public void init() throws InterruptedException {
		Thread.sleep(3000);
		System.out.println("ZkNodeInit初始化..");
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader bufferedReader = null;
		try {
			in = ZkNodeInit.class.getClassLoader().getResourceAsStream("zookeeper/zookeeperNode.properties");
			isr = new InputStreamReader(in);
			bufferedReader = new BufferedReader(isr);  
	        String str = null;  
	        while((str = bufferedReader.readLine()) != null)  
	        {  
	            System.out.println(str);
	        } 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(isr != null ) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        
	}
	public void destroy() {
		System.out.println("ZkNodeInit销毁");
	}
}
