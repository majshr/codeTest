package codeTest.v1.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test1 {
	/**
	 * 示例 1:
	 * 集合: [1,2,3]
	 * 结果: [1,2] (当然, [1,3] 也正确) 
	 * 
	 * 示例 2:
	 * 集合: [1,2,4,8]
	 * 结果: [1,2,4,8] 
	 * 
	 * 接龙的倍数可以传递, 实现后一个数是前一个数倍数, 就可以实现每两个可以整除 1 2 3 9
	 * 
	 */
    public List<Integer> largestDivisibleSubset(int[] nums) {
    	if(nums == null || nums.length == 0) {
    		return new ArrayList<Integer>();
    	}
    	
    	Arrays.sort(nums);
		/**
		 * 还是看一个栗子加深理解吧，也不举太复杂的栗子，就[0,1,2,3,4,5,6,7,8,9,10]其实本来不应该有0，但是为了表示的方便，暂时加进来，不考虑就是了。
		 * 从大到小遍历这个数组，dp[i]中代表以元素i作为结尾的最大集合长度。dp[10] = 1,然后逆推往回求dp[9]，从9一直到最后找能被10整除的数字，
		 * 没这样的不存在，所以dp[9]=1,同理dp[8]=dp[7]=dp[6]=1.到dp[5]的时候，向后找找到了10能整除5，所以dp[5] = 2,
		 * 同理dp[4]找到8、dp[3]找到6或者9，因为dp[6]=dp[9]=1，所以dp[3]找到他俩中的较大值再加1dp[3]=2，
		 * 也等于2,dp[2]找到4\6\8\10,其中最大的dp[4]=2，所以dp[2] = 3，dp[1]后面的都能找到它的倍数，
		 * 其中最大的dp[2]=3,所以dp[1]=4。到此为止，我们已经找到了最大的共存元素个数，这个时候就需要将其还原了，但是好像少了什么，
		 * 没错，我找到dp[1]最大了，但是dp[1]是包含哪4个元素？好像找不到了，这就需要在找的过程顺便建立一种联系，可以想象成很多个链表。
		 */
    	//从大往小找, 大的数满足条件
    	
    	//记录第i个数能找出满足条件的集合
    	//第i个数集合算法: 先向后找能整除它的数, 找到了m, n...; 找出dp[m], dp[n]...中的最大值, 加1, 就是第i个数的满足条件的集合了
    	int[] dp = new int[nums.length];
    	
    	//key:下标     value:下标对应最长集合
    	Map<Integer, List<Integer>> map = new TreeMap<Integer, List<Integer>>();
    	
    	List<Integer> list = new ArrayList<Integer>();
    	
    	//从最后一个元素开始, 向后查找它的最长集合
    	for(int i = nums.length - 1; i >= 0; i--) {
    		//记录当前元素最长集合的长度
    		int maxSize = 0;
    		//记录当前元素最长的集合
    		List<Integer> maxList = new LinkedList<Integer>();
    		for(int j = i + 1; j < nums.length; j++) {
    			if(nums[j] % nums[i] == 0) {
    				if(dp[j] > maxSize) {
    					maxSize = dp[j];
    					maxList.clear();
    					maxList.addAll(map.get(j));
    				}
    			}
    		}
    		//记录当前元素自身
    		dp[i] = maxSize + 1;
    		((LinkedList)maxList).addFirst(nums[i]);
    		map.put(i, maxList);
    	}
    	int i = 0;
    	int maxSize = 0;
    	int maxIndex = i;
    	for(int size : dp) {
    		if(size > maxSize) {
    			maxSize = size;
    			maxIndex = i;
    		}
    		i++;
    	}
    	return map.get(maxIndex);
    }
    /*    
     * 给出：nums1 = [1,1,2], nums2 = [1,2,3],  k = 2
		返回： [1,1],[1,1]
    	返回序列中的前 2 对数：
    	[1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
     */
    public static List<int[]> test(int[] nums1, int[] nums2, int k) {
    	List<int[]> list = new ArrayList<int[]>();
    	if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k == 0) {
    		return list;
    	}
    	
    	int count = 0;
    	for(int i = 0, j = 0; i < nums1.length && j < nums2.length; ) {
    		int[] arr = new int[2];
    		arr[0] = nums1[i];
    		arr[1] = nums2[j];
    		list.add(arr);
    		count++;
    		if(count == k) {
    			break;
    		}
    		if(i + 1 < nums1.length) {
    			if(j + 1 < nums2.length) {
    				if(nums1[i + 1] > nums2[j + 1]) {
    	    			j++;
    	    		}else {
    	    			i++;
    	    		}
    			}else {
    				i++;
    			}
    		}else {
    			j++;
    		}
    		
    	}
    	
    	return list;
    }
    
    public static void main(String[] args) {
    	Test1 test = new Test1();
//		int[] nums1 = new int[] {1,7,11};
//		int[] nums2 = new int[] {2,4,6};
//		System.out.println(Test.test(nums1, nums2, 4));;
		
		List<Interval> intervals = new ArrayList<>();
		intervals.add(test.new Interval(1, 3));
		intervals.add(test.new Interval(2, 6));
		intervals.add(test.new Interval(8, 10));
		intervals.add(test.new Interval(15, 18));
//		test.merge(intervals);
//		int[][] nums = new int[][]{{1, 3, 1},{1, 5, 1},{4, 2, 1}};
//		int[][] nums = new int[][]{{1,2},{1,1}};
//		char[][] charss = new char[][] {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
////		test.mergeTwo(test.new Interval(1, 4), test.new Interval(0, 1));
//		test.minPathSum1(nums);
		int[] nums = new int[]{2,2,3,4};
		test.triangleNumber(nums);
//		test.exist(charss, "ABCCED");
	}
    
    public int triangleNumber(int[] nums) {
        int res = 0;
        
       
        Arrays.sort(nums);
        for(int i = 0; i < nums.length; ++i) {
        	for(int j = i + 1; j < nums.length; ++j) {
        		for(int k = j + 1; k < nums.length; ++k) {
        			int val = nums[i] + nums[j];
        			if(val > nums[k]) {
        				res++;
        			}else {
        				break;
        			}
        		}
        	}
        }
        
        return res;
    }
    
    private void all(int[] nums, int index) {
    	if(index == nums.length - 1) {
    		System.out.println(nums.toString());
    		return;
    	}
    	for(int i = index; i < nums.length; i++) {
        	for(int j = index; j < nums.length; i++) {
        		swap(nums, i, j);
        		all(nums, index + 1);
        		swap(nums, i, j);
        	}
        }
    }
    
    private void swap(int[] nums, int m, int n) {
    	int temp = nums[m];
    	nums[m] = nums[n];
    	nums[n] = temp;
    }
    
    public boolean exist(char[][] board, String word) { 
    	//当前节点是否访问过了
        boolean[][] isVisited = new boolean[board.length][board[0].length]; 
        for (int i = 0; i < board.length; i++) 
            for (int j = 0; j < board[0].length; j++) 
            	//从当前节点出发的路是否通
                if (isThisWay(board, word, i, j, 0, isVisited)) 
                	return true; 
        return false; 
    } 
    //下一步的四个方向
    int[] dh = {0, 1, 0, -1}; 
    int[] dw = {1, 0, -1, 0};
    public boolean isThisWay(char[][] board, String word, int row, int column, 
    		int index, boolean[][] isVisited) { 
    	//判断当前节点是否符合条件
        if (row < 0 || row >= board.length || column < 0 || column >= board[0].length 
            || isVisited[row][column] || board[row][column] != word.charAt(index)) 
                return false;  
        //如果匹配到了字符末尾, 说明匹配到了字符串
        if (++index == word.length()) 
        	return true;  
        isVisited[row][column] = true; 
        
        //以board[row][column]为起点找到匹配上word路径 
        for (int i = 0; i < 4; i++) 
            if (isThisWay(board, word, row + dh[i], column + dw[i], index, isVisited)) 
                return true;  
        isVisited[row][column] = false;  //遍历过后，将该点还原为未访问过 
        return false; 
    }
    
