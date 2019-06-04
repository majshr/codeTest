package effective.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class All {
	public static void main(String[] args) {
		
	}
}

/**
 * 6, 消除过期对象的引用
 */
class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INIT_CAPACITY = 16;

	public Stack() {
		elements = new Object[DEFAULT_INIT_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	/**
	 * 弹出元素
	 * 
	 * @return Object
	 * @date: 2019年2月28日 下午7:49:25
	 */
	public Object pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		Object val = elements[--size];
		// 如果不清空, 栈先生长, 在收缩, 从栈中弹出来的对象将不会被当做垃圾回收, 即使栈中不再引用, 也不会被回收
		elements[size] = null;
		return val;
	}

	/**
	 * 扩容
	 * 
	 * @date: 2019年2月28日 下午7:49:40
	 */
	public void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}

	/**
	 * 垃圾回收语言中, 内存泄漏(这种内存泄漏为"无意识对象保持"更为恰当)是很隐蔽的. 
	 * 如果一个对象引用被无意识保留, 垃圾回收机制不会处理这个对象和这个对象所引用的其他对象, 许多对象被排除在垃圾回收机制之外, 从而对性能造成巨大影响.
	 * 一般, 只要类是自己管理内存, 就应该警惕内存泄漏的问题, 一旦元素被释放, 元素中包含任何对象引用都应该被清空.
	 * 内存泄漏另一个常见来源: 缓存.
	 * 第三个常见来源: 监听器和其他回调
	 */
	
	/**
	 * 
	 */
	private static void weakHashMapTest1() {
	    List<WeakHashMap<byte[][], byte[][]>> weakmaps1 = new ArrayList<WeakHashMap<byte[][], byte[][]>>();  
	    List<WeakHashMap<byte[][], byte[][]>> weakmaps2 = new ArrayList<WeakHashMap<byte[][], byte[][]>>();  

	    /*************/
	    for (int i = 0; i < 1000; i++) {  
	    	// java默认内存大小是64M, 跑不了几步就会内存溢出, jvm并没有帮我们释放内存
	        WeakHashMap<byte[][], byte[][]> d = new WeakHashMap<byte[][], byte[][]>();  
	        d.put(new byte[1000][1000], new byte[1000][1000]);  
	        weakmaps1.add(d);  

	        System.gc();  

	        System.err.println(i);  
	    }  
	 
	    /*****************/
	    for (int i = 0; i < 1000; i++) {  
	    	// java默认内存大小是64M, 跑不了几步就会内存溢出, jvm并没有帮我们释放内存
	        WeakHashMap<byte[][], byte[][]> d = new WeakHashMap<byte[][], byte[][]>();  
	        d.put(new byte[1000][1000], new byte[1000][1000]);  
	        weakmaps2.add(d);  

	        System.gc();  
	        
	        System.err.println(i);  
	        // 在访问其中元素的时候, 才会帮我们释放内存
	        for (int j = 0; j < i; j++) {  
                System.err.println(j+  " size" + weakmaps2.get(j).size());  

            }  
	    }
	}
	
	public static class Main{
		public static void main(String[] args) {
			weakHashMapTest1();
		}
	}
}




















