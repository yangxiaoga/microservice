package com.ethan.zk.practice.base;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZKBase {
	static final String CONNECT_STRING = "192.168.1.12:2181";
	static final int SESSION_TIMEOUT = 10000;
	
	//异步连接，防止实例未创建成功就使用
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	public static void main(String[] args){
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {

				public void process(WatchedEvent event) {
					KeeperState state = event.getState(); //事件状态
					EventType  eventType = event.getType();//事件类型
					System.out.println(state.toString());
					if (state.equals(KeeperState.SyncConnected)) {
						if (EventType.None.equals(eventType)) {
							connectedSemaphore.countDown();
							System.out.println("zk connection established!");
						}
					}
				}
				
			});
			
			connectedSemaphore.await();//阻塞
			String result = zk.create("/testRoot", "tesRoot".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("结果： "+result);
			zk.delete("/testRoot", -1, new AsyncCallback.VoidCallback() {

				public void processResult(int rc, String path, Object ctx) {
					
				}
			},"a");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}
}
