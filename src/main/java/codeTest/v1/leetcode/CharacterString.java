package codeTest.v1.leetcode;
 
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
 
/**
 * 【字符串相关算法题】
   1.字符串反转
   2.判断两个字符串是否由相同的字符组成
   3.删除字符串中重复的字符
   4.字符串转化为数字
   5.统计一行字符里有多少单词
   6.按要求打印数组的排列情况
   7.输出字符串的所有组合
 */
public class CharacterString {
	
	public static void main(String[] args){
		String str = "nice to meet you";
		String str1 = "how are you";
		String str2 = "aaaabbc";
		String str3 = "abcbaaa";
		String str4 = "@#!%^&!ABVS ";
		String str5 = "ABVS@#!%^& !";
		String str6 = "aabbbccccddddd";
		string2Int("-0.0");
		string2Int("-0.1");
		string2Int("0.1234");
		string2Int("0.123");
		string2Int("1.1234567");
		string2Int("-123.123");
		string2Int("0.0");
		string2Int(".1234");
		string2Int("1234.");
		string2Int("123..4");
		System.out.println("字符串反转："+ CharacterString.reverse(str1));
		System.out.println("字符串反转："+ CharacterString.reverse(str));
		System.out.println("判断两个字符串是否组成相同：" + CharacterString.compareStr(str4, str5));
		System.out.println("判断两个字符串是否组成相同：" + CharacterString.compareStr2(str2, str3));
		System.out.println("删除字符串中重复的字符：" + CharacterString.removeDuplicate(str6));
		getWordCounts("You are my everything.");
		printByRules();
		
		String s = "abc";
		char[] c = s.toCharArray();
		StringBuffer sb = new StringBuffer("");
		int len = c.length;
		for (int i = 1; i <= len; i++) {
			printCombination(c, 0, i, sb);
		}
	}
	/**
	 * 字符串反转：
	 * 首尾交换法
	 */
	public static void swap(char[] charArr, int front, int end){
		while(front < end){
			 char tmp = charArr[end];
			 charArr[end] = charArr[front];
			 charArr[front] = tmp;
			 front++;
			 end--;
		}
	}
	/**
	 * 字符串反转
	 */
	public static String reverse(String str){
		char[] charArr = str.toCharArray();
		//对整个字符串进行反转
		CharacterString.swap(charArr, 0, charArr.length-1);
		int begin=0;
		//对每个单词进行反转
		for(int i=0; i<charArr.length; i++){
			if(charArr[i] == ' '){
				swap(charArr, begin, i-1);
				begin = i+1;
				continue;
			}
		}
		swap(charArr, begin, charArr.length-1);
		return new String(charArr);
	}
	
