package com.ethan.zk.practice.base;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

public class ZKClientBase {

	static final String CONNECT_STRING = "192.168.1.12:2181";
	static final int SESSION_TIMEOUT = 10000;
	
	public static void main(String[] args) {
		ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_STRING), 10000);
		
		zkc.createEphemeral("/temp"); //创建临时节点
		zkc.createPersistent("super/c1", true);//递归创建
		
		zkc.deleteRecursive("super");//递归删除
		
		zkc.writeData("super/c1", "新内容");
		zkc.exists("super/c1");
		zkc.subscribeChildChanges("/super", new IZkChildListener() {

			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println("parentPath:" + parentPath);
				System.out.println("currentChilds:" + currentChilds);
			}
		
		});
		
		zkc.subscribeDataChanges("/super", new IZkDataListener() {

			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("变更的节点为："+dataPath+" 变更内容为："+data);
			}

			public void handleDataDeleted(String dataPath) throws Exception {
					System.out.println("删除的节点为："+dataPath);
			}
			
		});
		
	}
}
