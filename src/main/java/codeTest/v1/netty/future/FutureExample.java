package codeTest.v1.netty.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 异步设计, 不断判断是否完成计算
 * @author maj
 *
 */
public class FutureExample {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Runnable task1 = new Runnable() {
			public void run() {
				System.out.println("i am task1 ......");
			}
		};
		
		Callable<Integer> task2 = new Callable<Integer>() {
			public Integer call() throws Exception {
				return new Integer(100);
			}
		};
		
		Future<?> f1 = executor.submit(task1);
		Future<Integer> f2 = executor.submit(task2);
		
		System.out.println("task1 is complete?" + f1.isDone());
		System.out.println("task2 is complete?" + f2.isDone());
		
		while(f1.isDone()){
			System.out.println("task1 complete");
			break;
		}
		while(f2.isDone()){
			System.out.println("task2 complete");
			break;
		}
		
		executor.shutdown();
	}
}






























