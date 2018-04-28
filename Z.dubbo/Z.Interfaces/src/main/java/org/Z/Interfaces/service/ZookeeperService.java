package org.Z.Interfaces.service;

import java.util.List;
/**
 * 
 * <p>Title: ZookeeperService</p>  
 * Description: <pre></pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
public interface ZookeeperService {
	
	List<String> getChilden(String path);
	
	String getZnodeDate(String path);
	
	boolean addPersistentZnode(String znode,String data);
	
	boolean addEphemeralZnode(String znode,String data);
	
	
	
}
