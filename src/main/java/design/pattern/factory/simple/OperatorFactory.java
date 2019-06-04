package design.pattern.factory.simple;

import design.pattern.factory.Operator;
import design.pattern.factory.OperatorAdd;
import design.pattern.factory.OperatorSub;

/**
 * 简单工厂模式
 * 
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 下午3:13:21
 */
public class OperatorFactory {
	/**
	 * 简单工厂就一个静态方法, 根据参数类型生成不同类型的对象
	 * @param type
	 * @return
	 * Object 
	 * @date: 2019年1月23日 下午3:25:39
	 */
	public static Operator createInstance(String type) {
		Operator operator = null;
		switch (type) {
		case "+": {
			operator = new OperatorAdd();
			break;
		}
		case "-": {
			operator = new OperatorSub();
			break;
		}
		case "*": {
			operator = new OperatorAdd();
			break;
		}
		default: {

		}
		}

		return operator;
	}
}
