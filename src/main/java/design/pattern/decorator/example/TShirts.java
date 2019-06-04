package design.pattern.decorator.example;

/**
 * 衬衫装饰器
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月23日 上午10:52:00
 */
public class TShirts extends Finery{

	@Override
	public void show() {
		System.out.println("穿上TShirts...");
		basePerson.show();
	}
	
}
