package design.pattern.builder.common;

public class Main {
	public static void main(String[] args) {
		Builder b1 = new ConcreteBuilder1();
		Builder b2 = new ConcreteBuilder2();
		
		Director director = new Director();
		
		Product product1 = director.construct(b1);
		Product product2 = director.construct(b2);
	}
}
