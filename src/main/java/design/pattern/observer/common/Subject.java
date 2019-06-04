package design.pattern.observer.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题, 通知者
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月25日 上午11:42:45
 */
public class Subject {
	/**
	 * 观察者
	 */
	private List<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * 增加观察者
	 * @param observer
	 * void 
	 * @date: 2019年1月25日 上午11:45:57
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	/**
	 * 移除观察者
	 * @param observer
	 * void 
	 * @date: 2019年1月25日 下午2:21:27
	 */
	public void detach(Observer observer) {
		observers.remove(observer);
	}
	
	public void notice() {
		for(Observer observer : observers) {
			observer.update();
		}
	}
}
