package codeTest.v1.delay.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueMain {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>(); 
		list.add("00000001");
		list.add("00000002");
		list.add("00000003");
		list.add("00000004");
		list.add("00000005");

		DelayQueue<OrderDelay> queue = new DelayQueue<>();

		// ��ǰʱ��ms
		long start = System.nanoTime();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			// �ӳ����ʱ��
			int r = random.nextInt(10);
			System.out.println("�����" + r);
			
			// ��������, �ӳ�r��(r��ת��Ϊ����, �ټ������ڵ�����ʱ��, Ϊ�����Ǹ�ʱ��)
			queue.put(new OrderDelay(list.get(i), 
					start + TimeUnit.NANOSECONDS.convert(r, TimeUnit.SECONDS)));
		}
		
		for(int i = 0; i < 5; i++){
			try {
				queue.take().print();
				System.out.println("After " + 
				TimeUnit.SECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS) + " MilliSeconds");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
