package codeTest.v1.data.struct.graph;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 遍历图
 * @author maj
 *
 */
public class VisitGraph {
	/**
	 * 边是否被访问过
	 */
	boolean[] visited;
	/**
	 * 联通分量的数量（一个图可能有多个未连接的图组成）
	 */
	int count;
	
	GraphInterface graph;
	
	/**
	 * 深度优先遍历
	 * @param graph
	 */
	public VisitGraph(GraphInterface graph) {
		this.graph = graph;
		visited = new boolean[graph.size()];
		for(int i = 0; i < graph.size(); i++){
			visited[i] = false;
		}
		count = 0;
		
		// 遍历
		for(int i = 0; i < graph.size(); i++){
			// 如果i节点没有遍历到，从i开始，深度遍历
			if(!visited[i]){
				// 一次深度优先遍历，会将联通的顶点都遍历到，仍没有遍历到的节点就在另一个联通分量里
				dfs(i);
				count++;
			}
		}
	}
	
	/**
	 * 计算一个图联通分量的数量
	 * @return
	 */
	public int count(){
		return count;
	}
	
	/**
	 * 从 v节点开始，深度遍历
	 * @param v
	 */
	void dfs(int v){
		// 访问到了v节点
		visited[v] = true;
		System.out.println(v + " ");
		// 访问v连接的节点
		Iterator<Integer> iter = graph.getIterator(v);
		while(iter.hasNext()){
			int adjacentNode = iter.next();
			// 如果没有被访问过，对相邻节点进行深度遍历
			if(!visited[adjacentNode]){
				dfs(adjacentNode);	
			}
		}
	}
	
	
	/**
	 * 每访问一个节点，记录是从哪个节点过来的
	 */
	int[] from;
	private int v; // 寻址起点
	/**
	 * 求从v节点到任意节点的路径
	 * @param graph
	 * @param v
	 */
	public VisitGraph(GraphInterface graph, int v){
		//初始化算法
		this.graph = graph;
		visited = new boolean[graph.size()];
		from = new int[graph.size()];
		for(int i = 0; i < graph.size(); i++){
			visited[i] = false;
			from[i] = -1;
		}
		this.v = v;
		
		// 寻路算法
		dfsFindWay(v);
	}
	
	/**
	 * 是否连接到了w
	 * @param w
	 * @return
	 */
	boolean hasPath(int w){
		return visited[w];
	}
	
	/**
	 * 查找经过w的所有路径
	 * @param w
	 * @param paths
	 */
	void path(int w, List<Integer> paths){
		// 往回推路径
		Stack<Integer> s = new Stack<>();
		int p = w;
		while(p != -1){
			s.push(p);
			p = from[p]; 
		}
		
		paths.clear();
		while(!s.isEmpty()){
			paths.add(s.pop());
		}
	}
	
	void showPath(int w){
		
	}
	
	/**
	 * 从 v节点开始，深度遍历
	 * @param v
	 */
	void dfsFindWay(int v){
		// 访问到了v节点
		visited[v] = true;
		System.out.println(v + " ");
		// 访问v连接的节点
		Iterator<Integer> iter = graph.getIterator(v);
		// 路径经过的节点
		int index = 0;
		from[index] = v;
		while(iter.hasNext()){
			int adjacentNode = iter.next();
			// 如果没有被访问过，对相邻节点进行深度遍历
			if(!visited[adjacentNode]){
				// 没有访问过，就访问这个节点
				from[++index] = adjacentNode;
				dfsFindWay(adjacentNode);	
			}
		}
	}
}




















