package design.pattern.command.example;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务员(对应invoker, 收集发送命令)
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午4:08:12
 */
public class Waiter {
	private Command command;
	
	/**先记录点的什么串, 点完了一起通知*/
	private List<Command> commands = new ArrayList<>();
	
	/**
	 * 不管用户想要什么烤肉, 都是"命令", 只管记录订单, 然后通知'烤肉串师傅'即可
	 * @param command
	 * void 
	 * @date: 2019年2月12日 下午4:09:03
	 */
	public void setOrder(Command command) {
		this.command = command;
		commands.add(command);
	}
	
	/**
	 * 通知执行
	 * void 
	 * @date: 2019年2月12日 下午4:10:58
	 */
	public void notifyExe() {
		command.executeCommond();
	}
}
