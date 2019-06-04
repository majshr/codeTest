package codeTest.v1.data.struct.list;

import java.util.ArrayList;
import java.util.List;

/**
 * 用数组表示的链表叫做静态链表
 * 未被使用的数组元素成为备用链表
 * 数组的第一个元素和最后一个元素做特殊处理, 不存储元素
 * 	数组第一个元素, 即下标为0的元素的cur存储备用链表第一个节点的下标; 
 * 	数组最后一个元素的cur存储第一个有数值的元素的下标, 相当于单链表头结点的作用, 如果链表为空指向下标0
 */
public class StaticList {
	/**
	 * 静态链表每个节点元素, 包含本身的值, 和下一个元素的游标
	 */
	class Node{
		int val;
		int cur; //cursor游标, 记录下一个元素位置
	}
	
	/**
	 * 存储静态链表的数组
	 */
	Node[] nodes;
	private static int MAX_LEN = 1000;
	int len = 0;
	public StaticList() {
		this(MAX_LEN);
	}
	
	/**
	 * 初始化时, (1)设置好备用链表数据(2)设置第一个元素下标指向备用链表数组(3)设置最后一个元素下标为0(4)设置数组大小
	 */
	public StaticList(int len) {
		this.MAX_LEN = len;
		nodes = new Node[len];
		for(int i = 0; i < len; i++) {
			nodes[i] = new Node();
		}
		//备用链表设置为指向下一个节点; 第一个元素指向备用链表, 就是指向下一个节点
		for(int i = 0; i < len -1; i++) {
			nodes[i].cur = i + 1;
		}
		//设置最有一个元素
		nodes[len - 1].cur = 0;
	}
	
	/**
	 * 在第index元素之前插入元素e
	 * @param val
	 * @param index     
	 */
	public void insert(int val, int index) {
		if(index < 1 || index > len + 1) {
			throw new RuntimeException("超出链表长度");
		}
		
		//获取空闲空间分量的下标
		int freeIndex = mollocNode();
		nodes[freeIndex].val = val;
		//找到第index个元素之前的位置
		int curIndex = nodes[MAX_LEN - 1].cur;//第一个元素下标
		//当达到index之前的位置时, curIndex之后就是该插入的位置
		for(int i = 1; i <= index - 1; i++) {
			curIndex = nodes[curIndex].cur;
		}
		//把新加入的元素的cur设置为当前位置元素的下一个元素下标
		nodes[freeIndex].cur = nodes[curIndex].cur;
		nodes[curIndex].cur = freeIndex;
		len++;
	}
	
	/**
	 * 从备用链表中, 获取一个节点, 作为插入的元素
	 */
	public int mollocNode() {
		//备用链表第一个元素作为开辟的节点
		int i = nodes[0].cur;
		
		//备用链表的第一个元素被用了, 下一个元素作为第一个元素了
		nodes[0].cur = nodes[i].cur;
		
		return i;
	}
	
	/**
	 * 删除下标index位置的元素
	 */
	public void delete(int index) {
		if(index < 1 || index > len + 1) {
			throw new RuntimeException("超出链表长度");
		}
		
		//第一个节点下标
		int curIndex = nodes[MAX_LEN - 1].cur;
		int preIndex = 0;
		//找到需要删除的位置的下标, 和需要删除的位置的前一个位置的下标
		for(int i = 1; i <= index - 1; i++) {
			preIndex = curIndex;
			curIndex = nodes[curIndex].cur;
		}
		nodes[preIndex].cur = nodes[curIndex].cur;//链表中删除元素
		
		//在备用链表中添加删除元素
		nodes[curIndex].cur = nodes[0].cur;
		nodes[0].cur = curIndex;
	}
	
	/**
	 * 获取链表长度
	 */
	public int length() {
		return len;
	}
	
	/**
	 * 打印链表
	 */
	public void printNodes() {
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < len; i++) {
			//不断获取元素
		}
	}
	
	public static void main(String[] args) {
		StaticList list = new StaticList(1000);
		list.insert(1, list.length() + 1);
	}
}






































































