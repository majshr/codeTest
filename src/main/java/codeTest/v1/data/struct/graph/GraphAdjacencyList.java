package codeTest.v1.data.struct.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 邻接表表示图
 * @author maj
 *
 */
public class GraphAdjacencyList implements GraphInterface{
	List<Integer>[] graph;
	
	public GraphAdjacencyList(int n) {
		graph = new List[n];
		for(int i = 0; i < n; i++){
			graph[i] = new ArrayList<>();
		}
	}

	@Override
	public boolean addEdgeNo(int m, int n) {
		if(!graph[m].contains(n)){
			graph[m].add(n);
			graph[n].add(m);
		}
		return true;
	}

	@Override
	public boolean addEdge(int m, int n) {
		if(!graph[m].contains(n)){
			graph[m].add(n);
		}
		return true;
	}

	@Override
	public int size() {
		return graph.length;
	}
	
	@Override
	public Iterator<Integer> getIterator(int v){
		return new MyIterator(v);
	}
	
	@Override
	public void print(){
		
	}
	
	/**
	 * 根据每个顶点，迭代所有的边
	 * @author maj
	 *
	 * @param <Integer>
	 */
	private class MyIterator implements Iterator<Integer>{

		int index; //访问到哪个下标了
		int v; //访问哪个元素（哪个边）
		
		public MyIterator(int v) {
			index = 0;
			this.v = v;
		}
		
		/**
		 * 是否有下一个元素
		 */
		@Override
		public boolean hasNext() {
			return graph[v].size() > index;
		}

		/**
		 * 获取下一个元素
		 */
		@Override
		public Integer next() {
			// 如果有相邻元素
			return graph[v].get(index++);
			
		}

	}
}















