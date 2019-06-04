package codeTest.v1.thread;

import java.util.concurrent.atomic.AtomicReference;

public class SafeQueueCas<T> {
	
	/**首位是默认的, 不允许删除*/
	private Node<T> head;
	
	/**原子方式更新对象*/
	private AtomicReference<Node> atomicRefEnd = null;
	
	public SafeQueueCas() {
		head = new Node();
		atomicRefEnd = new AtomicReference<Node>(head);
	}
	
	public void push(T item) {
		Node<T> node = new Node<T>();
		node.item = item;
		//
		for(;;) {
			//获取当前队尾的节点值
			Node t = atomicRefEnd.get();
			//插入节点的前节点设置为队尾
			node.pre = t;
			
			//线程安全的更新队尾节点, 原子操作 如果对为节点仍文t, 在将队尾设置为node, 
			//同一时刻只能cas修改成功, 其他的就更新失败了, 继续循环
			if(atomicRefEnd.compareAndSet(t, node)) {
				t.next = node;
			}
		}
	}
	
	/**从队尾取出并删除元素*/
	public T poll() {
		for(;;) {
			//获取当前值
			Node t = atomicRefEnd.get();
			//获取前一个, 移除当前值后的队尾元素
			Node pre = t.pre;
			
			if(t == head && pre == null) {
				if(atomicRefEnd.compareAndSet(head, head)) {
					return null;
				}
			}
			else {
				if(atomicRefEnd.compareAndSet(t, pre)) {
					T item = (T) t.item;
					t = null;
					return item;
				}
			}
		}
	}
	
/*	private Iterator<> iterator() {
		return null;
	}*/
	
}

class Node<T>{
	Node<T> next;
	Node<T> pre;
	T item;
}