package com.youlanw.UCM.Provider.Enum;

public enum InitZnodePath {

	/**
	 * 操作zookeeper节点必须携带密钥参数，该值是密钥存放的节点路径
	 */
	OPT_ZK_SECRET_KEY_ZNODE("/zk_secret_key"),
	/**
	 * C端的节点创建应有一个统一的初始化地址,该地址用来存放初始化地址的路径
	 */
	ZK_C_NODE("/youlanw/c"),
	/**
	 * 同上
	 */
	ZK_B_NODE("/youlanw/b");
	
	private String path;
	private InitZnodePath(String path) {
        this.path = path;
    }
	public String getValue() {
        return path;
    }
}
