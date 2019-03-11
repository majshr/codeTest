package codeTest.v1.jdk;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<Integer>(); 
		list1.add(1);
		list1.add(2);
		
		List<Integer> list2 = new ArrayList<Integer>(list1);
	}
}
