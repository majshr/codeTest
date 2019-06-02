package codeTest.v1.test;

import java.util.Arrays;
import java.util.List;

public class CodeTest {
	static class Inner{
		static int one = 5;
		static final int two = 5;
		static final int three = new Integer(5);
	}
	public static void main(String[] args) {
		System.out.println(CodeTest.Inner.one);
		System.out.println(CodeTest.Inner.two);
		System.out.println(CodeTest.Inner.three);
		
//		int[] test = new int[]{1, 2, 3, 4};
		int[] test = {1, 2, 3, 4};
		List<int[]> list = Arrays.asList(test);
		System.out.println(list.size());
		
		
		
		
		
		
		
		
	}
}
