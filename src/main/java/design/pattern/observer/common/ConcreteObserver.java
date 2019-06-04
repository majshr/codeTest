package design.pattern.observer.common;

/**
 * 具体观察者
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月25日 下午2:35:18
 */
public class ConcreteObserver implements Observer {

	/**
	 * 观察者名字
	 */
	private String name;
	/**
	 * 订阅的主题
	 */
	private ConcreteSubject subject;
	/**
	 * 观察者的状态
	 */
	private String observerState;
	
	public ConcreteObserver(String name, ConcreteSubject subject) {
		super();
		this.name = name;
		this.subject = subject;
		this.observerState = subject.getSubjectState();
	}

	@Override
	public void update() {
		this.observerState = subject.getSubjectState();
		System.out.println("观察者状态更新!");
	}

}
