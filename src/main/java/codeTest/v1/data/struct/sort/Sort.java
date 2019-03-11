package codeTest.v1.data.struct.sort;

import java.util.Random;

public class Sort {
	
	/**
	 * ��������
	 * @param arr
	 * @param len
	 */
	public static void quickSort(int arr[]){
//		quickSortFind(arr, 0, arr.length - 1);
//		quickSortFind2(arr, 0, arr.length - 1);
//		quickSortFind3(arr, 0, arr.length - 1);
		quickSortFindThreeWays(arr, 0, arr.length - 1);
	}
	
	/**
	 * ��·����, ������ֳ���������, С��V, ����V, ����V; �������Ҫ�������ߵ�Ԫ�ؼ���
	 * @param arr
	 * @param l
	 * @param r
	 */
	public static void quickSortFindThreeWays(int arr[], int l, int r){
		if(r - l <= 1){
			return;
		}
		
		// 4, 7, 5, 2, 9
		/*
		 *  ��baseIndex=1, baseVal4Ϊ��׼, lt=2ָ��7, gt=ĩβָ��len; ��ʱlt֮ǰ�Ķ���baseС, gt֮�󶼱Ȼ�׼��; ��index=1������(��i)
		 *  	index=1, val=7, val > baseVal, ����ĩβ, ��9����, 4, 9, 5, 2, 7 ; gt--ǰ��һλ
		 *  	index=1, val=9, val > baseVal, ����ĩβ, ��2����, 4, 2, 5, 9, 7 ; gt--ǰ��һλ
		 *  	index=1, val=2; val < baseVal, ����ǰ��, ��2����, 4, 2, 5, 9, 7 ; lt++����һλ, index++����һλ
		 *  	index=2, val=5, val > baseVal, ����ĩβ,  4, 2, 5, 9, 7 gt--ǰ��һλ, ��ʱindex = gt��
		 *  	һ�α������, ��ʱbaseIndex��lt�±꽻��
		 *  
		 */
		int baseVal = arr[l];
		int lt = l + 1; 
		int gt = r + 1; 
		int index = l + 1; 
		
		// gtָ��len, ����gt-1Ϊ���һ��Ԫ��, i���Ե���gt-1
		while(index < gt){
			// ��ǰֵ��baseValС, ��i���
			if(arr[index] < baseVal){
				change(arr, index, lt);
				lt++;
				index++;
			}
			// ��ǰֵ��baseVal��, ��i�Ҳ�
			else if(arr[index] > baseVal){
				change(arr, index, gt - 1);
				gt--;
			}
			else{
				index++;
			}
		}
		// ����׼ֵ�ŵ��ҵ���λ��
		change(arr, l, lt - 1);
		quickSortFindThreeWays(arr, l, lt - 2);
		quickSortFindThreeWays(arr, gt, r);
	}
	 
	/**
	 * ���ڴ����ظ�Ԫ�ص����
	 * @param arr
	 * @param left
	 * @param right
	 */
	public static void quickSortFind(int arr[], int left, int right){
		if(left >= right){
			return;
		}
		
		//���ѡ��һ��Ԫ����Ϊ��׼ֵ
		int randIndex = new Random().nextInt(right - left + 1) + left;
		// ����Ԫ�����һ��λ�ý�������
		change(arr, left, randIndex);
		
		// �ҳ���׼ֵλ��, ʹ���ֵ������С, �ұ�ֵ��������, �ٶԻ�׼ֵ�������ߵݹ����
		//[left +1, start]<=baseVal, []
		int start = left + 1; //startΪ��׼ֵ��һλ
		int end = right; // endΪ����ĩβ
		int val = arr[left];
		while(start < end){
			/**���ڴ�����ͬԪ��, ����ͬԪ�ز��������, ʹ��ͬԪ�طֲ�����������*/
			/**���ȫ���ظ�Ԫ��, startû�취�����ƶ�, endû�취�����ƶ���, ��Ҫ������Ԫ��, start����, end����*/
			// ������ǰ�ҳ��Ȼ�׼ֵС��Ԫ��(���ڻ�׼ֵ�����Ҳ�)
			while(arr[end] > val && start < end){
				end--;
			}
			// ���������ҳ��Ȼ�׼ֵ���Ԫ��(С�ڻ�׼ֵ�������)
			while(arr[start] < val && start < end){
				start++;
			}
			
			// ����
			change(arr, start, end);
			
			start++; end--;
		}
		
		// start == endΪ�˳�ѭ��������,  ��ʱstart�����ҵ�����ȷ��λ��, 
		// (1)���ǿ��ܻ�׼ֵ���ǵ�ǰ������Сֵ, �� [1 3 2 4]; ��ʱstart == end ==1, ���ǲ���Ҫ����ֵ
		// (2)���ܻ�׼ֵ���ǵ�ǰ�������ֵ, ���ж��, ��ʱҲ��Ҫ���»�׼ֵλ��, ��[17, 0, 17, 17] 
		// �ҵ��±�3��λ��, ��������, ���baseIndex�������λ��3, baseIndex�ұߵ�Ԫ��, 0�ͱ�baseIndexС�� 
		int baseIndex = left;
		if(start == end && arr[start] <= val){
			change(arr, left, start);
			baseIndex = start;
		}
		// ���start>end Ϊ�˳�����, ��[4, 5, 2], 
		// start=1, end=2, ����λ��֮��, start++, end--, ��ʱstart--Ϊ��ȷ��λ��
		if(start > end && arr[--start] <= val){
			change(arr, left, start);
			baseIndex = start;
		}
		
		quickSortFind(arr, left, baseIndex - 1);
		quickSortFind(arr, baseIndex + 1, right);
	}
	
