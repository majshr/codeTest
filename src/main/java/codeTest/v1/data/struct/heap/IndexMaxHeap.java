package codeTest.v1.data.struct.heap;

import codeTest.v1.data.struct.Utils;
import codeTest.v1.data.struct.sort.SortHelper;
/**
 * ��������
 * @author maj
 *
 */
public class IndexMaxHeap {
	private int[] data;
	
	/**
	 * �洢ֵ��Ӧ������
	 */
	private int[] indexs;
	
	private int count;
	/**
	 * ����һ���ն�
	 * @param capacity
	 */
	public IndexMaxHeap(int capacity){
		data = new int[capacity];
		indexs = new int[capacity];
		count = 0;
	}
	
	/**
	 * ������ת��Ϊ��
	 * @param arr
	 */
	public IndexMaxHeap(int[] arr){
		data = arr;
		count = arr.length;
		
		// �ӵ�һ������Ҷ�ӽڵ�Ľڵ㿪ʼ
		// ��һ����Ҷ�ӽڵ��� (arr.length - 1) / 2
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
	 * ȡ����Ԫ��
	 */
	public int extractMax(){
		if(count == 0){
			throw new RuntimeException("����û��Ԫ��, ����ȡ��");
 		}
		
		// ȡ���±�Ϊ0��Ԫ��
		int ret = data[0];
		
		// �������һ��Ԫ���±�Ϊcount-1, ���һ��Ԫ���Ƶ���λ
		data[0] = data[count - 1];
		data[count - 1] = -1;
//		Utils.change(data, 0, count - 1);
		// ����Ԫ�ؼ���һ��
		count--;
		
		shiftDown(0);
		
		return ret;
	}
	
	/**
	 * ���±�iԪ�������ƶ�������λ��
	 * @param i
	 */
	private void shiftDown(int i) {
		
		// �ҳ�i�����ֵ
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

	/**
	 * ��λ��i����Ԫ��val
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
		
		// ���±�i�����Ԫ��
		data[i] = val;
		// index�����¼�±�ֵ
		indexs[count] = i;
		
		// ���������, count��i�Ƕ�Ӧ��, shiftUp����count, Ҳ���ǲ���i
		shiftUp(count);
		
		count++;
		
	}
	
	/**
	 * ��k���λ�õ�Ԫ�س��������ƶ�, ��ά�ֶѵĶ���
	 * @param k
	 */
	private void shiftUp(int k){
		// k�ĸ��ڵ�λ��Ϊ(k-1)/2,   k=0, ����Ҫ�����жϺ��ƶ���
		// ��Ҫͨ�����������ҵ�λ��
		while(k > 0 && data[indexs[(k-1)/2]] < data[indexs[k]]){
			// ��������indexs����
			Utils.change(data, (k - 1) / 2, k);
			k = (k - 1) / 2;
		}
	}
	
	/**
	 * �޸�Ԫ��ֵ
	 * @param i
	 * @param val
	 */
	public void change(int i , int val){
		
	}
	
	/**
	 * ������������
	 * @param arr
	 */
	public static void heapSort(int[] arr){
		IndexMaxHeap heap = new IndexMaxHeap(arr);
		// ������Ԫ�طŵ�����(һ��һ���Ž�ȥ)
//		for(int i = 0; i < arr.length; i++){
//			heap.insert(arr[i]);
//		}
		//��һ�ָ��õķ�ʽ, �������ɶѷ�ʽ
		/**���췽����ʵ��*/
		// �Ӷ���ȡ��, ���ǴӴ�С�ź����
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

































