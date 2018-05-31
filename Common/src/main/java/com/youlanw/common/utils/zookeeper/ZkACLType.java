package com.youlanw.common.utils.zookeeper;

import org.apache.zookeeper.ZooDefs;

/**
 * <p>Title: ZkDigestACL</p>  
 * Description: <pre></pre>   
 * @author wangchaoqun 
 * @date 2018年5月11日
 */
public enum ZkACLType {
	
	READ(ZooDefs.Perms.READ),
	WRITE(ZooDefs.Perms.WRITE),
	CREATE(ZooDefs.Perms.CREATE),
	DELETE(ZooDefs.Perms.DELETE),
	ADMIN(ZooDefs.Perms.ADMIN),
	ALL(ZooDefs.Perms.ALL);
	private int acl;
	private ZkACLType(int acl){
		this.acl = acl;
	}
	public int getAcl() {
		return acl;
	}
}
