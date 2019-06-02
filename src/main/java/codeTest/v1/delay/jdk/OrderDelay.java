package codeTest.v1.delay.jdk;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 订单对象，需要实现Delayed比较
 * @author maj
 *
 */
public class OrderDelay implements Delayed{

	/**
	 * 订单ID
	 */
	private String orderId;
	
	/**
	 * 订单过期时间, 时间为纳秒
	 */
	private long timeout;
	
	public OrderDelay(String orderId, long timeout) {
		super();
		this.orderId = orderId;
		this.timeout = timeout;
	}

	/**
	 * 延迟队列按时间排序, 与其他队列元素时间比较
	 */
	@Override
	public int compareTo(Delayed o) {
		if (this == o) {
			return 0;
		}
		OrderDelay t = (OrderDelay) o;

		long d = (getDelay(TimeUnit.NANOSECONDS) - t.getDelay(TimeUnit.NANOSECONDS));

		return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
	}

	/**
	 * 距离自定义的超时时间还有多少
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		// 将给定单元的时间段转换到此单元。从较细粒度到较粗粒度的舍位转换，这样会失去精确性。例如，将 999 毫秒转换为秒的结果为 0。
		// 此单元中的转换时间段；如果转换将负溢出，则返回 Long.MIN_VALUE；如果转换将正溢出，则返回 Long.MAX_VALUE
		return unit.convert(timeout - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	/**
	 * 打印删除信息
	 */
	public void print() {
		System.out.println(toString() + "  我要被删除了!");
	}

	@Override
	public String toString() {
		return orderId + "    " + timeout;
	}
	public static void main(String[] args) {
		System.out.println("你好");
	}
}























