package codeTest.v1.test;

public class TemplateTest<T, V> {
	/**
	 * 成员方法泛型必须在类上声明
	 * @param val
	 */
	public void methodA(T val){
		
	}
	/**
	 * 静态方法泛型, 必须在方法本身上声明, 不能用类的
	 * @param val
	 */
	public static <M> void methodB(M val){
		
	}
}
