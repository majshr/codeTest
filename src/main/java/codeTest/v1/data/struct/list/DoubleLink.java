package codeTest.v1.data.struct.list;

public class DoubleLink<T> {
	/**
	 * 节点
	 */
	class Node<T>{
		Node<T> next;
		Node<T> previous;
		T value;
		
		public Node(Node<T> next, Node<T> previous, T value) {
			this.next = next;
			this.previous = previous;
			this.value = value;
		}
	}
	
	
	/**
	 * 链表头结点
	 */
	private Node<T> head;
	/**
	 * 链表数据长度
	 */
	private Integer count;
	
	/**
	 * 构造方法, 初始化链表头借点, 链表长度
	 */
	public DoubleLink() {
		head = new Node<T>(null, null, null);
		count = 0;
	}
	
	/**
	 * 返回节点数目
	 */
	public int size() {
	    return count;
	}
	
	/**
	 * 返回链表是否为空
	 */
	public boolean isEmpty() {
	    return count == 0;
	}
	
	/**
	 * 获取index下标的节点
	 */
	private Node<T> getNode(int index){
		if(index < 0 || index >= count) {
			throw new IndexOutOfBoundsException();
		}
		//index超过一半, 从后往前找; 没超过一半, 从前往后找
		Node<T> node = null;
		if(index <= count/2) {
			for(int i = 0; i <= index; ++i) {
				node = head.next;
			}
		}else {
			for(int i = 0; i <= count - index; ++i) {
				node = head.previous;
			}
		}
		return node;
		
	}
}


























