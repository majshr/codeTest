package codeTest.v1.data.struct.sort;

import java.util.Arrays;

public class SortUtil {
	
	/************交换排序:交换排序的基本思想都为通过比较两个数的大小，当满足某些条件时对它进行交换从而达到排序的目的。*****************/
	/**
	 * 1,冒泡排序
	 * 思路:
	 * 比较相邻的两个数，如果前者比后者大，则进行交换。每一轮排序结束，选出一个未排序中最大的数放到数组后面。
	 * 最差时间复杂度为O(n^2),平均时间复杂度为O(n^2)。稳定性：稳定。辅助空间O(1)。
	 */
	public static void bubbleSort(Comparable[] a) {
		//每次将最大值替换到最后一位, 总共需要替换len - 1 次, 就排好序了
		for(int i = 1; i < a.length; i++) {
			//每次需要交换的位也会减少
			for(int j = 0; j < a.length - i; j++) {
				if(less(a[j], a[j + 1])) {
					change(a, j, j + 1);
				}
			}
		}
	}
	
	/**
	 * 快速排序
	 * 选一个基数, 找到基数正确的位置, 使基数左边的数都比它小, 基数右边的数都比它大,
	 * 然后对基数左边的数组, 基数右边的数组进行同样的操作
	 * 
	 * 选取下标left的元素为基数
	 * 选一个left指针, 一个right指针 
	 * while(left < right){
	 * 		rignt往左走, 比基数小时停止
	 * 		left指针往右走, 比基数大时停止
	 *      交换两个位置的元素
	 * }
	 *  
	 */
	public static void quickSort(Comparable[] a) {
		quickSortPaiXu(a, 0, a.length - 1);
		System.out.println();
	}
	
	public static void quickSortPaiXu(Comparable[] a, int left, int right) {
		if(left >= right) {
			return;
		}
		//选取左边第一个数为基数
		Comparable val = a[left];
		//基数的下标
		int valIndex = left;
		int l = left;
		int r = right;
		while(left < right) {
			//(1)找比基数小的数(可能第一个基数就是最小的数, 所以考虑边界情况)
			while(right >= 0 && less(a[right], val)) {
				right--;
			}
			//(2)第一个条件在前边, 因为如果left == a.length时, 判断less(), 会有数组越界异常
			//可能基数是最大的数, 所以考虑边界情况
			while(left < a.length && !less(a[left], val)) {
				left++;
			}
			//(3)如果是正常的left  和   right  交换两个位置的数
			if(left < right) {
				change(a, left, right);
			}
		}
		//将基数放到正确的位置(right为比)
		change(a, valIndex, right);
		
		//排好基数后, 在对左边的数组和右边的数组进行基数查找排序
		quickSortPaiXu(a, l, right - 1);
		quickSortPaiXu(a, right + 1, r);
	}
	
	
	/**
	 * 选择排序: 
	 *     默认第一个位置为最小元素位置, 向后遍历数组, 与当前最小元素比较, 根据结果, 记录最小元素位置, 
	 *     														遍历完毕后, 将最小元素与第一个位置元素交换; 
	 *     以第二个位置元素为最小元素, 再向后遍历 ...
	 * 特点:
	 *     当前索引的左边都是排好序的
	 *     运行时间与输入无关, 为了找到最小值而扫面一遍数组并不能为下一次扫描提供什么信息
	 */
	public static void selectSort(Comparable[] a) {
		//i记录每次遍历的第一个位置
		for(int i = 0; i < a.length; i++) {
			//默认将第i位做为最小元素, 从第i+1位开始查找最小
			int minIndex = i;
			for(int j = i + 1; j < a.length; j++) {
				//如果第j位数字更小, 记录j下标为最小
				if(!less(a[j], a[minIndex])) {
					minIndex = j;
				}
			}
			change(a, i, minIndex);
		}
	}
	
