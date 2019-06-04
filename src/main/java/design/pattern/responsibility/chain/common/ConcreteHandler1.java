package design.pattern.responsibility.chain.common;

public class ConcreteHandler1 extends Handler{

	@Override
	public void handleRequest(int request) {
		if(request >= 0 && request < 10) {
			System.out.println("处理请求, 0-10");
		}else if(successor != null) {
			// 转移到下一位执行
			successor.handleRequest(request);
		}
	}

}