	/**
	 * 判断两个字符串是否组成相同
	 * 方法1：排序法
	 */
	public static boolean compareStr(String s1, String s2){
		byte[] b1 = s1.getBytes();
		byte[] b2 = s2.getBytes();
		Arrays.sort(b1);
		Arrays.sort(b2);
		s1 = new String(b1);
		s2 = new String(b2);
		if(s1.equals(s2)){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 字符串组成判断
	 * 方法2：空间换时间
	 * 假设字符串中只使用ASCII字符，用于ASCII字符共有266个（对应编码是0~255），
	 * 在实现时可以通过申请大小为266的数组来记录各个字符出现的次数，初始化为0，
	 * 遍历第一个字符串数组时，将字符串中字符对应的ASCII码值作为数组的下标，把对应数
	 * 组的元素加1，然后遍历第二个数组，把数组对应的元素值-1。如果最后数组中各个元素的
	 * 值都为0，则说明这两个数组是有相同字符组成。
	 * 本解法的前提是字符串只是用ASCII码能表示的字符。
	 */
	public static boolean compareStr2(String s1, String s2){
		byte[] b1 = s1.getBytes();
		byte[] b2 = s2.getBytes();
		
		int count[] = new int[256];
		for(int i=0; i<256; i++){
			//初始化每个元素值为0
			count[i] = 0;
		}
		for(int j=0; j<b1.length; j++){
			count[b1[j]-'0']++;
		}
		for(int q=0; q<b2.length; q++){
			count[b2[q]-'0']--;
		}
		for(int i=0; i<256; i++){
			if(count[i]!=0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 删除字符串中重复的字符 
	 * 暴力法
	 */
	public static String removeDuplicate(String s){
		char[] arr = s.toCharArray();
		int len = arr.length;
		for(int i=0; i<len; i++){
			if(arr[i]=='\0'){
				continue;
			}
			for(int j=i+1; j<len; j++){
				if(arr[j]=='\0'){
					continue;
				}
				//把重复的字符置为'\0'
				if(arr[i]==arr[j]){
					arr[j] = '\0';
				}
			}
		}
		//把'\0'去掉
		int length = 0;
		for(int i=0; i<len; i++){
			if(arr[i]!='\0'){
				arr[length++] = arr[i];
			}
		}
		return new String(arr, 0, length);
	}
	
	/**
	 * 字符串转化为数字
	 * 方法：注意处理小数点
	 */
	public static void string2Int(String str){
		double intData = 10;
		double floatData = 0.1;
		boolean flag = true; // 记录正负数
		int count = 0; // 记录小数点个数
		int index = 0; // 记录小数点下标位置
		float leftRes = 0;  // 小数点左边数
		float rightRes = 0; // 小数点右边数
		float result = 0;   // 转换后的结果
		
		char[] c = str.toCharArray();
	
		for(int i=0; i<c.length; i++){
			if( c[i]=='.' ){
				count++;
				index = i;
			}
		}
		// 小数点数大于1时 不合法
		if(count>1){
			System.out.println("输入数"+str+"为非法数字！");
			return;
		}
		// 不含小数点的整数
		if(count==0 && index==0){
			index = c.length;
		}
		
		/** 处理整数部分 **/
		for(int i=0; i<index; i++){
			char temp = c[i];
			if( temp =='-' ){
				flag = false;
				continue;
			}
			if( temp == '+' ){
				flag = true;
			}
			double tempInt = temp-'0';
			// 判断是否为0-9之间的数字
			if(tempInt >= 0 && tempInt <=9){
				leftRes = (float)(leftRes * intData +  tempInt);
			}
		}
//		System.out.println("小数点左边数：" + leftRes);
		
		/** 处理小数部分 **/ 
		for(int j=c.length-1; j>index; j--){
			char temp = c[j];
			double tempDecimal = temp - '0';
			if(tempDecimal >= 0 && tempDecimal <= 9){
				rightRes = (float)(rightRes*floatData + tempDecimal*floatData);
			}
		}
//		System.out.println("小数点右边数：" + rightRes);
		// 结果 == 整数+分数
		result = leftRes + rightRes;
		if(!flag){
			result = (-1)* result;
		}
		System.out.println("由字符串" + str + "转化后的数字为：" + result);
	}
	
	/**
	 * 统计一行字符里有多少单词
	 * 注意：一行开头的空格不算在内，多个空格等于一个空格。
	 * 若测试出一个字符是非空格，并且它前面的字符是空格，则单词数加1；
	 * 若测试出一个字符是非空格，而前面的仍然是非空格，则单词数不增加。
	 */
	public static void getWordCounts(String str){
		int count = 0;
		int word = 0;
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == ' '){
				word = 0;
			}
			else{
				if(word == 0){
					word = 1;
					count++;
				}
				else{
					continue;
				}
			}
		}
		System.out.println("字符串 " + str + "中共有" + count +"个单词！");
	}
	
	/**
	 * 按要求打印数组的排列情况
	 * 描述：
	 * 针对1,2,2,3,4,5这6个数字，写一个函数，打印出所有不同的排列，
	 * 例如：512234，,215432等，要求4不能在第三位，3,5不相连。
	 * 思路：将这六个点看做图的六个节点，对六个节点两两相连可以组成一个无向连通图
	 * 这六个数字的全排列可以看做是从六个节点出发深度遍历这个图所有可能路径的集合。
	 * 再将不符合条件的去掉。
	 */
	public static void printByRules(){
		Gragh gragh = new Gragh();
		Set<String> set = gragh.getAllCombination();
		Iterator<String> it = set.iterator();
		int i = 1;
		while (it.hasNext()) {
			String string = (String)it.next();
			System.out.println("第" + i + "个排列：" + string);
			i++;
		}
	}
	
	/**
	 * 输出字符串中的所有组合
	 * 在求一个字符串中所有字符的组合的时候，针对一个字符，有两种情况，假设在长度为n的字符串中选择长度为m的组合字符串，
	 * 第一是选择长度为n的字符串中的第一个字符，那么要在其余的长度n-1的字符串中选择m-1个字符
	 * 第二是不选择长度为n的字符串中的第一个字符，那么要在其余的长度n-1的字符串中选择m个字符
	 * 递归结束的条件就是，当m为0，即从字符串中不再选出字符的时候，这个时候已经找到了m个字符的组合，输出即可。
	 * 还有一个条件是，当输入的字符串是串，自然是不能从中选出任何字符的。
	 */
	public static void printCombination(char[]c, int begin, int len, StringBuffer sb){
		if(len==0){
			System.out.print(sb+" ");
			return;
		}
		if(begin==c.length){
			return;
		}
		sb.append(c[begin]);
		printCombination(c, begin+1, len-1, sb);
		sb.deleteCharAt(sb.length()-1);
		printCombination(c, begin+1, len, sb);
	}
}
 
class Gragh{
	private int[] numbers = new int[]{1,2,2,3,4,5};
	private int n = numbers.length;
	private boolean[] visited  = new boolean[n];  //是否被访问
	private int[][] gragh = new int[n][n];  // 图的二维数组表示
	private String combination = "";  //数字的组合
	
	public Set<String> getAllCombination()
	{
		buildGragh();
		Set<String> set = new HashSet<String>(); //存放左右排列结果的集合
		for (int i = 0; i < n; i++) {
			this.depthFirstSearch(i, set);
		}
		return set;
	}
	
	/**
	 * 构造图 
	 */ 
	private void buildGragh(){
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				if(i==j)
				{
					gragh[i][j] = 0;
				}
				else{
					gragh[i][j] = 1; //连通
				}
			}
		}
		// 确保3和5是不相连的
		gragh[3][5] = 0;
		gragh[5][3] = 0;
	}
	
	/**
	 * 从每个节点开始深度遍历
	 */ 
	private void depthFirstSearch(int start, Set<String> set){
		visited[start] = true;
		combination = combination + numbers[start];
		if(combination.length()==n)   // 节点遍历完成
		{
			// 4不出现在第三个位置,则符合要求，存入结果集中
			if (combination.indexOf("4")!=2) {
				set.add(combination);
				System.out.println("combination=="+combination);
			}
		}
		// 未访问的访问
		for (int j = 0; j < n; j++) {
			if (gragh[start][j]==1 && visited[j]==false) {
				depthFirstSearch(j, set);
			}
		}
		combination = combination.substring(0, combination.length()-1);
		System.out.println("combination:" + combination);
		visited[start] = false;
		System.out.println("visited["+start+"]--> :  " + visited[start]);
	}
}