	/**
	 * 插入排序
	 * 	当前索引左边都是排好序的, 将当前位置插入到左边合适的位置; 遍历左边元素, 比当前元素大的右移
	 * 特点:
	 * 	当前索引的左边都是排好序的, 但他们的最终位置还不确定, 可能给更小的元素让位置, 右移
	 * 	所需时间取决于输入中元素的初始顺序, 对一个很大且有的元素已经有序, 回避对随机顺序的数组或是逆序数组进行排序快得多	
	 */
	public static void insertSort(Comparable[] a) {
		
		//i为当前位置, i之前的元素已经排好序了, 将a[i]插入到左边合适的位置
		//i从1开始, 因为第0位只有一位, 直接是排好的
		for(int i = 1; i < a.length; i++) {
			//记录需要排序的元素, i之前的元素已经排好序了
			Comparable temp = a[i];
			//在排好序的数组中, 倒着往前比较, 比a[i]大的, 直接往后移一位
			int j = i - 1;
			for(; j >= 0; j--) {
				//a[j] > temp, a[j]后移一位, 而不是进行交换操作, 可以提高效率
				if(less(a[j], temp)) {
					//change(a, j, j + 1);
					a[j + 1] = a[j];
				}else {
					break;
				}
			}
			//j可能在正常位置, 此时a[j] <= temp, 在j的后一位设置值; j也可能为-1, 在下标0处设置值
			a[j + 1] = temp;
		}
	}
	
	/**
	 * 希尔排序, 就是改进的插入排序
	 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；
	 * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
	 * 
	 * 一般选择增量的规则是：取上一个增量的一半作为此次子序列划分的增量，一般初始值元素的总数量。
	 */
	public static void xiErSort(Comparable[] a) {
		//增量
		int d = a.length / 2;
		while(d >= 1) {
			//从第d位开始, 因为从d位开始, 前边才会有需要排序的元素
			for(int i = d; i < a.length; i++) {
				//当前值
				Comparable val = a[i];
				int j = i - d;
				//直接插入排序, 向前找所适合的位置
				while(j >= 0 && less(a[j], val)) {
					a[j+d] = a[j];
					j = j - d;
				}
				a[j + d] = val;
			}
			
			//取下一个分量
			d = d / 2;
			
		}
	}
	
	/**
	 * 堆排序 堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序， 它的最坏，最好，平均时间复杂度均为O(nlogn)，它也是不稳定排序。
	 * 
	 * 堆是一棵完全二叉树 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；(升序使用)
	 * 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。(降序使用)
	 * 
	 * 堆在数组中的性质 A[i]的左节点为A[2i+1],右节点为A[2i+2]，父节点为A[i/2]
	 * 
	 * 大根堆, 父节点元素不小于任意子节点元素
	 * 
	 * 给定一个完全二叉树，除了根节点以外，此二叉树满足“堆序性”，
	 * “元素下沉”的目的是在此二叉树中将根节点的元素放至合适的坑位，相应地调整其他元素，最终使得包括根节点在内的整棵二叉树都满足“堆序性”。
	 * 
	 * 当父节点的元素值小于左子节点的元素值或者右子节点的元素值时，将父节点的元素值与左右子节点较大的元素值进行交换，针对交换后的父节点，
	 * 循环执行“元素下沉”操作， “元素下沉”的终止条件就是父节点的元素值不小于其任意左右子节点的元素值，或者当前父节点无子节点（即当前节点为叶子节点）。
	 * 
	 * a.将无需序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆;
	 * b.将堆顶元素与末尾元素交换，将最大或最小元素"沉"到数组末端;
	 * c.重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
	 */
	
