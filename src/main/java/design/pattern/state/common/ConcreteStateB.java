package design.pattern.state.common;

public class ConcreteStateB implements State {

	/**
	 * 设置下一状态为A
	 */
	@Override
	public void handle(Context context) {
		System.out.println("状态B对应行为--哦哦哦");
		context.setState(new ConcreteStateA());
	}

}
