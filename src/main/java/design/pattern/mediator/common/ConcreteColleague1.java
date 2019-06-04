package design.pattern.mediator.common;
/**
 * 同事1
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午5:04:49
 */
public class ConcreteColleague1 extends Colleague{

	public ConcreteColleague1(Mediator mediator) {
		super(mediator);
	}

	public void send(String message) {
		mediator.send(message, this);
	}
	
	public void Notify(String message) {
		System.out.println("同事1得到消息: " + message);
	}
}
