package design.pattern.decorator.example;

public class Person {
	private String name;
	
	public Person(String name) {
		this.name = name;
	}
	
	public Person() {
		
	}
	
	/**
	 * 装扮方法
	 * @date: 2019年1月23日 上午10:47:39
	 */
	public void show() {
		System.out.println("装扮  " + name);
	}
}
