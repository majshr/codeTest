package design.pattern.mediator.common;
/**
 * 中介者抽象
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午5:01:22
 */
public abstract class Mediator {
	
	/**
	 * 抽象发送消息方法, 得到同事对象和发送信息
	 * @param message
	 * void 
	 * @date: 2019年2月12日 下午4:58:05
	 */
	public abstract void send(String message, Colleague colleague);
}
