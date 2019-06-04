package design.pattern.mediator.common;

/**
 * 同事抽象类
 * 
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午5:00:00
 */
public abstract class Colleague {
	/**
	 * 中介者对象
	 */
	protected Mediator mediator;

	/**
	 * 构造方法, 设置中介者对象
	 * @param mediator
	 */
	public Colleague(Mediator mediator) {
		super();
		this.mediator = mediator;
	}
}
