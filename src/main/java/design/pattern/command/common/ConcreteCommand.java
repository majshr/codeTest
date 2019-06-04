package design.pattern.command.common;
/**
 * 将一个接收者对象绑定于一个动作, 调用接收者相应的操作, 以实现Execute
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午3:21:51
 */
public class ConcreteCommand extends Command{

	public ConcreteCommand(Receiver receiver) {
		super(receiver);
	}

	@Override
	public void execute() {
		receiver.action();
	}

}
