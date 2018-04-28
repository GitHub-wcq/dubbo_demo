package com.youlanw.common.utils.zookeeper;

import java.io.IOException;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
/**
 * 
 * <p>Title: ZooKeeperUtil</p>  
 * Description: <pre>zookeeper操作工具类</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
public class ZooKeeperUtil{
	/**
	 * 默认session时间
	 */
	private static final int SESSION_TIMEOUT=3000;
	
	private static ZooKeeper zookeeper;
	private ZkClient zkClient;
	public ZooKeeperUtil() {
		
	}
	/**
	 * 
	 * <p>Title: </p>  
	 * Description: <pre></pre> 
	 * @param address zookeeper连接地址 host:port
	 * @param sessionTimeout session timeout in milliseconds
	 */
	public ZooKeeperUtil(String address, Integer sessionTimeout) {
		try {
			if(sessionTimeout == null) {
				sessionTimeout = SESSION_TIMEOUT;
			}
			zookeeper = new ZooKeeper(address, sessionTimeout,null);
			this.zkClient = new ZkClient(address,sessionTimeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <p>Title: </p>  
	 * Description: <pre></pre> 
	 * @param address zookeeper连接地址 host:port
	 * @param session Timeout session timeout in milliseconds
	 * @param watcher a watcher object which will be notified of state changes
	 */
	public ZooKeeperUtil(String address, Integer sessionTimeout, Watcher watcher) {
		try {
			if(sessionTimeout == null) {
				sessionTimeout = SESSION_TIMEOUT;
			}
			zookeeper = new ZooKeeper(address, sessionTimeout, watcher);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <p>Title: getZooKeeper</p>  
	 * Description: <pre></pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @return
	 */
	public ZooKeeper getZooKeeper() {
		return zookeeper;
	}
	/**
	 * 
	 * <p>Title: isExist</p>  
	 * Description: <pre>判断节点是否存在</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param path
	 * @return
	 */
	public boolean isExist(String path) {
		try {
			Stat stat = zookeeper.exists(path, true);
			if(stat!= null) {
				return true;
			} else {
				System.out.println("znode:"+path+",不存在");  
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false ;
	}
	/**
	 * 
	 * <p>Title: addZnodeData</p>  
	 * Description: <pre>创建znode结点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param path 节点路径
	 * @param data 节点数据
	 * @param mode 节点类型
	 * @return true 创建结点成功 false表示结点存在
	 */
	public boolean addZnodeData(String path,String data,CreateMode mode) {
		try {
			Stat stat = zookeeper.exists(path,true);
			if(stat == null) {
				if(data != null) {
					zookeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, mode);
				} else {
					zookeeper.create(path, null, Ids.OPEN_ACL_UNSAFE, mode);
				}
				return true;
			} else {
				System.out.println("znode:"+path+",已存在");  
			}
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException("创建znode："+path+"出现问题！！",e);
		}
		System.out.println("znode"+path+"结点已存在");
		return false; 
	}
	/**
	 * 
	 * <p>Title: addPersistentZnode</p>  
	 * Description: <pre>创建永久Znode节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param path 节点路径
	 * @param data 节点数据
	 * @return true 创建结点成功 false表示结点存在
	 */
	public boolean addPersistentZnode(String path,String data) {
		return addZnodeData(path, data, CreateMode.PERSISTENT);
	}
	/**
	 * 
	 * <p>Title: addEphemeralZnode</p>  
	 * Description: <pre>创建临时Znode节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param path 节点路径
	 * @param data 节点数据
	 * @return true 创建结点成功 false表示结点存在
	 */
	public boolean addEphemeralZnode(String path,String data) {
		return addZnodeData(path, data, CreateMode.EPHEMERAL);
	}
	/**
	 * 
	 * <p>Title: updateZnode</p>  
	 * Description: <pre>修改节点内容</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param path 节点路径
	 * @param data 节点数据
	 * @return true 修改结点成功 false修改结点失败
	 */
	public boolean updateZnode(String path,String data) {
		Stat stat;
		try {
			stat = zookeeper.exists(path, true);
			if( stat != null) {
				if(data != null) {
					zookeeper.setData(path, data.getBytes(), stat.getVersion());
				} else {
					zookeeper.setData(path, null, stat.getVersion());
				}
				return true;
			} else {
				System.out.println("znode:"+path+",不存在");  
			}
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException("修改znode："+path+"出现问题！！",e);
		}
		return false;
	}
	/**
	 * 
	 * <p>Title: deleteZnode</p>  
	 * Description: <pre>删除节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param path 节点路径
	 * @return true 删除结点成功 false删除结点失败
	 */
	public boolean deleteZnode(String path) {
		Stat stat;
		try {
			stat = zookeeper.exists(path, true);
			if(stat != null) {
				List<String> subPaths=zookeeper.getChildren(path, false);
				if(subPaths.isEmpty()) {
					zookeeper.delete(path, stat.getVersion());
					return true;
				} else {
					for(String subPath : subPaths) {
						deleteZnode(path + "/" + subPath);
					}
				}
			} else {
				System.out.println("znode:"+path+",不存在");  
			}
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException("删除znode："+path+"出现问题！！",e);
		}
		return false;
	}
	/**
	 * 
	 * <p>Title: getZnodeData</p>  
	 * Description: <pre>获取节点数据</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param path 节点路径
	 * @return 节点内容
	 */
	public String getZnodeData(String path) {
		String data = null;
		Stat stat = null;
		try {
			stat = zookeeper.exists(path, true);
			if(stat != null) {
				byte[] datas = zookeeper.getData(path, true, stat);
				if(datas != null) {
					data = new String(datas);
				}
			} else {
				System.out.println("znode:"+path+",不存在");  
			}
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException("取到znode："+path+"出现问题！！",e);
		}
		return data;
	}
	/**
	 * 
	 * <p>Title: getChilden</p>  
	 * Description: <pre>获取节点列表</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param path
	 * @return 节点list
	 */
	public List<String> getChilden(String path){
		List<String> list = null;
		Stat stat = null;
		try {
			stat = zookeeper.exists(path, true);
			if(stat != null) {
				list = zookeeper.getChildren(path, true);
			} else {
				System.out.println("znode:"+path+",不存在");  
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 
	 * <p>Title: recursionCreate</p>  
	 * Description: <pre>递归创建节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path
	 * @param data
	 * @return
	 */
	public boolean recursionCreate(String path) {
		if(!path.startsWith("/")) {
			return false;
		}
		if(!isExist(path)) {
			String parentNode = path.substring(0, path.lastIndexOf("/"));
			if(parentNode == null || parentNode == "" || parentNode.length() <= 0) {
				return addPersistentZnode(path, null);
			}
			if(!isExist(parentNode)) {
				recursionCreate(parentNode);
			}
		}
		return addPersistentZnode(path,null);	
	}
	/**
	 * 
	 * <p>Title: recursionDelete</p>  
	 * Description: <pre>递归删除节点(禁止删除根节点 :/)</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param path
	 * @return
	 */
	public boolean recursionDelete(String path) {
		if(!path.startsWith("/") || "/".equals(path)) {
			return false;
		}
		List<String> list = getChilden(path);
		if(list == null || list.size() <= 0) {
			System.out.println("删除当前节点 : " + path);
			return deleteZnode(path);
		}
		list.forEach(childenZnode -> {
			recursionDelete(path + "/" + childenZnode);
		});
		System.out.println("删除当前节点 : " + path);
		return deleteZnode(path);
	}
	
	/**
	 * 监听节点变化
	 * @param path
	 * @param watcher
	 */
	public void subscribeChildChanges(String path, ZkNodeWatcher watcher) {
		watcher.subscribeChildChanges(path, zkClient);
	}
	/**
	 * 监听节点数据变化
	 * @param path
	 * @param watcher
	 */
	public void subscribeDataChanges(String path, ZkNodeWatcher watcher) {
		watcher.subscribeDataChanges(path, zkClient);
	}
}
