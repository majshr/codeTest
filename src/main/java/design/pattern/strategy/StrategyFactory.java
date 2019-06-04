package design.pattern.strategy;
/**
 * 策略简单工厂类
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年1月21日 下午7:11:44
 */
public class StrategyFactory {
	
	public static Strategy getStrategy(String type) {
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
