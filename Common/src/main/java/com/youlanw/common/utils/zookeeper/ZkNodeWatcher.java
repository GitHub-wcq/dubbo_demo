package com.youlanw.common.utils.zookeeper;
import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
public class ZkNodeWatcher implements Watcher{
	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
                System.out.println("Zookeeper session established");
            } else if (EventType.NodeCreated == event.getType()) {
                System.out.println("success create znode");

            } else if (EventType.NodeDataChanged == event.getType()) {
                System.out.println("success change znode: " + event.getPath());

            } else if (EventType.NodeDeleted == event.getType()) {
                System.out.println("success delete znode");

            } else if (EventType.NodeChildrenChanged == event.getType()) {
                System.out.println("NodeChildrenChanged");

            }

        }
	}
	
	public void subscribeChildChanges(String path, ZkClient zkClient) {
		zkClient.subscribeChildChanges(path, (parentPath, currentChilds) -> {
			System.out.println("parentPath：" + parentPath);  
            System.out.println("currentChilds：" + currentChilds); 
		});
	}

	public void subscribeDataChanges(String path, ZkClient zkClient) {
		zkClient.subscribeDataChanges(path, new IZkDataListener() {
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("节点数据更新事件 >> 节点为：" + dataPath + "，变更数据为：" + data);  
			}

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("节点数据删除事件 >> 删除的节点为：" + dataPath);  
			}
			
		});
	}

}