	/**
	 * ���׼ֵ��ͬԪ�ش����ڻ�׼ֵ��һ��
	 * @param arr
	 * @param left
	 * @param right
	 */
	public static void quickSortFind2(int arr[], int left, int right){
		if(left >= right){
			return;
		}
		
		// ���ѡ��һ��Ԫ����Ϊ��׼ֵ
//		int randIndex = new Random().nextInt(right - left + 1) + left;
//		if(randIndex < left || randIndex > right){
//			System.out.println(left + " " + randIndex + " " + right);
//		}
//		// ����Ԫ�����һ��λ�ý�������
//		change(arr, left, randIndex);
		
		// �ҳ���׼ֵλ��, ʹ���ֵ������С, �ұ�ֵ��������, �ٶԻ�׼ֵ�������ߵݹ����
		int start = left + 1; //startΪ��׼ֵ��һλ
		int end = right; // endΪ����ĩβ
		int val = arr[left];
		while(start < end){
			// ������ǰ�ҳ��Ȼ�׼ֵС��Ԫ��
			while(arr[end] > val && start < end){
				end--;
			}
			// ���������ҳ��Ȼ�׼ֵ���Ԫ��(С�ڵ��ڻ�׼ֵ�������)
			while(arr[start] <= val && start < end){
				start++;
			}
			
			// ����
			change(arr, start, end);
		}
		
		// start == endΪ�˳�ѭ��������,  ��ʱstart�����ҵ�����ȷ��λ��, 
		// (1)���ǿ��ܻ�׼ֵ���ǵ�ǰ������Сֵ, �� [1 3 2 4]; ��ʱstart == end ==1, ���ǲ���Ҫ����ֵ
		// (2)���ܻ�׼ֵ���ǵ�ǰ�������ֵ, ���ж��, ��ʱҲ��Ҫ���»�׼ֵλ��, ��[17, 0, 17, 17] 
		// �ҵ��±�3��λ��, ��������, ���baseIndex�������λ��3, baseIndex�ұߵ�Ԫ��, 0�ͱ�baseIndexС�� 
		int baseIndex = left;
		if(start == end && arr[start] <= val){
			change(arr, left, start);
			baseIndex = start;
		}
		
		quickSortFind2(arr, left, baseIndex - 1);
		quickSortFind2(arr, baseIndex + 1, right);
	}
	
