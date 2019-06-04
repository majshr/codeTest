package design.pattern.decorator.common;

/**
 * 抽象装饰类, 继承了Component, 从外类来装饰Component
 * Component无需知道Decorator的存在
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 上午10:21:46
 */
public abstract class Decorator implements Component{
	protected Component component;
	
}
