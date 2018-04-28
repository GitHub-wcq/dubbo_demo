package com.youlanw.UCM.Provider.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.youlanw.UCM.Interfaces.ZookeeperService;
import com.youlanw.UCM.Interfaces.model.Znode;
import com.youlanw.UCM.Provider.Enum.InitZnodePath;
import com.youlanw.UCM.Provider.util.ZookeeperNodeUtil;
import com.youlanw.common.utils.encrypt.EncryptUtils;
import com.youlanw.common.utils.zookeeper.ZooKeeperUtil;

/**
 * 
 * <p>Title: ZookeeperServiceImpl</p>  
 * Description: <pre>zookeeper服务实现，要求每个方法实现都必须对密钥进行判断</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
@Service(value="zookeeperService")
public class ZookeeperServiceImpl implements ZookeeperService {
	
	//操作zookeeper节点必须携带密钥参数，该值是密钥存放的节点路径
	private static final String OPT_ZK_SECRET_KEY_ZNODE = InitZnodePath.OPT_ZK_SECRET_KEY_ZNODE.getValue();
	
	private static ZooKeeperUtil zk = ZookeeperNodeUtil.getZooKeeperUtil();
	
	/**
	 * 查询子节点,该方法禁止查询根节点（“/”）
	 */
	@Override
	public List<String> getChilden(String path,String secretKey) {
		if("/".equals(path)) {
			return null;
		}
		if(!isTrueSecretKey(secretKey)) {
			return null;
		}
		List<String> list = null;
		list = zk.getChilden(path);
		System.out.println("list.size : " + list.size());
		return list;
	}
	
	@Override
	public String getZnodeDate(String path,String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return null;
		}
		return zk.getZnodeData(path);
	}

	@Override
	public boolean addPersistentZnode(String znode, String data,String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return false;
		}
		return zk.addPersistentZnode(znode, data);
	}

	@Override
	public boolean addEphemeralZnode(String znode, String data,String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return false;
		}
		zk.subscribeDataChanges(znode, new ZkNodeWatcherImpl());
		return zk.addEphemeralZnode(znode, data);
	}

	@Override
	public boolean isExits(String path,String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return false;
		}
		return zk.isExist(path);
	}
	
	/**
	 * 查询path和path路径下的所有子节点及内容，该方法禁止查询根节点（“/”）
	 */
	@Override
	public Znode getChildenAndData(String path,String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return null;
		}
		if(!zk.isExist(path) || "/".equals(path)) {
			return null;
		}
		Znode znode = new Znode();
		znode.setPath(path);
		String name = path.substring(path.lastIndexOf("/")+1);
		if(name == null || name == "" || name.length() <= 0) {
			name = "/";
		}
		znode.setName(name);
		znode.setData(zk.getZnodeData(path));
		String parentPath = path.substring(0, path.lastIndexOf("/"));
		if(parentPath == null || parentPath == "" || parentPath.length() <= 0) {
			parentPath = "/";
		}
		znode.setParentPath(parentPath);
		znode = getChildenZnode(znode);
		return znode;
	}
	/**
	 * 
	 * <p>Title: getChildenZnode</p>  
	 * Description: <pre>将子节点信息递归添加到Znode中,获取znode和znode下的所有子节点的路径和内容信息</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param znode
	 * @return
	 */
	private Znode getChildenZnode(Znode znode) {
		List<Znode> znodelist = new ArrayList<>();
		List<String> list = zk.getChilden(znode.getPath());
		if(list != null && list.size() > 0) {
			list.forEach(childen -> {
				Znode cZnode = new Znode();
				String path = "";
				if("/".equals(znode.getPath())) {
					path = "/" + childen;
				} else {
					path = znode.getPath() + "/" + childen;
				}
				cZnode.setPath(path);
				cZnode.setName(childen);
				cZnode.setData(zk.getZnodeData(path));
				cZnode.setParentPath(znode.getName());
				if(zk.getChilden(path) != null && zk.getChilden(path).size() > 0) {
					getChildenZnode(cZnode);
				}
				znodelist.add(cZnode);
			});
		}
		znode.setChilden(znodelist);
		return znode;
	}
	
	@Override
	public boolean updateZnodeData(String path, String data, String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return false;
		}
		return zk.updateZnode(path, data);
	}

	@Override
	public boolean deleteZnode(String path, String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return false;
		}
		return zk.deleteZnode(path);
	}

	@Override
	public boolean recursionDeleteNodes(String path, String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return false;
		}
		return zk.recursionDelete(path);
	}
	
	@Override
	public boolean recursionCreateNodes(String path,String secretKey) {
		if(!isTrueSecretKey(secretKey)) {
			return false;
		}
		return zk.recursionCreate(path);
	}

	@Override
	public boolean updateZkSecretKey(String newSecretKey,String oldSecretKey) {
		//判断节点是否存在
		if(!zk.isExist(OPT_ZK_SECRET_KEY_ZNODE)) {
			//不存在创建永久节点
			zk.addPersistentZnode(OPT_ZK_SECRET_KEY_ZNODE, null);
		}
		String md5SecretKey = zk.getZnodeData(OPT_ZK_SECRET_KEY_ZNODE);
		//判断节点里是否有值
		if(md5SecretKey == null || md5SecretKey == "") {
			//无值，说明第一次传值，加密保存
			zk.updateZnode(OPT_ZK_SECRET_KEY_ZNODE, encryptionSecretKey(newSecretKey));
			return true;
		} else {
			//有值，判断旧密钥是否正确
			if(md5SecretKey.equals(encryptionSecretKey(oldSecretKey))) {
				//旧密钥正确，允许更新密钥，将新密钥更新到节点里
				zk.updateZnode(OPT_ZK_SECRET_KEY_ZNODE, encryptionSecretKey(newSecretKey));
				return true;
			}
		}
		return false;
	}
	/**
	 * <p>Title: encryptionSecretKey</p>  
	 * Description: <pre>对密钥进行加密</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param secretKey 密钥
	 * @return String 加密字符串
	 */
	private String encryptionSecretKey(String secretKey) {
		String encryptionSecretKey = EncryptUtils.toMD5Str(secretKey);
		return encryptionSecretKey;
	}
	/**
	 * 
	 * <p>Title: isTrueSecretKey</p>  
	 * Description: <pre>判断密钥是否正确</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月19日  
	 * @param secretKey 密钥
	 * @return boolen  true表示正确
	 */
	private boolean isTrueSecretKey(String secretKey) {
		String md5SecretKey = zk.getZnodeData(OPT_ZK_SECRET_KEY_ZNODE);
		return md5SecretKey.equals(encryptionSecretKey(secretKey));
	}


}
