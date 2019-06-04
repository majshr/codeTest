package design.pattern.decorator.example;

/**
 * 大垮裤装饰器
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 上午10:55:37
 */
public class BigTrouser extends Finery{
	@Override
	public void show() {
		System.out.println("穿上了大垮裤....");
		basePerson.show();
	}
}
