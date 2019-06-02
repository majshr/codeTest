package codeTest.v1.fuzaijunheng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Test {
	public static HashMap<String, SmoothWeightServer> serverMap;
	public static int allWeight;
	static{
		/***
		 * * 服务器目前的权重，开始0，之后动态调整。当请求到来，选取服务器时，会遍历数组中所有服务器，对每个服务器
	 * 让它的current_weight增加它的weight，同时累加所有服务器的weight，并保存total。
		 */
		serverMap = 
				new HashMap<String, SmoothWeightServer>();
		serverMap.put("www.xttblog.com", new SmoothWeightServer(1, 0, "www.xttblog.com"));
		serverMap.put("公众号业余草", new SmoothWeightServer(2, 0, "公众号业余草"));
		serverMap.put("业余草微信号：xmtxtt", new SmoothWeightServer(4, 0, "业余草微信号：xmtxtt"));
		allWeight = 5 + 1 + 1;
	}
	
	public static String go(){
		// （1）所有currentWeight加上自身weight，，
		for(Map.Entry<String, SmoothWeightServer> item : serverMap.entrySet()){
			SmoothWeightServer currentServer = item.getValue();
			currentServer.currentWeight = currentServer.currentWeight + currentServer.weight;
		}
		
		// （2）选出最大的currentWeight，最为服务器，找出最大权重值
		SmoothWeightServer maxWeightServer = null;
		for(Map.Entry<String, SmoothWeightServer> item : serverMap.entrySet()){
			SmoothWeightServer currentServer = item.getValue();
			if(maxWeightServer == null || currentServer.currentWeight > maxWeightServer.currentWeight){
				maxWeightServer = currentServer;
			}
			
		}
		
		// （3）然后将这个最大的currentWeight减去总的weight
		maxWeightServer.currentWeight = maxWeightServer.currentWeight - allWeight;
		
		return maxWeightServer.ip;
	}
	
	/**三个网址*/
	public static List<String> list = null;
	static{
		list = new ArrayList<>();
		list.add("www.abc.com");
		list.add("业余草");
		list.add("xxxxx");
	}
	public static String goHash(String client){
		/**每个网址有二十个节点*/
		int nodeCount = 20;
		// （1）存储设置网站IP的hash值
		TreeMap<Integer, String> treeMap = new TreeMap<>();
		for(String s : list){
			for(int i = 0; i < nodeCount; i++){
				treeMap.put((s + "--业余草---" + i).hashCode(), s);
			}
		}
		
		// （2）根据客户端IP hash值，寻找第一个比他大的服务器的地址，获取即可 
		int clientHash = client.hashCode();
		SortedMap<Integer, String> subMap = treeMap.tailMap(clientHash);
		Integer firstHash;
		if(subMap.size() > 0){
			firstHash = subMap.firstKey();
		}else{
			firstHash = treeMap.firstKey();
		}
		
		return treeMap.get(firstHash);
	}
	
	
	public static void main(String[] args) {
		for(int i = 0; i < 7; i++){
			System.out.println(go());
		}
	}
}


class SmoothWeightServer{
	/**
	 * 服务器权重，固定不变
	 */
	int weight;
	/**
	 * 服务器目前的权重，开始0，之后动态调整。当请求到来，选取服务器时，会遍历数组中所有服务器，对每个服务器
	 * 让它的current_weight增加它的weight，同时累加所有服务器的weight，并保存total。
	 * 遍历完所有服务器之后，如果该服务器的current_weight是最大的，就选择这个服务器处理本次请求
	 * 最后把该服务器的current_weight减去total
	 */
	int currentWeight;
	String ip;
	
	public SmoothWeightServer(int weight, int currentWeight, String ip) {
		super();
		this.weight = weight;
		this.currentWeight = currentWeight;
		this.ip = ip;
	}
}


