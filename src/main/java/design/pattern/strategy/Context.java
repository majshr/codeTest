package design.pattern.strategy;

/**
 * Context类
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月21日 下午7:15:40
 */
public class Context {
	private Strategy strategy;
	
	/**
	 * 1, 初始化, 传入具体策略
	 * @param strategy
	 */
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	/**
	 * 2, 策略模式与简单工厂结合, 获取具体策略
	 * @param type
	 */
	public Context(String type) {
		this.strategy = getStrategy(type);
	}
	
	/**
	 * 执行算法
	 */
	public void contextAlgorithm() {
		strategy.algorithm();
	}
	
	/**
	 * 简单工厂方法, 根据类型获取实例对象
	 * @param type
	 * @return Strategy 
	 * @date: 2019年1月21日 下午7:13:10
	 */
	public Strategy getStrategy(String type) {
		Strategy s = null;
		switch (type) {
		case "A": {
			s = new StrategyImplA();
			break;
		}
		case "B": {
			s = new StrategyImplB();
			break;
		}
		default: {
			s = new StrategyImplA();
		}
		}
		return s;
	}
}
