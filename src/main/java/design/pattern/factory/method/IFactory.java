package design.pattern.factory.method;
/**
 * 工厂方法模式, 要为每种生产对象提供一个工厂
 * 工厂接口
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 下午3:37:44
 */

import design.pattern.factory.Operator;

public interface IFactory {
	Operator createOperator();
}
