package design.pattern.decorator.common;

/**
 * 具体对象, 可以给这个对象添加装饰职责
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 上午10:01:32
 */
public class ConcreteComponent implements Component {

	@Override
	public void operation() {
		System.out.println("ConcreteComponent 执行....");
	}

}
