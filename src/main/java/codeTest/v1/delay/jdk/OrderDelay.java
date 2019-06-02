package codeTest.v1.delay.jdk;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * ����������Ҫʵ��Delayed�Ƚ�
 * @author maj
 *
 */
public class OrderDelay implements Delayed{

	/**
	 * ����ID
	 */
	private String orderId;
	
	/**
	 * ��������ʱ��, ʱ��Ϊ����
	 */
	private long timeout;
	
	public OrderDelay(String orderId, long timeout) {
		super();
		this.orderId = orderId;
		this.timeout = timeout;
	}

	/**
	 * �ӳٶ��а�ʱ������, ����������Ԫ��ʱ��Ƚ�
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
	 * �����Զ���ĳ�ʱʱ�仹�ж���
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		// ��������Ԫ��ʱ���ת�����˵�Ԫ���ӽ�ϸ���ȵ��ϴ����ȵ���λת����������ʧȥ��ȷ�ԡ����磬�� 999 ����ת��Ϊ��Ľ��Ϊ 0��
		// �˵�Ԫ�е�ת��ʱ��Σ����ת������������򷵻� Long.MIN_VALUE�����ת������������򷵻� Long.MAX_VALUE
		return unit.convert(timeout - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	/**
	 * ��ӡɾ����Ϣ
	 */
	public void print() {
		System.out.println(toString() + "  ��Ҫ��ɾ����!");
	}

	@Override
	public String toString() {
		return orderId + "    " + timeout;
	}
	public static void main(String[] args) {
		System.out.println("���");
	}
}