	/**
	 * ��������
	 * @param a
	 * @param left
	 * @param right
	 */
	public static void quickSortFind3(int[] a, int left, int right) {
		if(left >= right) {
			return;
		}
		
		// ���ѡ��һ��Ԫ����Ϊ��׼ֵ
		int randIndex = new Random().nextInt(right - left + 1) + left;
		if(randIndex < left || randIndex > right){
			System.out.println(left + " " + randIndex + " " + right);
		}
		// �����Ԫ�����һ��λ�ý�������
		change(a, left, randIndex);
		
		//ѡȡ��ߵ�һ����Ϊ����
		int val = a[left];
		//�������±�
		int valIndex = left;
		int l = left;
		int r = right;
		while(left < right) {
			//(1)�ұȻ���С����(���ܵ�һ������������С����, ���Կ��Ǳ߽����)
			while(right >= 0 && a[right] > val) {
				right--;
			}
			//(2)��һ��������ǰ��, ��Ϊ���left == a.lengthʱ, �ж�less(), ��������Խ���쳣
			//���ܻ�����������, ���Կ��Ǳ߽����
			while(left < a.length && a[left] <= val) {
				left++;
			}
			//(3)�����������left  ��   right  ��������λ�õ���
			if(left < right) {
				change(a, left, right);
			}
		}
		//�������ŵ���ȷ��λ��(rightΪ��ȷλ��)
		change(a, valIndex, right);
		
		//�źû�����, �ڶ���ߵ�������ұߵ�������л�����������
		quickSortFind3(a, l, right - 1);
		quickSortFind3(a, right + 1, r);
	}
	
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
	
	
	/**
	 * �鲢����
	 * �����鲻�Ϸָ�ΪС����, ���ϲ�
	 * @param arr
	 * @param len
	 */
	public static void mergeSort(int arr[], int len){
		if(len <= 1){
			return;
		}
		
		int[] temp = new int[len];
		
		int left = 0, right = len - 1;
		mergeSort(arr, left, right, temp);
		
	}
	
	/**
	 * ��������, ���ϲ������, ֱ����������Ϊ����, ��ʼ�ϲ�����
	 * @param arr
	 * @param left ������߽�, ������
	 * @param right �����ұ߽�, ������
	 */
	public static void mergeSort(int arr[], int left, int right, int[] temp){
		// ���right == left, ��������ֻ��һ��Ԫ��, ����Ҫ������
		if(right > left){
			int mid = (right - left) / 2 + left;
			mergeSort(arr, left, mid, temp); 
			mergeSort(arr, mid + 1, right, temp);
			mergeArrs(arr, left, mid, right, temp);
		}
	}
	
	/**
	 * ������������ϲ�Ϊ��������
	 * @param arr
	 * @param left
	 * @param mid
	 * @param right
	 * @param temp
	 */
	public static void mergeArrs(int arr[], int left, int mid, int right, int[] temp){
		int start = left;
		int end = right;
		int first = left; // ���һ��
		int second = mid + 1; // �ұ�һ��
		int index = 0;
		for(; first <= mid && second <= right; ){
			if(arr[first] < arr[second]){
				temp[index++] = arr[first++];
			}else{
				temp[index++] = arr[second++];
			}
		}
		
		while(first <= mid){
			temp[index++] = arr[first++];
		}
		while(second <= right){
			temp[index++] = arr[second++];
		}
		
		// temp�д洢���Ǻϲ���, �ź����Ԫ��, ���Ƶ�������
		index = 0;
		while(start <= end){
			arr[start++] = temp[index++];
		}
	}
	
	public static void mergeSort(int[] arr){
		// ��ʱ�洢��������
		int[] temp = new int[arr.length];
		
		// len��ʾ�鲢�����鳤��, 1��ʾһ��һ���Ĺ鲢, �鲢��̶�Ϊ2; 2��ʾ���������Ĺ鲢; �鲢�󳤶�Ϊ4, һ������
		for(int len = 1; len < arr.length; len = 2 * len){
			// ���ճ���len�鲢����, �鲢�󳤶ȷ���
			for(int start = 0; start < arr.length; start = start + 2 * len){
				/**************����������й鲢һ�εĲ���**********/
				// �鲢���м�ֵ
				int mid = start + len - 1;
				// ��������ϲ�, ���鳤��Ϊ2*len; �������鳤�Ȳ�����2��x���ݵ����飬end���ܻ�������鳤��
				// ����mid���ܻ����end
				int end = Math.min(start + 2 * len - 1, arr.length-1);
				
				// ���mid����end, ˵����ʱ����ʣ��Ԫ�ض��ǹ鲢���Ԫ��, ����Ҫ�ϲ���
				if(mid < end){
					mergeArrs(arr, start, mid, end, temp);	
				}
			}
		}
	}
	
	
	/**
	 * ��������
	 * @param arr
	 * @param len
	 */
	public static void insertSort(int arr[], int len){
		if(len <= 1){
			return ;
		}
		for(int i = 1; i < len; i++){
			// i�±�Ԫ��, Ϊ��Ҫ���뵽ǰ����ȷλ�õ�Ԫ��
			int tmp = arr[i];
			
			int j = i - 1;
			for(; j >= 0 ; j--){
				if(tmp > arr[j]){
					// ����ƶ�һλ
					arr[j + 1] = arr[j];
				}else{
					break;
				}
			}
			arr[j + 1] = tmp;
		}
	}
	
