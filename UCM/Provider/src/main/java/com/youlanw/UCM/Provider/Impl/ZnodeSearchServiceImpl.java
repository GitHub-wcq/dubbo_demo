package com.youlanw.UCM.Provider.Impl;

import org.springframework.stereotype.Service;

import com.youlanw.UCM.Interfaces.ZnodeSearchService;
import com.youlanw.UCM.Provider.Enum.InitZnodePath;
import com.youlanw.UCM.Provider.util.ZookeeperNodeUtil;
import com.youlanw.common.utils.zookeeper.ZooKeeperUtil;

@Service(value="znodeSearchService")
public class ZnodeSearchServiceImpl implements ZnodeSearchService {

	private static final String ZK_C_NODE=InitZnodePath.ZK_C_NODE.getValue();
	private ZooKeeperUtil zk = ZookeeperNodeUtil.getZooKeeperUtil();
	
	@Override
	public String searchCZnode(String projectName,String type) {
		String path = ZK_C_NODE +"/"+projectName+"/"+type;
		System.out.println(path);
		if(zk.isExist(path)) {
			return zk.getZnodeData(path);
		}
		return null;
	}
	
}
