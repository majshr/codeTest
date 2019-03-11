package codeTest.v1.data.struct.heap;

import codeTest.v1.data.struct.Utils;
import codeTest.v1.data.struct.sort.SortHelper;
/**
 * 索引最大堆
 * @author maj
 *
 */
public class IndexMaxHeap {
	private int[] data;
	
	/**
	 * 存储值对应的索引
	 */
	private int[] indexs;
	
	private int count;
	/**
	 * 创建一个空堆
	 * @param capacity
	 */
	public IndexMaxHeap(int capacity){
		data = new int[capacity];
		indexs = new int[capacity];
		count = 0;
	}
	
	/**
	 * 讲数组转换为堆
	 * @param arr
	 */
	public IndexMaxHeap(int[] arr){
		data = arr;
		count = arr.length;
		
		// 从第一个不是叶子节点的节点开始
		// 第一个非叶子节点是 (arr.length - 1) / 2
		for(int i = (arr.length - 1) / 2; i >= 0; i--){
			shiftDown(i);
		}
	}
	
	public int size(){
		return count;
	}
	public boolean isEmpty(){
		return count == 0;
	}
	
	/**
	 * 取出的元素
	 */
	public int extractMax(){
		if(count == 0){
			throw new RuntimeException("堆中没有元素, 不能取出");
 		}
		
		// 取出下标为0的元素
		int ret = data[0];
		
		// 数组最后一个元素下标为count-1, 最后一个元素移到首位
		data[0] = data[count - 1];
		data[count - 1] = -1;
//		Utils.change(data, 0, count - 1);
		// 数组元素减少一个
		count--;
		
		shiftDown(0);
		
		return ret;
	}
	
	/**
	 * 将下标i元素向下移动到合适位置
	 * @param i
	 */
	private void shiftDown(int i) {
		
		// 找出i的最大值
		int left = i * 2 + 1, right = i * 2 + 2;
		// 存在左节点&&左节点较大  || 存在右节点&&右节点较大
		while((left < count && data[left] > data[i]) || 
				(right < count && data[right] > data[i])){
			// 如果右侧子节点存在, 且右侧子节点较大, 和右侧子节点交换
			if(right < count && data[left] < data[right]){
				Utils.change(data, i, right);
				i = right;
			}else{ //否则, 和左侧子节点交换
				Utils.change(data, i, left);
				i = left;
			}
			
			// 进行下一轮交换
			left = i * 2 + 1; right = i * 2 + 2;
		}
		
	}

	/**
	 * 在位置i插入元素val
	 * @param i
	 * @param val
	 */
	public void insert(int i, int val){
		if(count == data.length){
			throw new RuntimeException();
		}
		if(i >= data.length){
			throw new RuntimeException();
		}
		
		// 在下标i处添加元素
		data[i] = val;
		// index数组记录下标值
		indexs[count] = i;
		
		// 正常情况下, count和i是对应的, shiftUp操作count, 也就是操作i
		shiftUp(count);
		
		count++;
		
	}
	
	/**
	 * 将k这个位置的元素尝试向上移动, 来维持堆的定义
	 * @param k
	 */
	private void shiftUp(int k){
		// k的父节点位置为(k-1)/2,   k=0, 不需要向上判断和移动了
		// 需要通过索引数组找到位置
		while(k > 0 && data[indexs[(k-1)/2]] < data[indexs[k]]){
			// 交换的是indexs索引
			Utils.change(data, (k - 1) / 2, k);
			k = (k - 1) / 2;
		}
	}
	
	/**
	 * 修改元素值
	 * @param i
	 * @param val
	 */
	public void change(int i , int val){
		
	}
	
	/**
	 * 利用最大堆排序
	 * @param arr
	 */
	public static void heapSort(int[] arr){
		IndexMaxHeap heap = new IndexMaxHeap(arr);
		// 讲数组元素放到堆中(一个一个放进去)
//		for(int i = 0; i < arr.length; i++){
//			heap.insert(arr[i]);
//		}
		//有一种更好的方式, 将数组变成堆方式
		/**构造方法中实现*/
		// 从堆中取出, 就是从大到小排好序的
		for(int i = arr.length - 1; i >= 0; i--){
			arr[i] = heap.extractMax();
		}
		
	}
	
	public static void main(String[] args) {
		IndexMaxHeap heap = new IndexMaxHeap(6);
		heap.insert(100);
		heap.insert(0);
		heap.insert(1);
		heap.insert(10);
		heap.insert(8);
		heap.insert(7);
		SortHelper.printArr(heap.data);
		
		heap.extractMax();
        SortHelper.printArr(heap.data);
        
        heap.extractMax();
        SortHelper.printArr(heap.data);
        
        heap.extractMax();
        SortHelper.printArr(heap.data);
        
        heap.extractMax();
        SortHelper.printArr(heap.data);
        
        heap.extractMax();
        SortHelper.printArr(heap.data);
        
        heap.extractMax();
        SortHelper.printArr(heap.data);
        
	}
	
	
}

