//    public boolean exist(char[][] board, String word) {
//        boolean isExist = false;
//        
//        
//        
//        return isExist;
//    }
    
    /**
     * 到当前节点的最短距离 = min(到上节点最短距离 + 到下节点最短距离) + 当前节点大小
     * [
		  [1,3,1],
		  [1,5,1],
		  [4,2,1]
	   ]
     */
    public int minPathSum1(int[][] grid) {
    	
        int res = 0;
        //dp数组记录每个节点的最短距离
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        //先算出第一横行, 第一竖列的距离
        for(int i = 1; i < dp.length; i++) {
        	dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        
        for(int i = 1; i < dp[0].length; i++) {
        	dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        
        //再从第二行开始, 算出每一位最短距离
        for(int i = 1; i < dp.length; i++) {
        	for(int j = 1; j < dp[0].length; j++) {
        		dp[i][j] = dp[i - 1][j] > dp[i][j - 1] ? dp[i][j - 1] : dp[i - 1][j];
        		dp[i][j] += grid[i][j];
        	}
        }
        
        return dp[dp.length - 1][dp[0].length - 1];
    }
    
    public int minPathSum(int[][] grid) {
    	
        int res = 0;
        int i = grid.length - 1;
        int j = grid[0].length - 1;
        //如果数组已有一个元素
        if(i == 0 && j == 0) {
        	res = grid[i][j];
        }
        else {
        	res = minPath(grid, i , j);        	
        }
        
        return res;
    }
    
    //只有两边都有路径的情况才能用
    public int minPath(int[][] grid, int i, int j) {
    	int leftNodeMin = 0;
		int shangNodeMin = 0;
		int res = 0;
		
		//如果当前节点同时存在左节点和上节点, 需要从两种路径中选出小的
    	if(i > 0 && j > 0) {
    		shangNodeMin = minPath(grid, i - 1, j);
    		leftNodeMin = minPath(grid, i, j - 1);
    		res = shangNodeMin > leftNodeMin ? leftNodeMin : shangNodeMin;
    	}
    	//如果i==0, 只有从上边下来的路径, 也就是最短路径了
    	if(i == 0) {
    		for(int index = 0; index < j; index++) {
    			res += grid[i][index]; 
    		}
    	}
    	//如果j==0, 只有从左边过来的路径, 也就是最短路径了
    	if(j == 0) {
    		for(int index = 0; index < i; index++) {
    			res += grid[index][j];
    		}
    	}
    	
    	res += grid[i][j];
    	return res;
    }
    
	public class Interval {
		int start;
		int end;

		Interval() {
			start = 0;
			end = 0;
		}

		Interval(int s, int e) {
			start = s;
			end = e;
		}
	}
	/**
	 * 合并区间
	 * a和b合并区间
	 * a.start a.end   b.start  b.end
	 * (1)b.start  a.start  a.end   b.end
	 * (2)b.start  a.start  b.end   a.end
	 * (3)a.start  b.start  a.end   b.end
	 * (4)a.start  b.start  b.end   a.end
	 */
    public List<Interval> merge(List<Interval> intervals) {
        if(intervals == null || intervals.size() == 0 || intervals.size() == 1) {
        	return intervals;
        }
        labelWhile:
        while(true) {
        	int len = intervals.size();
        	label:
                for(int i = 0; i < len; i++) {
                	Interval first = intervals.get(i);
                	for(int j = i + 1; j < len; j++) {
                		Interval next = intervals.get(j); 
                		List<Interval> list = mergeTwo(first, next);
                		if(list.size() > 0) {
                			intervals.set(i, list.get(0));
                			intervals.remove(j);
                			break label;
                		}
                	}
                	if(i == len - 1) {
                		break labelWhile;
                	}
                }
        	
        }
        return intervals;
        
    }
    /*
     * 判断a和b是否存在区间的情况1 4  0 1
     * (1)b.start  a.start  b.end   a.end
	 * (2)b.start  a.start  a.end   b.end
	 * (3)a.start  b.start  a.end   b.end
	 * (4)a.start  b.start  b.end   a.end*/
    public List<Interval> mergeTwo(Interval a, Interval b){
    	Interval c = null;
    	List<Interval> list = new ArrayList<>();
    	if(a.start <= b.end && a.start >= b.start && a.end >= b.end) {
    		c = new Interval();
    		c.start = b.start;
    		c.end = a.end;
    	}else if(a.start >= b.start && a.end <= b.end) {
    		c = b;
    	}else if(a.start <= b.start && a.end <= b.end && a.end >= b.start){
    		c = new Interval();
    		c.start = a.start;
    		c.end = b.end;
    	}else if(a.start <= b.start && a.end >= b.end) {
    		c = a; 
    	}
    	if(c != null) {
    		list.add(c);
    	}
    	return list;
    }
}







































