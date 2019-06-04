package codeTest.v1.data.struct.str;

import java.util.Arrays;

public class MyStr {
	public static void main(String[] args) {
		getNext("ababaaaba");
		getNextUpdate("aaaabcde");
	}
	public static void index(String str1, String str2, int pos) {
		
	}
	/**
	 * 0 1 2 3 4 5 6 7 8 
	 * a b a b a a a b a 
	 *-1 0
	 * 判断j == 0时, 是初始, k = -1, 所以next[0] = -1, 此时j==0, k==-1
	 * 判断j = 1时, k==-1; 所以next[j++] = k++; 此时, j == 1, k == 0;
	 * 判断j = 2时, str[1]!=str[0], k=next[k]=next[0]=-1; str[j++]==str[k++]; 所以next[2] = 0, 此时j==2,k==0
	 * 判断j = 3时, str[2]==str[0], 所以next[j++] = k++;所以next[3] = 1, 此时j==3,k==1 
	 * 判断j = 4时, str[3]==str[1], 所以next[j++] = k++;所以next[4] = 2, 此时j==4,k==2
	 * 判断j = 5时, str[4]==str[2], 所以next[j++] = k++;所以next[5] = 3, 此时j==5,k==3
	 * 判断j = 6时, str[5]!=str[3], k=next[k]=next[3]=1; str[5]!=str[1], k=next[k]=next[1]=0; str[5]=str[0];
	 * 		所以next[j++] = k++, next[6] = 1, 此时j=6, k=1  
	 */
	public static int[] getNext(String subStr) {
		int[] next = new int[subStr.length()];
		
		//j表示第几位的字符, k表示j的下一位的匹配所在的索引的值
		int j = 0, k = -1;
		//初始第一位next的值为-1
		next[j] = k;
		
		//已知前j位, 推测第j + 1位
		while(j < subStr.length() - 1) {
			//如果str[k] == str[j]，很明显，我们的next[j+1]就直接等于k+1
			if(k == -1 || subStr.charAt(j) == subStr.charAt(k)) {
				next[++j] = ++k;
			}
			//否则, 需要向前查找
			else {
				k = next[k];
			}
		}
		
		return next;
	}
	
	//ababacde
	public static int[] getNextUpdate(String str) {
		str = "abcaacbbcbadaabcacbd";
		/* 0   1   2   3   4  5  6  7  8   9 10   11  12  13  14  15  16  17
		 * a   b   c   a   a  c  b  b  c   b  a   d   a   a   b   c   a   c b  d
		 * next[0] = -1
		 * next[1] = 0
		 * next[2] = 0
		 * next[3] = -1
		 * next[4] = 1
		 */
		int[] next = new int[str.length()];
		for(int i = 0; i < next.length; ++i) {
			next[i] = 100;
		}

		// j表示第几位的字符, k表示j的下一位的匹配所在的索引的值
		int j = 0, k = -1;
		// 初始第一位next的值为-1
		next[j] = k;

		// 已知前j位, 推测第j + 1位
		while (j < str.length() - 1) {
			// 如果str[k] == str[j]，很明显，我们的next[j+1]就直接等于k+1
			if (k == -1 || str.charAt(j) == str.charAt(k)) {
				// 如果str[j + 1] == str[k + 1]，回退后仍然失配，所以要继续回退
				char a = str.charAt(++j);
				char b = str.charAt(++k);
				if(a == b) {
					next[j] = next[k];
				}else {
					next[j] = k;					
				}
			}
			// 否则, 需要向前查找
			else {
				k = next[k];
			}
		}
		System.out.println(Arrays.toString(next));
		return next;
	}
}














































