package codeTest.v1.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Test {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService ser = Executors.newFixedThreadPool(1);
		CallableImpl impl = new CallableImpl();
		
		FutureTask<Integer> ft = new FutureTask<Integer>(impl);
		
		Future<Integer> f = (Future<Integer>) ser.submit(ft);
		
		new Thread(ft).start();
		
//		Integer num = f.get();
		int num2 = ft.get();
//		System.out.println(num);
		System.out.println(num2);
		ser.shutdown();
		
	}
}
class CallableImpl implements Callable<Integer>{

	public Integer call() throws Exception {
		return 1000;
	}


}