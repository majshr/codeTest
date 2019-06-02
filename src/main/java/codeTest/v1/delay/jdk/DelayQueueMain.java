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

		// 当前时间ms
		long start = System.nanoTime();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			// 延迟随机时间
			int r = random.nextInt(10);
			System.out.println("随机数" + r);
			
			// 从现在起, 延迟r秒(r秒转换为纳秒, 再加上现在的纳秒时间, 为到的那个时间)
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
