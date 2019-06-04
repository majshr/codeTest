package codeTest.v1.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
/*    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) {
        	return 0;
        }
        int count = s.length();
        int len = s.length();
        
        while(count > 1) {
        	for(int i = 0; i + count <= len; i++) {
            	String subStr = s.substring(i, i + count);
            	if(isStrRight(subStr)) {
            		return count;
            	}
            }
        	count--;
        }
        return count;
    }*/
	
	/**
	 * 最长不含相同字母的子串
	 */
	public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) {
        	return 0;
        }
        int count = 0;
        
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int len = s.length();
        
        for(int i = 0, j = 0; j < len; j++) {
        	char ch = s.charAt(j);
        	if(map.containsKey(ch) && map.get(ch) >= i) {
        		i = map.get(ch) + 1;
        	}
        	count = Math.max(count, j - i + 1);
        	map.put(ch, j);
        }
        
        return count;
    }
	
    public boolean isStrRight(String str) {
    	Set<Character> set = new HashSet<>();
    	for(int i = 0; i < str.length(); i++) {
    		if(set.contains(str.charAt(i))) {
    			return false;
    		}else {
    			set.add(str.charAt(i));
    		}
    	}
    	return true;
    }

    /**
     * 由于回文分为偶回文和奇回文, 在处理奇偶问题上比较繁琐, 使用一个技巧, 在字符串首尾, 及各字符间插入一个字符(前提是这个字符未出现在串里)
     * 举个例子：s="abbahopxpo"，转换为s_new="$#a#b#b#a#h#o#p#x#p#o#"（这里的字符 $ 只是为了防止越界，下面代码会有说明），
     * 如此，s 里起初有一个偶回文abba和一个奇回文opxpo，被转换为#a#b#b#a#和#o#p#x#p#o#，长度都转换成了奇数。
     * 
     * 
     */
    public String transStr(String s) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("#");
    	for(int i = 0; i < s.length(); ++i) {
    		sb.append(s.charAt(i)).append("#");
    	}
    	return sb.toString();
    }

    /**
     * 从中心往两边扩散, 找回文数, 分两种情况, 回文字符串长度可能是单数, 可能是双数
     * 		单数: index; left=index-1, right=index+1
     * 		双数: index; left=index, right=index+1
     * 需要遍历一遍数组, 找中心位置{
     * 		遍历两次数组, 一遍找单数回文字符串, 一遍找双数回文字符串
     * }
     * n*(2*n)  O(n2)的时间复杂度
     */
    public String longestPalindrome1(String s) {
    	if(s == null || s.length() == 0) {
    		return s;
    	}
    	//
    	String maxStr = "";
        for(int i = 0; i < s.length(); i++) {
        	String leftStr = findHuiWenShu(s, i, i - 1, i + 1);
        	String rightStr = findHuiWenShu(s, i, i, i + 1);
        	leftStr = leftStr.length() > rightStr.length() ? leftStr : rightStr;
        	maxStr = maxStr.length() > leftStr.length()? maxStr : leftStr;
        }
        return maxStr;
    }
    public String findHuiWenShu(String s, int index, int left, int right) {
    	boolean isHas = false;
    	while(left >=0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
    		isHas = true;
    		left--;
    		right++;
    	}
    	//如果有回文, 恢复到回文处
    	if(isHas) {
        	left++;
        	right--;
        	return s.substring(left, right + 1);
    	}
    	//如果没有回文
    	else {
    		return String.valueOf(s.charAt(index));
    	}
    }
    
    public String convert(String s, int numRows) {
    	if(s == null || s.length() == 0) {
    		return s;
    	}
        ArrayList<Character>[] lists = new ArrayList[numRows];
        for(int i = 0; i < numRows; ++i) {
        	lists[i] = new ArrayList<Character>();
        }
        int strIndex = 0;
        while(strIndex < s.length()) {
        	for(int i = 0; i < numRows; ++i) {
        		if(strIndex >= s.length()) {
            		break;
            	}
            	lists[i].add(s.charAt(strIndex++));
            }
            for(int i = numRows - 1 - 1; i > 0; i--) {
            	if(strIndex >= s.length()) {
            		break;
            	}
            	lists[i].add(s.charAt(strIndex++));
            }        	
        }
        //
        StringBuffer sb = new StringBuffer();
        for(List<Character> list : lists) {
        	for(Character ch : list) {
        		sb.append(ch);        		
        	}
        }
        return sb.toString();
    }
    
    public int expressiveWords(String S, String[] words) {
    	if(S == null || words == null) {
    		return 0;
    	}
    	int count = 0;
        for(String word : words) {
        	if(isExpressiveWords(S, word)) {
        		count++;
        	}
        }
        return count;
    }
    public boolean isExpressiveWords(String s, String word) {
    	int i = 0, j = 0;
    	for(; i < s.length() && j < word.length(); ) {
    		if(s.charAt(i) == word.charAt(j)) {
				i = i + 1;
				j = j + 1;
    		}else{
    			i = getSameCharIndex(s, i);
    			if(i == -1) {
    				return false;    				
    			}
    		}
    	}
    	if(j == word.length()) {
    		if(i == s.length()) {
    			return true;
    		}else {
    			i = getSameCharIndex(s, i);
    			if(i == s.length()) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public int getSameCharIndex(String s, int index) {
    	if(index - 1 >= 0) {
    		char ch = s.charAt(index - 1);
    		int count = 1;
    		while(index < s.length() && ch == s.charAt(index)) {
    			index++;
    			count++;
    		}
    		if(count >= 3) {
    			return index;
    		}
    	}
    	return -1;
    }
    
    public void nextPermutation(int[] nums) {
    	for(int i = nums.length - 1; i - 1 >= 0; i--) {
    		
    		if(nums[i] > nums[i - 1]) {
    			Arrays.sort(nums, i, nums.length);
    			for(int j = i; j < nums.length; j++) {
    				if(nums[i - 1] < nums[j]) {
    					int temp = nums[i - 1];
    					nums[i - 1] = nums[j];
    					nums[j] = temp;
    					return;
    				}
    			}
    		}
    	}
    	int left = 0, right = nums.length - 1;
    	while(left < right) {
    		int temp = nums[right];
    		nums[right] = nums[left];
    		nums[left] = temp;
    		left++;
    		right--;
    	}
    }
    
    public static void main(String[] args) {
//		new Test().lengthOfLongestSubstring("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCD");
//		new Test().longestPalindrome1("abbc");
//    	new Test().convert("PAYPALISHIRING", 3);
//    	new Test().expressiveWords("vvkkkejjjjjj", new String[]{"vvkeej","vkeejj","vkeej","vvkejj","vkej","vvkkeej"});
//    	new Test().customSortString("cbafg", "abcd");
//    	new Test().numMatchingSubseq("abcde", new String[] {"a", "bb", "acd", "ace"});
//    	new Test().maxProduct(new int[] {1,5,0,3,1});
//    	new Test().maximumSwap(2736);
//    	new Test().minSubArrayLen(7, new int[] {2,3,1,2,4,7});
//    	new Test().test(new int[] {1, 2, 3, 4}, 0, 4, 3);;
//    	System.out.println("=====================");
//    	System.out.println(new Test().permute(new int[] {1, 2, 3, 4}).toString().substring(1));
    	new Test().numSubarrayBoundedMax(new int[] {73,55,36,5,55,14,9,7,72,52}, 32, 69);
    	ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
//		ListNode node6 = new ListNode(4);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
//		node5.next = node6;
//    	new Test().swapPairs(node1);
//		new Test().uniquePaths1(3, 2);
		
//		int[][] a = new int[][] {{0,0,0},{0,1,0},{0,0,0}};
//		new Test().uniquePathsWithObstacles(a);
		
		List<List<Integer>> triangle = new ArrayList();
		List<Integer> list1 = new ArrayList<>();
		list1.add(2);
		triangle.add(list1);
		list1 = new ArrayList<>();
		list1.add(3);
		list1.add(4);
		triangle.add(list1);
		list1 = new ArrayList<>();
		list1.add(6);
		list1.add(5);
		list1.add(7);
		triangle.add(list1);
		list1 = new ArrayList<>();
		list1.add(4);
		list1.add(1);
		list1.add(8);
		list1.add(3);
		triangle.add(list1);
//		new Test().minimumTotal(triangle);
		new Test().spiralOrder(new int[][] {{1, 2, 3}, {4, 5, 6},{ 7, 8, 9 }});
		//[-1, 0, 1, 2, -1, -4]
		new Test().threeSum(new int[]{-1, 0, 1, 2, -1, -4});
	}
    
    public int numFriendRequests(int[] ages) {
        int res = 0;
        
        
        
        return res;
    }
    
    /**三数之和*/
    //-4, -1, -1, 0, 1, 2
    public List<List<Integer>> threeSum(int[] nums) {
    	List<List<Integer>> res = new ArrayList<>();
    	Arrays.sort(nums);
    	if(nums.length > 0 && nums[0] > 0) {
    		return res;
    	}
    	
    	for(int i = 0; i < nums.length; i++) {
    		int l = i + 1, r = nums.length - 1;
    		while(l < r) {
    			int sum = nums[i] + nums[l] + nums[r];
    			if(sum == 0) {
    				List<Integer> list = new ArrayList<>();
    				list.add(nums[i]);
    				list.add(nums[l]);
    				list.add(nums[r]);
    				res.add(list);
//    				while(i < j && nums[i] == nums[i + 1]) {
//    					
//    				}
    			}
    		}
    		
    	}
    	
    	return res;
    }
    
    public void sumEqualsVal(int val, int[] nums) {
    	int i = 0, j = nums.length - 1;
    	while(i < j) {
    		int sum = nums[i] + nums[j];
    		if(sum > val) {
    			i++;
    		}else if(sum < val) {
    			j--;
    		}else {
    			
    		}
    	}
    	
    	
    }
    
    public List<Integer> spiralOrder(int[][] matrix) {
    	List<Integer> res = new ArrayList<>();
        
    	int i = 0, j = -1;
    	int right = matrix[0].length - 1;
    	int left = 0;
    	int top = matrix.length - 1;
    	int di = 0;
    	
    	while(res.size() < (right + 1) * (top + 1)) {
    		boolean a = false, b = false, c = false, d = false;
    		//右移
    		j++;
			while(j <= right) {
				res.add(matrix[i][j]);
				a = true;
				if(j == right) {
					break;
				}
				j++;
			}
			right--;
			
			//下移
			i++;
			while(i <= top) {
				res.add(matrix[i][j]);
				b = true;
				if(i == top) {
					break;
				}
				i++;
			}
			top--;
			
			//左移
			j--;
			while(j >= left) {
				res.add(matrix[i][j]);
				c = true;
				if(j == left) {
					break;
				}
				j--;
			}
			left++;
			
			//上移
			i--;
			while(i != di && i >= di) {
				res.add(matrix[i][j]);
				d = true;
				if(i == di) {
					break;
				}
				i--;
			}
			di++;
			
			if(!a && !b && !c &&!c) {
				break;
			}
    	}
    	
        return res;
    }
    
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        
        return res;
    }
    
    public int trap(int[] height) {
        int res = 0;
        
        Map<Integer, Integer> mapQian = new HashMap<>();
        List<Integer> indexListQian = new ArrayList<>();
        
        Map<Integer, Integer> mapHou = new HashMap<>();
        List<Integer> indexListHou = new ArrayList<>();
        
        int max = 0;
        int pre = 0;
        for(int i = 0; i < height.length; i++) {
        	if(height[i] > pre) {
        		mapQian.put(i, height[i]);
        		indexListQian.add(i);
        		pre = height[i];
        	}
        }
        
        max = pre;
        pre = 0;
        for(int i = height.length - 1; i >=0; i--) {
        	if(height[i] > pre) {
        		mapHou.put(i, height[i]);
        		indexListHou.add(i);
        		pre = height[i];
        		if(pre == max) {
        			break;
        		}
        	}
        }
        
        int indexMaxQian = 0;
        if(indexListQian.size() > 0) {
        	indexMaxQian = indexListQian.get(indexListQian.size() - 1);
        }
        for(int i = 0; i < indexListQian.size() - 1; i++) {
        	//小值下标
        	int index = indexListQian.get(i);
        	//大值下标
        	int indexN = indexListQian.get(i + 1);
        	//小值的值
        	int min = mapQian.get(index);

        	int val = (indexN - index - 1) * min;
        	for(int j = index + 1; j < indexN; j++) {
        		val = val - height[j];
        	}
        	res += val;
        }
        
        int indexMaxHou = 0;
        if(indexListHou.size() > 0) {
        	indexMaxHou = indexListHou.get(indexListHou.size() - 1);
        }
        for(int i = 0; i < indexListHou.size() - 1; i++) {
        	//小值下标
        	int index = indexListHou.get(i);
        	//大值下标
        	int indexN = indexListHou.get(i + 1);
        	//小值的值
        	int min = mapHou.get(index);

        	int val = (index - indexN - 1) * min;
        	for(int j = indexN + 1; j < index; j++) {
        		val = val - height[j];
        	}
        	res += val;
        }
        
        if(indexMaxQian != indexMaxHou) {
        	int val = (indexMaxHou - indexMaxQian - 1) * max;
        	for(int i = indexMaxQian + 1; i < indexMaxHou; i++) {
        		val = val - height[i];
        	}
        	res += val;
        }
        
        return res;
    }
    
    
    
    //1 2 2 3  4            1 2 3 4        2 3 4
   /*                               
       1  12  12  122                        1
                               2
                               3
                               4
                               12
                               13
                               14
    */
    
    //5 5 5 5 5
    // 1 2 2 2 3  2 22 222 
    //5 55 555
    
    Lock lock = new ReentrantLock();
    
    boolean[] b;
    Set<String> set;
    List<List<Integer>> list;
    Set<String> set1;
    public List<List<Integer>> subsetsWithDup2(int[] nums) {
        b = new boolean[nums.length];
        set = new HashSet<String>();
        list = new ArrayList<List<Integer>>();
        set1 = new HashSet<String>();
        
        Arrays.sort(nums);
        count(nums,"",nums.length,0);
        return list;
    }
    
    private void count(int[] nums,String s,int n,int j){
            //没有重复才添加
            if(set.add(s)){
               //以","分割数组
               String[] sa = s.split(",");
               List<Integer> al = new ArrayList<Integer>();
               for(int i = 0; i < sa.length; i++){
            	   if(sa[i].length() > 0){
            		   al.add(Integer.parseInt(sa[i]));
            	   }
               }
               Collections.sort(al);
               if(set1.add(al.toString()))
            	   list.add(al);
            }
        
        for(int i = j; i < nums.length;i++){
            if(!b[i]){
                b[i] = true;
                count(nums,s + "," + nums[i],n-1,i+1);
                b[i] = false;
            }
        }
    }

    
    public List<List<Integer>> subsetsWithDup1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int size = nums.length;
        
        Set<Integer> allSet = new HashSet<>();
        for(int i = 0; i < size; i++) {
        	if(allSet.contains(nums[i])) {
        		continue;
        	}else {
        		allSet.add(nums[i]);
        	}
        	List<List<Integer>> subRes = new ArrayList<>();
        	List<Integer> list = new ArrayList<>();
        	//判断是不是重复的元素
        	Set<Integer> set = new HashSet<>();
        	set.add(nums[i]);
        	list.add(nums[i]);
        	subRes.add(list);
        	
        	for(int j = i + 1; j < size; j++) {
        		int subResSize = subRes.size();
        		for(int k = subResSize - 1; k >= 0; k--) {
        			List<Integer> subResList = subRes.get(k);
        			if(set.contains(nums[j]) && !subResList.contains(nums[j])) {
        				continue;
        			}else {
        				List<Integer> newSubResList = new ArrayList<>(subResList);	
        				newSubResList.add(nums[j]);
            			subRes.add(newSubResList);
        			}
        		}
        		set.add(nums[j]);
        	}
        	res.addAll(subRes);
        }
    	res.add(new ArrayList<>());
    	return res;
    }
    
    
    public List<List<Integer>> subsetsWithDup(int[] nums) {
    	
        List<List<Integer>> res = new ArrayList<>();
        int size = nums.length;
        Arrays.sort(nums);
        
        for(int i = 0; i < size; i++) {
        	List<List<Integer>> subRes = new ArrayList<>();
        	List<Integer> list = new ArrayList<>();
        	//判断是不是重复的元素
        	Set<Integer> set = new HashSet<>();
        	set.add(nums[i]);
        	list.add(nums[i]);
        	subRes.add(list);
        	
        	for(int j = i + 1; j < size; j++) {
        		int subResSize = subRes.size();
        		for(int k = 0; k < subResSize; k++) {
        			List<Integer> subResList = subRes.get(k);
        			List<Integer> newSubResList = new ArrayList<>(subResList);
        			newSubResList.add(nums[j]);
        			subRes.add(newSubResList);
        		}
        	}
        	res.addAll(subRes);
        }
    	res.add(new ArrayList<>());
    	Iterator<List<Integer>> iterator = res.iterator();
    	Set<String> set = new HashSet<String>();
    	while(iterator.hasNext()) {
    		List<Integer> list = iterator.next();
    		if(set.contains(list.toString())) {
    			iterator.remove();
    		}else {
    			set.add(list.toString());
    		}
    	}
    	return res;
    }
    
    public void digui(List valList, int index, List<List<Integer>> res) {
    	
    }
    
    /**
     * 3
     * 1 2 3
     * 1 3 6
     */
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        
        int[] dp = new int[nums.length];
        Map<Integer, List<Integer>> map = new HashMap<>(); 
        for(int i = 0; i < nums.length; i++) {
        	if(i == 0) {
        		dp[i] = nums[i];
        	}else {
        		dp[i] = dp[i - 1] + nums[i];
        	}
        	List<Integer> values = map.get(dp[i]);
        	if(values == null) {
        		values = new ArrayList<Integer>();
        		values.add(i);
        	}else {
        		values.add(i);
        	}
        	map.put(dp[i], values);
        }
        for(int i = 0; i < nums.length; i++) {
        	int leftVal = 0;
        	if(i != 0) {
        		leftVal = dp[i - 1];
        	}
        	int rightVal = leftVal + k;
        	List<Integer> values = map.get(rightVal);
        	if(values != null) {
        		for(int index = 0; index < values.size(); index++) {
        			Integer value = values.get(index);
        			if(value >= i) {
        				count += (values.size() - index);
        				break;
        			}
        		}
        	}
        }
        
        return count;
    }
    /**求数组中和为k的最大子数组的元素个数*/
    public void testA(int[] nums, int k) {
    	//dp为下标从0-index的所有元素的和
    	int[] dp = new int[nums.length];
    	//key: dp[i]的值      value: dp[i]的值对应的下标i
    	Map<Integer, Integer> map = new HashMap<>();
    	for(int i = 0; i < nums.length; i++) {
    		if(i == 0) {
    			dp[i] = nums[i];
    		}else {
    			dp[i] += dp[i - 1];
    		}
    		//list只需要保存最后面那个下标i集合, 这样减去当前的下标, 就是最大的子串的数量了
    		map.put(dp[i], i);
    	}
    	
    	int count = 0;
    	for(int i = 0; i < dp.length; i++) {
    		if(dp[i] == k) {
    			count = Math.max(i + 1, count);
    		}
    		if(map.get(dp[i] + k) != null) {
    			count = Math.max(map.get(dp[i] + k) - i + 1, count);
    		}
    	}
    }
    
