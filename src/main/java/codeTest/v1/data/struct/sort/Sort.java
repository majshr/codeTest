package codeTest.v1.data.struct.sort;

import java.util.Random;

public class Sort {
	
	/**
	 * 快速排序
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
	 * 三路快排, 讲数组分成了三部分, 小于V, 等于V, 大于V; 排序仅需要排序两边的元素即可
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
		 *  以baseIndex=1, baseVal4为基准, lt=2指向7, gt=末尾指向len; 此时lt之前的都比base小, gt之后都比基准大; 从index=1向后遍历(即i)
		 *  	index=1, val=7, val > baseVal, 移向末尾, 和9交换, 4, 9, 5, 2, 7 ; gt--前移一位
		 *  	index=1, val=9, val > baseVal, 移向末尾, 和2交换, 4, 2, 5, 9, 7 ; gt--前移一位
		 *  	index=1, val=2; val < baseVal, 移向前边, 和2交换, 4, 2, 5, 9, 7 ; lt++后移一位, index++后移一位
		 *  	index=2, val=5, val > baseVal, 移向末尾,  4, 2, 5, 9, 7 gt--前移一位, 此时index = gt了
		 *  	一次遍历完成, 此时baseIndex和lt下标交换
		 *  
		 */
		int baseVal = arr[l];
		int lt = l + 1; 
		int gt = r + 1; 
		int index = l + 1; 
		
