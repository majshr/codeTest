package design.pattern.state.common;
/**
 * 定义当前状态
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月29日 上午9:50:39
 */
public class Context {
	/**当前状态*/
	private State state;
	public Context(State state) {
		this.state = state;
	}
	
	/**
	 * 根据状态执行相应的行为
	 * 
	 * void 
	 * @date: 2019年1月29日 上午10:23:27
	 */
	public void request() {
		state.handle(this);
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	public static void main(String[] args) {
		// 设置初始状态为A
		Context c = new Context(new ConcreteStateA());
		
		c.request();
		c.request();
		c.request();
		c.request();
	}
	
}



















