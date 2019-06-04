package design.pattern.decorator.common;

/**
 * 具体装饰类A
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 上午10:21:26
 */
public class ConcreteDecoratorA extends Decorator{

	/**
	 * 装饰一个状态
	 */
	private String addedState;
	
	public ConcreteDecoratorA(Component component) {
		this.component = component;
	}
	
	@Override
	public void operation() {
		this.addedState = "add";
		// 原来的操作
		component.operation();
	}

}
