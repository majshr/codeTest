package design.pattern.memorandum.common;

public class Main {
	public static void main(String[] args) {
		Originator originator = new Originator();
		originator.setState("on");
		originator.show();
		
		Caretaker caretaker = new Caretaker();
		caretaker.setMemento(originator.createMemento());
		
		originator.setState("over");
		originator.show();
		
		originator.setMemento(caretaker.getMemento());
		originator.show();
	}
}

/**
 * 发起人类
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月29日 下午6:02:32
 */
class Originator{
	private String state; // 需要保存的属性, 可能有多个
	
	public void show() {
		System.out.println("i am " + state);
	}
	
	/**
	 * 创建备忘录
	 * @return
	 * Memento 
	 * @date: 2019年1月29日 下午6:07:17
	 */
	public Memento createMemento() {
		return new Memento(state);
	}
	
	/**
	 * 恢复备忘录, 将备忘录中信息恢复
	 * @param memento
	 * void 
	 * @date: 2019年1月29日 下午6:07:46
	 */
	public void setMemento(Memento memento) {
		state = memento.getState();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

/**
 * 备忘录类, 与发起人有相同的信息, 用于记录中间状态
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月29日 下午6:03:03
 */
class Memento{
	private String state;

	public Memento(String state) {
		super();
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}

/**
 * 管理者类, 得到或设置备忘录
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月29日 下午6:05:06
 */
class Caretaker{
	private Memento memento;

	public Memento getMemento() {
		return memento;
	}

	public void setMemento(Memento memento) {
		this.memento = memento;
	}
	
}
