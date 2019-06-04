package design.pattern.command.common;

/**
 * 执行操作的接口
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午3:19:55
 */
public abstract class Command {
	protected Receiver receiver;
	
	public Command(Receiver receiver) {
		this.receiver = receiver;
	}
	
	abstract public void execute();
}
