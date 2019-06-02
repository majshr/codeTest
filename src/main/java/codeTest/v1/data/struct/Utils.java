package codeTest.v1.data.struct;

public class Utils {
	/**
	 * 交换两个元素
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static void change(int[] arr, int i, int j){
		if(i != j){
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;	
		}
	}
	
	
	
	public static void main(String[] args) {
		
	}
}
