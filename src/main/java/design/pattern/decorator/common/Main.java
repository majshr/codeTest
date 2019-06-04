package design.pattern.decorator.common;

public class Main {
	public static void main(String[] args) {
		// 自己
		Component component = new ConcreteComponent();
		
		// 装饰A
		Decorator decoratorA = new ConcreteDecoratorA(component);
		decoratorA.operation();
		
		// 再装饰B
		Decorator decoratorB = new ConcreteDecoratorB(decoratorA);
		decoratorB.operation();
	}
}
