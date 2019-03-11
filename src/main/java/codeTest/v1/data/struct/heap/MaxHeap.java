package codeTest.v1.data.struct.heap;

import codeTest.v1.data.struct.Utils;
import codeTest.v1.data.struct.sort.SortHelper;
/**
 * 大顶堆
 */
public class MaxHeap {
	/**堆数组*/
	private int[] data;
	/**堆中数据个数*/
	private int count;
	
	/**************空堆进行插入和移除元素****************/
	/**
	 * 初始化创建一个空堆
	 * @param capacity
	 */
	public MaxHeap(int capacity){
		data = new int[capacity];
		count = 0; // 初始堆中元素个数为0
	}
	
	/**
	 * 插入元素
	 * @param val
	 */
	public void insert(int val){
		if(count == data.length){
			throw new RuntimeException();
		}
		
		// 先将元素插入到末尾
		data[count] = val;
		// 向上冒到合适位置
		shiftUp(count);
		
		count++;
	}
	
	/**
	 * 将k这个位置的元素尝试向上移动, 来维持堆的定义
	 * @param k
	 */
	private void shiftUp(int k){
		// k的父节点位置为(k-1)/2,   k=0, 不需要向上判断和移动了
		while(k > 0 && data[(k-1)/2] < data[k]){
			// 如果大于父节点, 交换位置
			Utils.change(data, (k - 1) / 2, k);
			k = (k - 1) / 2;
		}
	}
	
	/**
	 * 取出的元素(只能取出堆顶元素)
	 */
	public int extractMax(){
		if(count == 0){
			throw new RuntimeException("堆中没有元素, 不能取出");
 		}
		
		// 取出下标为0的元素
		int ret = data[0];
		
		// 数组最后一个元素下标为count-1, 最后一个元素移到首位
		data[0] = data[count - 1];
		data[count - 1] = -1; // 清空元素位置的值
		// 数组元素减少一个
		count--;
		// 将元素向下冒到合适位置
		shiftDown(0);
		
		return ret;
	}
	
	/**
	 * 将下标i元素向下移动到合适位置
	 * @param i
	 */
	private void shiftDown(int i) {
		// 找出i节点和左右子节点比较的最大值
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
	
	/**********************将无序数组转换为堆***************************/
	/**
	 * 讲数组转换为堆, 从第一个非叶子节点开始, 对他和之前的所有非叶子节点, 组down操作
	 * @param arr
	 */
	public MaxHeap(int[] arr){
		data = arr;
		count = arr.length;
		
		// 从第一个不是叶子节点的节点开始, 将他向下移到自己的位置; 然后它前边的非叶子节点, 做同样操作
		// 第一个非叶子节点是 (arr.length - 1) / 2
		for(int i = (arr.length - 1) / 2; i >= 0; i--){
			shiftDown(i);
		}
	}
	
	/*********************利用大顶堆进行排序, 排序从小到大**********************/
	/**
	 * 利用最大堆排序
	 * @param arr
	 */
	public static void heapSort(int[] arr){
		//将数组变成堆方式
		/**构造方法中实现*/
		MaxHeap heap = new MaxHeap(arr);
		
		// 从堆中取出, 将取出的最大值一次放到数组末尾, 得到的的数组就是从大到小排好序的
		for(int i = arr.length - 1; i >= 0; i--){
			arr[i] = heap.extractMax();
		}
		
	}
	
	public int size(){
		return count;
	}
	public boolean isEmpty(){
		return count == 0;
	}
	
	public static void main(String[] args) {
		MaxHeap heap = new MaxHeap(6);
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

































