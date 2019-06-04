package design.pattern.responsibility.chain.common;

public abstract class Handler {
	/**
	 * 责任继承者
	 */
	protected Handler successor;
	
	/**
	 * 设置下一个handler
	 * @param successor
	 * void 
	 * @date: 2019年2月12日 下午4:32:50
	 */
	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}
	
	/**
	 * 处理请求的抽象方法
	 * @param request
	 * void 
	 * @date: 2019年2月12日 下午4:32:47
	 */
	public abstract void handleRequest(int request); 
}
