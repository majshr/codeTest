package codeTest.v1.data.struct.graph;

import java.util.Iterator;

public interface GraphInterface {
	/**
	 * 插入一条边, 无向图
	 * @param m
	 * @param n
	 * @return
	 */
	boolean addEdgeNo(int m, int n);
	
	/**
	 * 插入一条边， 有向图
	 * @param m
	 * @param n
	 * @return
	 */
	boolean addEdge(int m, int n);
	
	/**
	 * 图节点数
	 * @return
	 */
	int size();
	
	/**
	 * 获取遍历顶点的所有边的迭代器
	 * @param v
	 * @return
	 */
	Iterator<Integer> getIterator(int v);
	
	void print();
}