/*    相当于是一种滑动窗口的解法，维护一个数字乘积刚好小于k的滑动窗口窗口，用变量left来记录其左边界的位置，
    右边界i就是当前遍历到的位置。遍历原数组，用prod乘上当前遍历到的数字，然后进行while循环，如果prod大于等于k，
    则滑动窗口的左边界需要向右移动一位，删除最左边的数字，那么少了一个数字，乘积就会改变，所以用prod除以最左边的数字，
    然后左边右移一位，即left自增1。当我们确定了窗口的大小后，就可以统计子数组的个数了，就是窗口的大小。
    为啥呢，比如[5 2 6]这个窗口，k还是100，右边界刚滑到6这个位置，这个窗口的大小就是包含6的子数组乘积小于k的个数，
    即[6], [2 6], [5 2 6]，正好是3个。
    所以窗口每次向右增加一个数字，然后左边去掉需要去掉的数字后，窗口的大小就是新的子数组的个数，每次加到结果res中即可*/
    //nums = [10,5,2,6], k = 100
    /*
     * 10            [10]
     * 10,5          [5] [10, 5]
     * 10,5,2->5,2   [2] [5,2]
     * 5,2,6         [6] [2,6] [5,2,6] 
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
    	if(k <= 1) {
    		return 0;
    	}
    	int count = 0, left = 0;
    	int all = 1;
    	for(int i = 0; i < nums.length; i++) {
    		//求出当前数组的元素乘积
    		all *= nums[i];
    		while(all >= k) {
    			all /= nums[left];
    			left++;
    		}
    		count += (i - left + 1);
    	}
    	
    	return count;
    }
    
    public int numSubarrayProductLessThanK1(int[] nums, int k) {

        if (k <= 1)
            return 0;
        int count = 0, left = 0 , one = 1;
        for (int right = 0; right < nums.length; right++)
        {
        	//求当前的乘积和
            one *= nums[right];
            //
            while (one >= k)
            {
                one /= nums[left];
                left++;
            }
            count += right - left + 1;
        }
        return count;
    }
    
 /*   [
     [2],             [2]
    [3,4],           [5,6]
   [6,5,7],       [11,10,13]
  [4,1,8,3]       [4, 1, 8,3]
                  15  11  18  16
  [ ]
]*/
    public int minimumTotal(List<List<Integer>> triangle) {
        int len = triangle.size();
        
        int[] arr = new int[len];
        //在第几层就有几个元素
        for(int i = 0; i < len; i++) {
        	List<Integer> list = triangle.get(i);
        	if(i == 0) {
        		arr[0] = list.get(0);
        	}else {
        		List<Integer> preList = triangle.get(i - 1);
        		for(int k = 0; k < list.size(); k++) {
        			int val = list.get(k);
        			if(k == 0) {
        				list.set(k, val + preList.get(0));
        			}
        			else if(k == list.size() - 1) {
        				list.set(k, val + preList.get(k - 1));
        			}
        			else {
        				list.set(k, val + 
        						(preList.get(k) > preList.get(k - 1) ? preList.get(k - 1) : preList.get(k)));
        			}
        			
        		}
        	}
        }
    	
        List<Integer> list = triangle.get(triangle.size() - 1);
        int min = Integer.MAX_VALUE;
        for(Integer val : list) {
        	if(min > val) {
        		min = val;
        	}
        }
    	return min;
    }
    
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    	int m = obstacleGrid.length;
    	int n = obstacleGrid[0].length;
    	int[][] all = new int[m][n];
    	
    	for(int i = 0; i < n; i++) {
    		if(obstacleGrid[0][i] == 0) {
    			all[0][i] = 1;    			
    		}else {
    			while(i < n) {
    				all[0][i] = 0;
    				i++;
    			}
    		}
    	}
    	
    	for(int i = 0; i < m; i++) {
    		if(obstacleGrid[i][0] == 0) {
    			all[i][0] = 1;	
    		}else {
    			while(i < m) {
    				all[i][0] = 0;
    				i++;
    			}
    		}
    	}
    	
    	for(int i = 1; i < m; i++) {
    		for(int j = 1; j < n; j++) {
    			if(obstacleGrid[i][j] == 0) {
    				all[i][j] = all[i][j - 1] + all[i - 1][j];	
    			}else {
    				all[i][j] = 0;
    			}
    			
    		}
    	}
    	
    	return all[m - 1][n - 1];
    }
    
    public int uniquePaths1(int m, int n) {
    	
    	int[][] all = new int[m][n];
    	for(int i = 0; i < n; i++) {
    		all[0][i] = 1;
    	}
    	for(int i = 0; i < m; i++) {
    		all[i][0] = 1;
    	}
    	for(int i = 1; i < m; i++) {
    		for(int j = 1; j < n; j++) {
    			all[i][j] = all[i][j - 1] + all[i - 1][j];
    		}
    	}
    	
    	return all[m - 1][n - 1];
    }
    
    public int uniquePaths(int m, int n) {
    	List<Integer> list = new ArrayList<>();
    	list.add(0);
    	
    	go(1, 1, m, n, list);
    	
    	return list.get(0);
    }
    
    public void go(int x, int y, int m, int n, List<Integer> list) {
    	if(x < m && y < n) {
    		go(x + 1, y, m, n, list);
    		go(x, y + 1, m, n, list);
    	}
    	else if(x < m && y == n) {
    		go(x + 1, y, m, n, list);
    	}
    	else if(x == m && y < n) {
    		go(x, y + 1, m, n, list);
    	}else if(x == m && y == n) {
    		int count = list.get(0);
    		count++;
    		list.set(0, count);
    	}
    }
    
	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}
	
    public ListNode swapPairs(ListNode head) {
        ListNode node = head;
        ListNode pre = head;
        int count = 1;
        for(; node != null; count++) {
        	if(count == 1) {
        		//当前节点
        		node = head;
            	pre = head;
            	if(node != null && node.next != null) {
            		//当前节点的下一个节点, 需要与之交换
                	ListNode next = node.next;
                	ListNode nextNext = node.next.next;
                	
                	node.next = nextNext;
                	next.next = node;
                	head = next;            		
                	
                	//进入下一个交换的节点
                	node = head.next.next;
                	pre = head.next;
            	}else {
            		break;
            	}
            	
        	}else {
        		//交换node和node.next节点
        		if(node != null && node.next != null) {
        			ListNode next = node.next;
        			ListNode nextNext = next.next;
        			pre.next = next;
        			next.next = node;
        			node.next = nextNext;
        			
        			pre = node;
        			node = node.next;
        		}else {
        			break;
        		}
        	}
        	count++;
        }
        return head;
    }
    
	/****/
    public ListNode removeNthFromEnd(ListNode head, int n) {
    	int count = 1;
    	ListNode cur = head;
    	ListNode nextCur = head;
    	ListNode nextCurPrevious = head;
        while(cur != null) {
        	count++;
        	cur = cur.next;
        	if(count > n && cur != null) {
        		nextCurPrevious = nextCur;
        		nextCur = nextCur.next;
        	}
        }
        //如果要删除的是链表的第一个元素, nextCurPrevious==nextCur==head
        if(nextCur == nextCurPrevious) {
        	//如果链表不止一个元素, 删除链表第一个元素
        	if(nextCur.next != null) {
        		head = nextCur.next;
        	}
        	//如果链表只有一个元素, 删除即是清空链表
        	else {
        		head = null;
        	}
        }
        //如果删除的不是链表第一个元素
        else {
        	nextCurPrevious.next = nextCur.next;
        }
        return head;
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	if(l1 == null || l2 == null) {
    		return null;
    	}
    	ListNode l3 = null;
    	ListNode cur = null;
    	int shengwei = 0;
    	boolean isfirst = true;
        while(l1 != null || l2 != null) {
        	int val1 = 0;
        	int val2 = 0;
        	if(l1 != null) {
        		val1 = l1.val;        		
        		l1 = l1.next;
        	}
        	if(l2 != null) {
        		val2 = l2.val;        		
        		l2 = l2.next;
        	}
        	
        	int val3 = val1 + val2 + shengwei;
        	shengwei = val3 / 10;
        	val3 = val3 % 10;
        	ListNode node = new ListNode(val3);
        	if(isfirst) {
        		l3 = node;
        		cur = node;
        	}else {
        		cur.next = node;
        		cur = node;
        	}
        	isfirst = false;
        }
        if(shengwei != 0) {
        	ListNode node = new ListNode(shengwei);
        	cur.next = node;
        }
        return l3;
    }
    
	/*
	 * 题目: A = [1, 2, 2, 1, 1, 4, 3, 1] L = 2 R = 3 Output: 3, 输出  ">=2 && <=3" 的数组的个数 
	 * {73,55,36,5,55,14,9,7,72,52}, 32, 69       测试用例可以考虑全面
	 * 
	 * 1）A[i]在[L, R]区间内，所以此时我们拥有从[left, i]...到[i, i]一共(i - left + 1)个合法的区间，
	 * 所以我们更新res += (i - left + 1)，并且更新count的值（后面会有用）；
	 * 2）A[i]小于L，此时虽然A[i]不在[L, R]区间内，但是对于前面的(rigntIndex - left + 1)个合法的，每个加上A[i]，
	 * 仍然可以形成合法的区间，所以res += (rigntIndex - left + 1)；
	 * 3）A[i] > R，此时所有包含A[i]的区间都将不再合法，i所以我们更新左边界left = i + 1，rightIndex = -1。
	 */
	public int numSubarrayBoundedMax(int[] A, int L, int R) {
		int left = 0; //左边第一个不大于R的元素的下标
		Set<Integer> set = new HashSet<Integer>();//单个复合条件的元素
		int res = 0;//集合数量
		int rightIndex = -1;//最新的符合区间的元素的下标, 没有符合区间的元素时, 设置为-1, 这样A[i] < L时, 不需要计算
		for(int i = 0; i < A.length; i++) {
			if(A[i] < L) {
				//如果找到了符合范围的下标
				if(rightIndex != -1) {
					res += (rightIndex - left + 1);    				
				}
			}
			else if(A[i] >= L && A[i] <= R) {
				rightIndex = i;
				//不用考虑单个重复的元素, 考虑的话加个set集合去重
				res += (i - left + 1); 
			}
			//如果当前位大于指定数, left移动到下一位
			else if(A[i] > R) {
				rightIndex = -1;
				left = i + 1;
			}
		}
		
		return res;
	}
    
    public void subPermute(int[] nums, int start, int length, int size) {
    	if(start == size) {
    		List<Integer> list = new ArrayList<>();
    		for(int i = 0; i < size; i++) {
    			list.add(nums[i]);
    		}
    		list.add(0);
    		System.out.print(list);
    	}
    	
    	for(int i = start; i < length; i++) {
    		swap(nums, i, start);
    		subPermute(nums, start + 1, length, size);
    		swap(nums, i , start);
    	}
    }
    
    /**
     	求不重复的数组元素的全排列
     */
    public List<List<Integer>> permute(int[] nums) {
    	List<List<Integer>> lists = new ArrayList<>();
    	execute(lists, nums, 0, nums.length);
    	return lists;
    }
    /**
     * 递归方法, 
     * 	交换0位和第0位的位置, 再保证第0位不变, 递归求出第1~n位的序列 
     * 	交换0位于第1位的位置, 再保证第0位不变, 递归求出第1~n的序列; 交换回第0位和第1位的位置, 恢复原数组
     *  交换0位和第2位的位置, 再保证第0位不变, 递归求出第1~n的序列; 恢复原数组 
     *  递归结束条件:
     *  	到达最后一位, 此时不能再交换数组元素, 此时的数组几位不重复的一个序列的元素, 保存起来.
     *  参数    lists:所有序列存储到的集合   nums:数组   start:每次递归数组的第一位     length:数组的长度
     */
    public void execute(List<List<Integer>> lists, int[] nums, int start, int length ) {
    	//如果到了最后一位, 直接将数组元素加入集合, 存起来
    	if(start == length) {
    		List<Integer> list = new ArrayList<>();
    		for(int i = 0; i < length; i++) {
    			list.add(nums[i]);
    		}
    		lists.add(list);
    	}else {
    		//先从自己开始, 然后一下一位作为自己
    		for(int j = start; j < length; j++) {
    			
    			//如果数组中有重复的元素, 如1 2 3 2, 
    			//1只需与2交换一次, 第二次与2交换时, 得到的都是重复的元素; 而2与2不用交换; 
    			//所以"需要交换的end元素"与"交换的start元素"之间存在一样的元素,就不要交换了
    			//判断是否可以交换获取不同数组
    			boolean isChange = true;
    			for(int k = start; k < j; k++) {
    				if(nums[k] == nums[j]) {
    					isChange = false;
    					break;
    				}
    			}
    			
    			//交换j和start元素(变换第一位与后边元素的值, 使第一位变成不同的值)
    			if(isChange) {
    				swap(nums, j, start);    				
    			}else {
    				continue;
    			}
    			
    			//求出所有剩余元素的序列
    			execute(lists, nums, start + 1, length);
    			
    			//之后需要再将数组交换回来, 恢复数组初始状态, 保证下一次循环正确
    			swap(nums, j, start);
    		}
    	}
    }
    /**交换数组元素*/
	public void swap(int[] nums, int a, int b) {
		int t;
		t = nums[a];
		nums[a] = nums[b];
		nums[b] = t;
	}
    
    public int minSubArrayLen(int s, int[] nums) {
    	if(nums == null || nums.length == 0) {
    		return 0;
    	}
    	int left = 0, right = 0;
    	int sum = nums[0];
    	int min = nums.length + 1;
    	while(true) {
    		if(sum < s) {
    			right++;
    			if(right >= nums.length) {
    				break;
    			}
    			sum += nums[right];
    		}
    		//符合条件
    		else {
    			min = (right - left + 1) > min ? min : (right - left + 1);
    			sum -= nums[left];
    			left++;
    		}
    	}
    	return min > nums.length ? 0 : min;
    }
    
    public int maximumSwap(int num) {
    	String str = String.valueOf(num);
    	if(str.length() == 1) {
    		return num;
    	}
    	for(int i = 0; i < str.length() - 1; i++) {
    		char ch = str.charAt(i);
    		char chN = str.charAt(i + 1);
    		//如果需要交换数据
    		if(ch < chN) {
    			//从后往前找最大的元素, 与前边比它小的位交换
    			int maxIndex = str.length() - 1;
    			for(int j = str.length() - 1; j > i; j--) {
    				if(str.charAt(j) > str.charAt(maxIndex)) {
    					maxIndex = j;
    				}
    			}
    			
    			int excIndex = 0;
    			for(int k = 0; k <= i; k++) {
    				if(str.charAt(maxIndex) > str.charAt(k)) {
    					excIndex = k;
    					break;
    				}
    			}
    			
    			StringBuffer sb = new StringBuffer(str);
    			char maxCh = str.charAt(maxIndex);
    			char minCh = str.charAt(excIndex);
    			sb.setCharAt(excIndex, maxCh);
    			sb.setCharAt(maxIndex, minCh);
    			return Integer.valueOf(sb.toString());
    		}
    	}
    	return num;
    	
    }
    
    public int maxProduct(int[] nums) {
    	if(nums == null || nums.length == 0) {
    		return 0;
    	}
    	int[] maxend = new int[nums.length];
    	int[] minend = new int[nums.length];
    	maxend[0] = nums[0];
    	minend[0] = nums[0];
        for(int i = 1; i < nums.length; ++i) {
			maxend[i]=Math.max(Math.max(maxend[i-1]*nums[i], minend[i-1]*nums[i]), nums[i]);
			minend[i]=Math.min(Math.min(maxend[i-1]*nums[i], minend[i-1]*nums[i]), nums[i]);
        }
        
        int max = maxend[0];
        for(int i = 1; i < nums.length; i++) {
        	if(maxend[i] > max) {
        		max = maxend[i];
        	}
        }
        return max;
    }
    
    public String customSortString(String S, String T) {
    	LinkedHashMap<Character, Integer> map = new LinkedHashMap<Character, Integer>();
    	for(int i = 0; i < T.length(); ++i) {
    		char ch = T.charAt(i);
    		if(map.containsKey(ch)) {
    			map.put(ch, map.get(ch) + 1);
    		}else {
    			map.put(ch, 1);
    		}
    	}
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0; i < S.length(); ++i) {
    		char ch = S.charAt(i);
    		if(map.containsKey(ch)) {
    			int count = map.get(ch);
    			while(count >= 1) {
    				sb.append(ch);
    				count--;
    			}
    			map.remove(ch);
    		}
    	}
    	for(int i = 0; i < T.length(); ++i) {
    		char ch = T.charAt(i);
    		if(map.containsKey(ch)) {
    			sb.append(ch);
    		}
    	}
    	return sb.toString();
    }
    
    public int numMatchingSubseq(String S, String[] words) {
        if(S == null || S.length() == 0 || words == null || words.length == 0) {
        	return 0;
        }
        int count = 0;
        for(String word : words) {
        	int leftW = 0, rightW = word.length() - 1;
        	int leftS = 0, rightS = S.length() - 1;
        	
        	while(leftW <= rightW && leftS <= rightS) {
        		if(S.charAt(leftS) == word.charAt(leftW)) {
        			leftW++;
        			leftS++;
        		}else {
        			leftS++;
        		}
        		if(S.charAt(rightS) == word.charAt(rightW)) {
        			rightS--;
        			rightW--;
        		}else {
        			rightS--;
        		}
        	}
        	
        	if(leftW > rightW) {
        		count++;
        	}
        }
        
        return count;
    }	
    
}





















