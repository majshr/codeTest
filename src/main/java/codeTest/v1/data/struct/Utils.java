package codeTest.v1.data.struct;

public class Utils {
	/**
	 * ������������λ��Ԫ��
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
