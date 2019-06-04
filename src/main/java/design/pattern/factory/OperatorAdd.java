package design.pattern.factory;

/**
 * 加法操作
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 下午3:30:13
 */
public class OperatorAdd implements Operator {

	@Override
	public int getResult(int a, int b) {
		return a + b;
	}

}
