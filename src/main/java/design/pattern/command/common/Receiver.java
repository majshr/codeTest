package design.pattern.command.common;

/**
 * 命令的最终执行者(考肉串师傅)
 * 知道如何实施与执行一个与请求相关的操作, 任何类都可能作为一个接收者
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年2月12日 下午3:24:56
 */
public class Receiver {
	public void action() {
		System.out.println("执行请求!");
	}
}
