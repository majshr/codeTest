package design.pattern.responsibility.chain.common;

public class ConcreteHandler2  extends Handler{

	@Override
	public void handleRequest(int request) {
		if(request >= 10 && request < 20) {
			System.out.println("处理请求, 10-20");
		}else if(successor != null) {
			// 转移到下一位执行
			successor.handleRequest(request);
		}
	}
}