	public static void heapSort(Comparable[] a) {
		
		//(1)总共需要调整(len-1)次最大堆, 才能找到(len - 1)次最大值, 才能排好序
		for(int k = 1; k < a.length; k++) {
			//(2)一次将数组调整为最大堆(对每个节点进行设置为大顶堆)
			for(int i = a.length - 1; i >= 0; i--) {
				heapify(a, i, a.length - k + 1);
			}	
			
			//(3)将堆顶元素(最大值)放到末尾
			change(a, 0, a.length - k);
		}
	}
	/**
	 * 将当前节点设置为大顶堆
	 * @param a 当成是完全二叉树的数组
	 * @param curIndex 当前父节点的位置(对此节点设置大顶堆)
	 * @param size 节点总数
	 */
	public static void heapify(Comparable[] a, int curIndex, int size) {
		if(curIndex < size) {
			//当前节点的左右节点(公式获得)
			int left = 2 * curIndex + 1;
			int right = 2 * curIndex + 2;
			
			//记录父节点与左右子节点最大值得那个节点的下标
			int maxIndex = curIndex;
			if(right < size) {
				if(less(a[right], a[maxIndex])) {
					maxIndex = right;
				}
			}
			if(left < size) {
				if(less(a[left], a[maxIndex])) {
					maxIndex = left;
				}
			}
			
			//如果最大的元素不是根元素, 交换元素, 并根据交换后的子节点继续设置大顶堆
			if(maxIndex != curIndex) {
				//交换最大值与当前顶的元素
				change(a, curIndex, maxIndex);
				
				//对交换的顶点继续设置大顶堆
				heapify(a, maxIndex, size);
			}
		}
	}
	
	/**
	 * 归并排序
	 */
	public static void mergeSort(int[] a) {
		//在排序前，先建好一个长度等于原数组长度的临时数组，在归并时存储临时值使用(避免递归时创建, 造成空间的频繁创建和销毁)
		int[] temp = new int[a.length];
		mergeSort(a, 0, a.length - 1, temp);
	}
	
	/**
	 * 递归排序, left为当前分解数组的左节点, right为当前分解数组的右节点
	 *    算法:将数组从中间截断, 对左侧归并排序, 对右侧归并排序, 再将将两边数组归并为一个数组
	 */
	public static void mergeSort(int[] a, int left, int right, int[] temp) {
		if(left < right) {
			int mid = (left + right) / 2;
			mergeSort(a, left, mid, temp);
			mergeSort(a, mid + 1, right, temp);
			merge(a, left, right, mid, temp);
		}
	}
	
	/**
	 * 合并left~mid   mid+1~right  两个数组
	 */
	public static void merge(int[] a, int left, int right, int mid, int[] temp) {
		int i = left;//左序列指针
		int j = mid + 1;//有序列指针
		int t = 0;//临时数组指针
		//(1)合并两个数组
		while(i <= mid && j <= right) {
			if(a[i] > a[j]) {
				temp[t++] = a[j++];
			}else {
				temp[t++] = a[i++];
			}
		}
		
		//(2)将剩余元素放进temp中
		while(i <= mid) {
			temp[t++] = a[i++];
		}
		while(j <= right) {
			temp[t++] = a[j++];
		}
		
		//(3)将temp中元素全部拷贝到原数组中
		t = 0;
		while(left <= right) {
			a[left++] = temp[t++];
		}
	}
	
	/**
	 * @Description: 
	 * a>b,返回true  a<b,返回false
	 */
	public static boolean less(Comparable a, Comparable b) {
		if(a.compareTo(b) > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: 
	 * 交换数组a的下标index1和index2的位置
	 */
	public static void change(Comparable[] a, int index1, int index2) {
		if(index1 == index2) {
			return;
		}
		Comparable val = a[index1];
		a[index1] = a[index2];
		a[index2] = val;
	}
	
	public static void main(String[] args) {
//		Integer[] a = new Integer[] {8,4,5,7,1,3,6,2};
		int[] a = new int[] {8,4,5,7,1,3,6,2,9};
//		a = new Integer[] {1,2,3,1,1};
//		selectSort(a);
//		quickSort(a);
//		insertSort(a);
//		xiErSort(a);
//		heapSort(a);
		mergeSort(a);
		System.out.println(Arrays.asList(a));
	}
}