		// gt指向len, 所以gt-1为最会一个元素, i可以等于gt-1
		while(index < gt){
			// 当前值比baseVal小, 在i左侧
			if(arr[index] < baseVal){
				change(arr, index, lt);
				lt++;
				index++;
			}
			// 当前值比baseVal大, 在i右侧
			else if(arr[index] > baseVal){
				change(arr, index, gt - 1);
				gt--;
			}
			else{
				index++;
			}
		}
		// 将基准值放到找到的位置
		change(arr, l, lt - 1);
		quickSortFindThreeWays(arr, l, lt - 2);
		quickSortFindThreeWays(arr, gt, r);
	}
	 
	/**
	 * 存在大量重复元素的情况
	 * @param arr
	 * @param left
	 * @param right
	 */
	public static void quickSortFind(int arr[], int left, int right){
		if(left >= right){
			return;
		}
		
		//随机选择一个元素作为基准值
		int randIndex = new Random().nextInt(right - left + 1) + left;
		// 将该元素与第一个位置交换即可
		change(arr, left, randIndex);
		
		// 找出基准值位置, 使左边值都比他小, 右边值都比他大, 再对基准值左右两边递归操作
		//[left +1, start]<=baseVal, []
		int start = left + 1; //start为基准值下一位
		int end = right; // end为数组末尾
		int val = arr[left];
		while(start < end){
			/**存在大量相同元素, 让相同元素参与进交换, 使相同元素分布在左右两边*/
			/**如果全是重复元素, start没办法向右移动, end没办法向左移动了, 需要交换完元素, start右移, end左移*/
			// 从右往前找出比基准值小的元素(大于基准值的在右侧)
			while(arr[end] > val && start < end){
				end--;
			}
			// 从左往右找出比基准值大的元素(小于基准值的在左侧)
			while(arr[start] < val && start < end){
				start++;
			}
			
			// 交换
			change(arr, start, end);
			
			start++; end--;
		}
		
		// start == end为退出循环的条件,  此时start就是找到的正确的位置, 
		// (1)但是可能基准值就是当前数组最小值, 如 [1 3 2 4]; 此时start == end ==1, 但是不需要交换值
		// (2)可能基准值就是当前数组最大值, 且有多个, 此时也需要更新基准值位置, 如[17, 0, 17, 17] 
		// 找到下标3的位置, 符合条件, 如果baseIndex不标记新位置3, baseIndex右边的元素, 0就比baseIndex小了 
		int baseIndex = left;
		if(start == end && arr[start] <= val){
			change(arr, left, start);
			baseIndex = start;
		}
		// 如果start>end 为退出条件, 如[4, 5, 2], 
		// start=1, end=2, 交换位置之后, start++, end--, 此时start--为正确的位置
		if(start > end && arr[--start] <= val){
			change(arr, left, start);
			baseIndex = start;
		}
		
		quickSortFind(arr, left, baseIndex - 1);
		quickSortFind(arr, baseIndex + 1, right);
	}
	
	/**
	 * 与基准值相同元素存在于基准值的一边
	 * @param arr
	 * @param left
	 * @param right
	 */
	public static void quickSortFind2(int arr[], int left, int right){
		if(left >= right){
			return;
		}
		
		// 随机选择一个元素作为基准值
//		int randIndex = new Random().nextInt(right - left + 1) + left;
//		if(randIndex < left || randIndex > right){
//			System.out.println(left + " " + randIndex + " " + right);
//		}
//		// 将该元素与第一个位置交换即可
//		change(arr, left, randIndex);
		
		// 找出基准值位置, 使左边值都比他小, 右边值都比他大, 再对基准值左右两边递归操作
		int start = left + 1; //start为基准值下一位
		int end = right; // end为数组末尾
		int val = arr[left];
		while(start < end){
			// 从右往前找出比基准值小的元素
			while(arr[end] > val && start < end){
				end--;
			}
			// 从左往右找出比基准值大的元素(小于等于基准值的在左侧)
			while(arr[start] <= val && start < end){
				start++;
			}
			
			// 交换
			change(arr, start, end);
		}
		
		// start == end为退出循环的条件,  此时start就是找到的正确的位置, 
		// (1)但是可能基准值就是当前数组最小值, 如 [1 3 2 4]; 此时start == end ==1, 但是不需要交换值
		// (2)可能基准值就是当前数组最大值, 且有多个, 此时也需要更新基准值位置, 如[17, 0, 17, 17] 
		// 找到下标3的位置, 符合条件, 如果baseIndex不标记新位置3, baseIndex右边的元素, 0就比baseIndex小了 
		int baseIndex = left;
		if(start == end && arr[start] <= val){
			change(arr, left, start);
			baseIndex = start;
		}
		
		quickSortFind2(arr, left, baseIndex - 1);
		quickSortFind2(arr, baseIndex + 1, right);
	}
	
	/**
	 * 快速排序
	 * @param a
	 * @param left
	 * @param right
	 */
	public static void quickSortFind3(int[] a, int left, int right) {
		if(left >= right) {
			return;
		}
		
		// 随机选择一个元素作为基准值
		int randIndex = new Random().nextInt(right - left + 1) + left;
		if(randIndex < left || randIndex > right){
			System.out.println(left + " " + randIndex + " " + right);
		}
		// 将随机元素与第一个位置交换即可
		change(a, left, randIndex);
		
		//选取左边第一个数为基数
		int val = a[left];
		//基数的下标
		int valIndex = left;
		int l = left;
		int r = right;
		while(left < right) {
			//(1)找比基数小的数(可能第一个基数就是最小的数, 所以考虑边界情况)
			while(right >= 0 && a[right] > val) {
				right--;
			}
			//(2)第一个条件在前边, 因为如果left == a.length时, 判断less(), 会有数组越界异常
			//可能基数是最大的数, 所以考虑边界情况
			while(left < a.length && a[left] <= val) {
				left++;
			}
			//(3)如果是正常的left  和   right  交换两个位置的数
			if(left < right) {
				change(a, left, right);
			}
		}
		//将基数放到正确的位置(right为正确位置)
		change(a, valIndex, right);
		
		//排好基数后, 在对左边的数组和右边的数组进行基数查找排序
		quickSortFind3(a, l, right - 1);
		quickSortFind3(a, right + 1, r);
	}
	
	/**
	 * 交换数组两个位置元素
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
	 * 归并排序
	 * 讲述组不断分隔为小数组, 最后合并
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
	 * 排序数组, 不断拆分数组, 直到讲述组拆分为单个, 开始合并数组
	 * @param arr
	 * @param left 数组左边界, 闭区间
	 * @param right 数组右边界, 闭区间
	 */
	public static void mergeSort(int arr[], int left, int right, int[] temp){
		// 如果right == left, 数组区间只有一个元素, 不需要排序了
		if(right > left){
			int mid = (right - left) / 2 + left;
			mergeSort(arr, left, mid, temp); 
			mergeSort(arr, mid + 1, right, temp);
			mergeArrs(arr, left, mid, right, temp);
		}
	}
	
	/**
	 * 两个有序数组合并为有序数组
	 * @param arr
	 * @param left
	 * @param mid
	 * @param right
	 * @param temp
	 */
	public static void mergeArrs(int arr[], int left, int mid, int right, int[] temp){
		int start = left;
		int end = right;
		int first = left; // 左边一段
		int second = mid + 1; // 右边一段
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
		
		// temp中存储的是合并好, 排好序的元素, 复制到数组中
		index = 0;
		while(start <= end){
			arr[start++] = temp[index++];
		}
	}
	
	public static void mergeSort(int[] arr){
		// 临时存储数据数组
		int[] temp = new int[arr.length];
		
		// len表示归并子数组长度, 1表示一个一个的归并, 归并后程度为2; 2表示两个两个的归并; 归并后长度为4, 一次类推
		for(int len = 1; len < arr.length; len = 2 * len){
			// 按照长度len归并数组, 归并后长度翻倍
			for(int start = 0; start < arr.length; start = start + 2 * len){
				/**************两个数组进行归并一次的操作**********/
				// 归并的中间值
				int mid = start + len - 1;
				// 两个数组合并, 数组长度为2*len; 对于数组长度不满足2的x次幂的数组，end可能会大于数组长度
				// 并且mid可能会大于end
				int end = Math.min(start + 2 * len - 1, arr.length-1);
				
				// 如果mid大于end, 说明此时数组剩余元素都是归并左侧元素, 不需要合并了
				if(mid < end){
					mergeArrs(arr, start, mid, end, temp);	
				}
			}
		}
	}
	
	
	/**
	 * 插入排序
	 * @param arr
	 * @param len
	 */
	public static void insertSort(int arr[], int len){
		if(len <= 1){
			return ;
		}
		for(int i = 1; i < len; i++){
			// i下标元素, 为需要插入到前边正确位置的元素
			int tmp = arr[i];
			
			int j = i - 1;
			for(; j >= 0 ; j--){
				if(tmp > arr[j]){
					// 向后移动一位
					arr[j + 1] = arr[j];
				}else{
					break;
				}
			}
			arr[j + 1] = tmp;
		}
	}
	
	/**
	 * 希尔排序
	 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；
	 * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
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
			// 将整个数组分为gap个小数组
			for(int i = 0; i < gap; i++){
				// 对每个小数组进行归并排序
				for(int j = i + gap; j < len; j += gap){
					// 记录当前元素
					int tmp = arr[j];
					
					// 遍历前边的元素
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
	 * 希尔排序
	 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；
	 * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
	 * @param arr
	 * @param len
	 */
	public static void xiErSortUpgraded(int arr[], int len){
		int gap = len / 2;
		while(gap >= 1){
			/**从gap位开始, 后边的元素都是需要向前查找自身位置, 插入的; 
			 * 不必刻意找gap分出来的数组, 只要找到位置, 向前-gap, 查找自己的插入位置即可*/
			// 从第gap位开始, 有需要排序的元素
			for(int i = gap; i < len; i++){
				int tmp = arr[i];
				
				// 直接插入排序, 向前查找合适的位置
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
	 * 两个有序数组合并为有序数组, 此时查找逆序对
	 */
	public static void mergeArrsFind(int arr[], int left, int mid, int right, int[] temp) {
		int start = left;
		int end = right;
		int first = left; // 左边一段
		int second = mid + 1; // 右边一段
		int index = 0;
		for (; first <= mid && second <= right;) {
			if (arr[first] <= arr[second]) {
				temp[index++] = arr[first++];
			} else {
				temp[index++] = arr[second++];
				/** 如果左边比右边大, 就是一个; 因为是排好序的, 左边的剩余的也比右边当前元素大, 也加进去
				 * 之后右边小的元素归并到新数组, 而和此小元素符合的已都找到
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

		// temp中存储的是合并好, 排好序的元素, 复制到数组中
		index = 0;
		while (start <= end) {
			arr[start++] = temp[index++];
		}
	}
	
}
