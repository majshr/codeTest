package design.pattern.command.example;

/**
 * 烤肉串师傅(命令接收者, 真正不同命令的执行者)
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午4:00:37
 */
public class Barbecuer {
	public void bakeMutton() {
		System.out.println("烤羊肉串!");
	}
	
	public void bakeChickenWing() {
		System.out.println("烤鸡翅!");
	}
}
