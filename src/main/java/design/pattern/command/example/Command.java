package design.pattern.command.example;

/**
 * 抽象命令
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午3:59:10
 */
public abstract class Command {
	/**
	 * 真实执行命令者
	 */
	protected Barbecuer receiver;
	
	public Command(Barbecuer receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * 执行命令方法
	 */
	public abstract void executeCommond();
}
