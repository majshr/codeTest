package design.pattern.state.common;

public class ConcreteStateA implements State {

	@Override
	public void handle(Context context) {
		System.out.println("状态A对应行为--啊啊啊!");
		context.setState(new ConcreteStateB());
	}

}
