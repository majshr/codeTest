package design.pattern.state.common;
/**
 * 抽象状态类, 定义一个接口以封装与Context的一个特定状态相关的行为
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月28日 下午5:41:55
 */
public interface State {
	/**
	 * 与状态相关的行为
	 * @param context
	 * void 
	 * @date: 2019年1月29日 上午10:12:08
	 */
	public void handle(Context context);
}
