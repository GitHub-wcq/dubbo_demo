package com.youlanw.UCM.Interfaces;

import java.util.List;
import java.util.Map;

import com.youlanw.UCM.Interfaces.model.Znode;
import com.youlanw.common.utils.zookeeper.ZkACLType;
/**
 * 
 * <p>Title: ZookeeperService</p>  
 * Description: <pre>UCM操作zookeeper节点接口，该接口要求每个方法都必须携带密钥参数</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
public interface ZookeeperService {
	/**
	 * <p>Title: getChilden</p>  
	 * Description: <pre>获取子节点集合</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param secretKey 密钥
	 * @return
	 */
	List<String> getChilden(String path);
	/**
	 * 
	 * <p>Title: getZnodeDate</p>  
	 * Description: <pre>获取节点内容</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param secretKey 密钥
	 * @return
	 */
	String getZnodeDate(String path);
	/**
	 * <p>Title: addPersistentZnode</p>  
	 * Description: <pre>添加永久节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param data 节点内容
	 * @param secretKey 密钥
	 * @return
	 */
	boolean addPersistentZnode(String path,String data,Map<String,Map<String,List<ZkACLType>>> map);
	/**
	 * <p>Title: addEphemeralZnode</p>  
	 * Description: <pre>添加临时节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param data 节点内容
	 * @param secretKey 密钥
	 * @return
	 */
	boolean addEphemeralZnode(String path,String data);
	/**
	 * 
	 * <p>Title: isExits</p>  
	 * Description: <pre>判断节点是否存在</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param secretKey 密钥
	 * @return
	 */
	boolean isExits(String path);
	/**
	 * 
	 * <p>Title: updateZnodeData</p>  
	 * Description: <pre>更新节点内容</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param data 节点内容
	 * @param secretKey 密钥
	 * @return
	 */
	boolean updateZnodeData(String path, String data);
	/**
	 * 
	 * <p>Title: deleteZnode</p>  
	 * Description: <pre>删除节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param secretKey 密钥
	 * @return
	 */
	boolean deleteZnode(String path);
	/**
	 * 
	 * <p>Title: recursionDeleteNodes</p>  
	 * Description: <pre>递归创建节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param secretKey 密钥
	 * @return
	 */
	boolean recursionDeleteNodes(String path);
	/**
	 * 
	 * <p>Title: recursionCreateNodes</p>  
	 * Description: <pre>递归创建节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path 节点路径
	 * @param secretKey 密钥
	 * @return
	 */
	boolean recursionCreateNodes(String path,Map<String,Map<String,List<ZkACLType>>> map);
	/**
	 * 
	 * <p>Title: getChildenAndData</p>  
	 * Description: <pre>获取当前节点及该节点下的所有节点的路径及内容信息</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path
	 * @param secretKey
	 * @return
	 */
	Znode getChildenAndData(String path);
	/**
	 * <p>Title: setZnodeAcl</p>  
	 * Description: <pre>设置节点权限</pre>  
	 * @author wangchaoqun 
	 * @date 2018年5月19日  
	 * @param path
	 * @param map
	 * @return
	 */
	boolean setZnodeAcl(String path,Map<String,Map<String,List<ZkACLType>>> map);
	
	
}
