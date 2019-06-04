package design.pattern.strategy;

public class Main {
	public static void main(String[] args) {
		/**
		 * 根据不同策略对象, 使用不同策略
		 */
		Context context = new Context(new StrategyImplA());
		context.contextAlgorithm();
		
		context = new Context(new StrategyImplB());
		context.contextAlgorithm();
	}
}
