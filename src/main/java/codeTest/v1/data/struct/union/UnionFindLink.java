package codeTest.v1.data.struct.union;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 父节点方式实现的并查集(父节点由下标指定)
 * @author maj
 *
 */
public class UnionFindLink {
	int[] id;
	int count;
	
	int[] sz; // sz[i]  表示以i为根的集合中元素的个数
	
	int[] rank; // rank[i] 表示以i为根集合表示的树的高度
	
	public UnionFindLink(int n) {
		count = n;
		id = new int[n];
		sz = new int[n];
		rank = new int[n];
		for(int i = 0; i < n; i++){
			id[i] = i;
			
			sz[i] = 1;
			
			rank[i] = 1;
		}
	}
	
	/**
	 * 查找与p节点连接的值(一直找到父节点)
	 */
	public int find(int p){
		while(p != id[p]){
			// p存在父亲节点, 向下查找, 一直查找到父节点
			p = id[p];
		}
		
		return p;
	}
	
	/**
	 * 两个节点是否连接
	 * @param p
	 * @param q
	 * @return
	 */
	boolean isConnected(int p, int q){
		// 判断父节点是否相同
		return find(p) == find(q);
	}
	/*********************base*********************/
	/**
	 * 查找与p节点连接的值(一直到找到父节点)
	 */
	public int findBase(int p){
		while(p != id[p]){
			// p存在父亲节点, 向下查找, 一直查找到父节点
			p = id[p];
		}
		
		return p;
	}
	/**
	 * 连接p, q两个节点
	 * @param p
	 * @param q
	 */
	void unionelementsBase(int p, int q){
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot){
			return;
		}
		
		// 如果不是同一根, 设置根元素
		id[pRoot] = qRoot;
	}
	
	/**********************父节点子元素数优化, sz[]表示以index为根节点子元素个数*********************/
	/**
	 * 和基础版相同
	 * @param p
	 * @return
	 */
	public int findUpdSubCount(int p){
		while(p != id[p]){
			// p存在父亲节点, 向下查找, 一直查找到父节点
			p = id[p];
		}
		
		return p;
	}
	/**
	 * 将元素少的, 指向元素多的
	 * @param p
	 * @param q
	 */
	void unionelementsUpdSubCount(int p, int q){
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot){
			return;
		}
		
		// 如果不是同一根, 设置根元素, 将元素少的指向元素多的
		if(sz[pRoot] < sz[qRoot]){
			id[pRoot] = qRoot;
			sz[qRoot] += sz[pRoot];
		}else{
			id[qRoot] = pRoot;	
			sz[pRoot] += sz[qRoot];
		}
	}
	/********************根据树的高度优化, rank[]表示index下标元素的高度********************/
	/**
	 * 和基础版相同
	 * @param p
	 * @return
	 */
	public int findUpdRank(int p){
		while(p != id[p]){
			// p存在父亲节点, 向下查找, 一直查找到父节点
			p = id[p];
		}
		
		return p;
	}
	
	void unionelementsUpdRank(int p, int q){
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot){
			return;
		}
		
		// 矮的指向高的, 然后两者高度均不变
		if(rank[pRoot] < rank[qRoot]){
			id[pRoot] = qRoot;
			// rank保持不变
		}else if(rank[pRoot] > rank[qRoot]){
			id[qRoot] = pRoot;	
			// rank保持不变
		}else{
			// 两个高度相同的, 被指向的高度加个1
			// 让p根指向q根, 那么q节点高度增加了
			id[pRoot] = qRoot;
			rank[qRoot]++;
		}
	}
	
	/*****************路径压缩, 优化(1)使树高度降低(2)树高度降低为2, 在find方法处实现*******************/
	/**
	 * 和基础版相同
	 * @param p
	 * @return
	 */
	public int findUpdLevel(int p){ 
		// p指向节点不是根节点
		int parIndex = id[p];
		while(id[parIndex] != parIndex){
			// p指向父节点的父节点
			id[p] = id[parIndex];
			
			// 再对此时p新指向的父节点判断, 进行循环
			p = id[p];
			parIndex = id[p];
		}
		return parIndex;
	}
	/**
	 * 和基础版相同
	 * @param p
	 * @return
	 */
	public int findUpdLevelTwo(int p){ 
		// 如果不是根节点, 让当前节点指向根节点
		if(id[p] != p){
			// 从父节点向上, 查找根节点
			id[p] = findUpdLevelTwo(id[p]);
		}
		return id[p];
	}
	
	public static void main(String[] args) {
		UnionFindLink l = new UnionFindLink(5);
		l.id = new int[]{0, 0, 1, 2, 3};
		l.findUpdLevelTwo(4);
		System.out.println(l.id);
	}
	
}
























