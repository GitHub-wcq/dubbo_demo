package org.Z.Provider.service.impl;

import java.util.List;

import org.Z.Interfaces.service.ZookeeperService;
import org.Z.Provider.service.util.ZookeeperNodeUtil;
import org.springframework.stereotype.Service;

import com.youlanw.common.utils.zookeeper.ZooKeeperUtil;

/**
 * 
 * <p>Title: ZookeeperServiceImpl</p>  
 * Description: <pre>zookeeper服务实现</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
@Service("zookeeperService")
public class ZookeeperServiceImpl implements ZookeeperService {
	
	private ZooKeeperUtil zk = ZookeeperNodeUtil.getZooKeeperUtil();
	
	@Override
	public List<String> getChilden(String path) {
		List<String> list = null;
		list = zk.getChilden(path);
		System.out.println("list.size : " + list.size());
		return list;
	}

	@Override
	public String getZnodeDate(String path) {
		return zk.getZnodeData(path);
	}

	@Override
	public boolean addPersistentZnode(String znode, String data) {
		return zk.addPersistentZnode(znode, data);
	}

	@Override
	public boolean addEphemeralZnode(String znode, String data) {
		return zk.addEphemeralZnode(znode, data);
	}

}