	/**
	 * ϣ������
	 * ϣ�������ǰѼ�¼���±��һ���������飬��ÿ��ʹ��ֱ�Ӳ��������㷨����
	 * ���������𽥼��٣�ÿ������Ĺؼ���Խ��Խ�࣬����������1ʱ�������ļ�ǡ���ֳ�һ�飬�㷨����ֹ��
	 * @param arr
	 * @param len
	 */
	public static void xiErSort(int arr[], int len){
		if(len <= 1){
			return;
		}
		int gap = len / 2;
		//[98, 78, 58, 36, 77, 38]
		while(gap >= 1){
			// �����������Ϊgap��С����
			for(int i = 0; i < gap; i++){
				// ��ÿ��С������й鲢����
				for(int j = i + gap; j < len; j += gap){
					// ��¼��ǰԪ��
					int tmp = arr[j];
					
					// ����ǰ�ߵ�Ԫ��
					int pre = j - gap;
					for(; pre >= 0; pre = pre - gap){
						if(tmp > arr[pre]){
							arr[pre + gap] = arr[pre];
						}else{
							break;
						}
					}
					arr[pre + gap] = tmp;
				}
			}
			gap = gap / 2;
		}
		
	}
	
	/**
	 * ϣ������
	 * ϣ�������ǰѼ�¼���±��һ���������飬��ÿ��ʹ��ֱ�Ӳ��������㷨����
	 * ���������𽥼��٣�ÿ������Ĺؼ���Խ��Խ�࣬����������1ʱ�������ļ�ǡ���ֳ�һ�飬�㷨����ֹ��
	 * @param arr
	 * @param len
	 */
	public static void xiErSortUpgraded(int arr[], int len){
		int gap = len / 2;
		while(gap >= 1){
			/**��gapλ��ʼ, ��ߵ�Ԫ�ض�����Ҫ��ǰ��������λ��, �����; 
			 * ���ؿ�����gap�ֳ���������, ֻҪ�ҵ�λ��, ��ǰ-gap, �����Լ��Ĳ���λ�ü���*/
			// �ӵ�gapλ��ʼ, ����Ҫ�����Ԫ��
			for(int i = gap; i < len; i++){
				int tmp = arr[i];
				
				// ֱ�Ӳ�������, ��ǰ���Һ��ʵ�λ��
				int j = i - gap;
				for(; j >= 0; j = j - gap){
					if(tmp > arr[j]){
						arr[j + gap] = arr[j];
					}else{
						break;
					}
				}
				arr[j + gap] = tmp;
				
			}
			gap = gap / 2;
		}
	}
	
	public static int mergeFind(int[] arr){
		if(arr.length <= 1){
			return 0;
		}
		int[] temp = new int[arr.length + 1];
		mergeSortFind(arr, 0, arr.length - 1, temp);
		return temp[arr.length];
	}

	private static void mergeSortFind(int[] arr, int left, int right, int[] temp) {
		if(right > left){
			int mid = left + (right - left) / 2;
			mergeSortFind(arr, left, mid, temp);
			mergeSortFind(arr, mid + 1, right , temp);
			mergeArrsFind(arr, left, mid, right, temp);
		}
	}

	/**
	 * ������������ϲ�Ϊ��������, ��ʱ���������
	 */
	public static void mergeArrsFind(int arr[], int left, int mid, int right, int[] temp) {
		int start = left;
		int end = right;
		int first = left; // ���һ��
		int second = mid + 1; // �ұ�һ��
		int index = 0;
		for (; first <= mid && second <= right;) {
			if (arr[first] <= arr[second]) {
				temp[index++] = arr[first++];
			} else {
				temp[index++] = arr[second++];
				/** �����߱��ұߴ�, ����һ��; ��Ϊ���ź����, ��ߵ�ʣ���Ҳ���ұߵ�ǰԪ�ش�, Ҳ�ӽ�ȥ
				 * ֮���ұ�С��Ԫ�ع鲢��������, ���ʹ�СԪ�ط��ϵ��Ѷ��ҵ�
				 * */
				temp[temp.length - 1] += (mid - first + 1);
			}
		}
		
		while (first <= mid) {
			temp[index++] = arr[first++];
		}
		while (second <= right) {
			temp[index++] = arr[second++];
		}

		// temp�д洢���Ǻϲ���, �ź����Ԫ��, ���Ƶ�������
		index = 0;
		while (start <= end) {
			arr[start++] = temp[index++];
		}
	}
	
}
