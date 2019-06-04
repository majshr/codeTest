package design.pattern.factory;

/**
 * 减法操作
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 下午3:31:11
 */
public class OperatorSub implements Operator {

	@Override
	public int getResult(int a, int b) {
		return a - b;
	}

}
