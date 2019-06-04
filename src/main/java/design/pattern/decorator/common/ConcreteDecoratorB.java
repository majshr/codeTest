package design.pattern.decorator.common;

/**
 * 装饰器类B
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 上午10:29:15
 */
public class ConcreteDecoratorB extends Decorator{
	
	public ConcreteDecoratorB(Component component) {
		this.component = component;
	}
	
	/**
	 * 装饰添加一个执行前的行为, 本类独有方法
	 * void 
	 * @date: 2019年1月23日 上午10:30:55
	 */
	private void addBehavior() {
		System.out.println("装饰执行前行为!");
	}

	@Override
	public void operation() {
		addBehavior();
		
		component.operation();
	}
	
	
}
