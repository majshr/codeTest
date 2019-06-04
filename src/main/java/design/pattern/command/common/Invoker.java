package design.pattern.command.common;

/**
 * 要求该命令执行这个请求(对应服务员)
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午3:23:02
 */
public class Invoker {
	private Command command;

	/**
	 * 执行请求
	 * 
	 * void 
	 * @date: 2019年2月12日 下午3:23:54
	 */
	public void executeCommand() {
		command.execute();
	}
	
	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
}
