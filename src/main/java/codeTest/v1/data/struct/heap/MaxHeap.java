package codeTest.v1.data.struct.heap;

import codeTest.v1.data.struct.Utils;
import codeTest.v1.data.struct.sort.SortHelper;
/**
 * �󶥶�
 */
public class MaxHeap {
	/**������*/
	private int[] data;
	/**�������ݸ���*/
	private int count;
	
	/**************�նѽ��в�����Ƴ�Ԫ��****************/
	/**
	 * ��ʼ������һ���ն�
	 * @param capacity
	 */
	public MaxHeap(int capacity){
		data = new int[capacity];
		count = 0; // ��ʼ����Ԫ�ظ���Ϊ0
	}
	
	/**
	 * ����Ԫ��
	 * @param val
	 */
	public void insert(int val){
		if(count == data.length){
			throw new RuntimeException();
		}
		
		// �Ƚ�Ԫ�ز��뵽ĩβ
		data[count] = val;
		// ����ð������λ��
		shiftUp(count);
		
		count++;
	}
	
	/**
	 * ��k���λ�õ�Ԫ�س��������ƶ�, ��ά�ֶѵĶ���
	 * @param k
	 */
	private void shiftUp(int k){
		// k�ĸ��ڵ�λ��Ϊ(k-1)/2,   k=0, ����Ҫ�����жϺ��ƶ���
		while(k > 0 && data[(k-1)/2] < data[k]){
			// ������ڸ��ڵ�, ����λ��
			Utils.change(data, (k - 1) / 2, k);
			k = (k - 1) / 2;
		}
	}
	
	/**
	 * ȡ����Ԫ��(ֻ��ȡ���Ѷ�Ԫ��)
	 */
	public int extractMax(){
		if(count == 0){
			throw new RuntimeException("����û��Ԫ��, ����ȡ��");
 		}
		
		// ȡ���±�Ϊ0��Ԫ��
		int ret = data[0];
		
		// �������һ��Ԫ���±�Ϊcount-1, ���һ��Ԫ���Ƶ���λ
		data[0] = data[count - 1];
		data[count - 1] = -1; // ���Ԫ��λ�õ�ֵ
		// ����Ԫ�ؼ���һ��
		count--;
		// ��Ԫ������ð������λ��
		shiftDown(0);
		
		return ret;
	}
	
	/**
	 * ���±�iԪ�������ƶ�������λ��
	 * @param i
	 */
	private void shiftDown(int i) {
		// �ҳ�i�ڵ�������ӽڵ�Ƚϵ����ֵ
		int left = i * 2 + 1, right = i * 2 + 2;
		// ������ڵ�&&��ڵ�ϴ�  || �����ҽڵ�&&�ҽڵ�ϴ�
		while((left < count && data[left] > data[i]) || 
				(right < count && data[right] > data[i])){
			// ����Ҳ��ӽڵ����, ���Ҳ��ӽڵ�ϴ�, ���Ҳ��ӽڵ㽻��
			if(right < count && data[left] < data[right]){
				Utils.change(data, i, right);
				i = right;
			}else{ //����, ������ӽڵ㽻��
				Utils.change(data, i, left);
				i = left;
			}
			
			// ������һ�ֽ���
			left = i * 2 + 1; right = i * 2 + 2;
		}
		
	}
	
	/**********************����������ת��Ϊ��***************************/
	/**
	 * ������ת��Ϊ��, �ӵ�һ����Ҷ�ӽڵ㿪ʼ, ������֮ǰ�����з�Ҷ�ӽڵ�, ��down����
	 * @param arr
	 */
	public MaxHeap(int[] arr){
		data = arr;
		count = arr.length;
		
		// �ӵ�һ������Ҷ�ӽڵ�Ľڵ㿪ʼ, ���������Ƶ��Լ���λ��; Ȼ����ǰ�ߵķ�Ҷ�ӽڵ�, ��ͬ������
		// ��һ����Ҷ�ӽڵ��� (arr.length - 1) / 2
		for(int i = (arr.length - 1) / 2; i >= 0; i--){
			shiftDown(i);
		}
	}
	
	/*********************���ô󶥶ѽ�������, �����С����**********************/
	/**
	 * ������������
	 * @param arr
	 */
	public static void heapSort(int[] arr){
		//�������ɶѷ�ʽ
		/**���췽����ʵ��*/
		MaxHeap heap = new MaxHeap(arr);
		
		// �Ӷ���ȡ��, ��ȡ�������ֵһ�ηŵ�����ĩβ, �õ��ĵ�������ǴӴ�С�ź����
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

































