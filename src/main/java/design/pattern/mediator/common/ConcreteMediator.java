package design.pattern.mediator.common;
/**
 * 具体中介者
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月14日 上午10:44:45
 */
public class ConcreteMediator extends Mediator{
	
	/**中介者直到所有的同事对象*/
	private ConcreteColleague1 colleague1;
	private ConcreteColleague2 colleague2;

	@Override
	public void send(String message, Colleague colleague) {
		if(colleague == colleague1) {
			colleague2.Notify(message);
		}else {
			colleague1.Notify(message);
		}
	}

}
