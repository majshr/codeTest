package design.pattern.facade;
/**外观模式, 为子系统中的一组接口提供一个一致的界面, 此模式定义一个高层接口, 这个接口使得这一子系统更容易使用*/
/***
 * 外观, 包装各种子系统接口的调用
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月24日 下午2:36:53
 */
public class Facade {
	SubSystemA a;
	SubSystemB b;
	SubSystemC c;
	
	public Facade() {
		a = new SubSystemA();
		b = new SubSystemB();
		c = new SubSystemC();
	}
	
	public void methodOne() {
		a.methodA();
		b.methodB();
	}
	
	public void methodTwo() {
		a.methodA();
		c.methodC();
	}
}

/**不同子系统, 有不同的方法*/
class SubSystemA{
	public void methodA() {
		System.out.println("子系统A方法调用.");
	}
}
class SubSystemB{
	public void methodB() {
		System.out.println("子系统B方法调用.");
	}
}
class SubSystemC{
	public void methodC() {
		System.out.println("子系统B方法调用.");
	}
}
