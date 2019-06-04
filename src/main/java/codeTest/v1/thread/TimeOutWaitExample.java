package codeTest.v1.thread;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeOutWaitExample {
	private static final Object lock = new Object();
	private static boolean ready = false;
	protected static final Random random = new Random();

	public static void waiter(final long timeOut) throws InterruptedException {
		if (timeOut < 0) {
			throw new InterruptedException();
		}
		long start = System.currentTimeMillis();
		long waitTime;
		long now;
		synchronized (lock) {

		}
	}

//	public static void main(String[] args) throws InterruptedException {
//		NestedMonitorLockoutDemo.main(null);
//	}
	
    public static void main(String[] args) throws ExecutionException, InterruptedException {
    	 
		/**
		 * 第一种方式:Future + ExecutorService 
		 */
/*		 Task task = new Task(); 
		 ExecutorService service = Executors.newCachedThreadPool(); 
		 Future<Integer> future =  service.submit(task); 
		 service.shutdown();*/

		/**
		 * 第二种方式: FutureTask + ExecutorService 
		 * public FutureTask(Callable) 构造方法能将Callable接口类转换为Runnable的
		 */
/*		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
		executor.submit(futureTask);
		executor.shutdown();
*/
		/**
		 * 第三种方式:FutureTask + Thread
		 * public FutureTask(Callable) 构造方法能将Callable接口类转换为Runnable的, 可以用Thread执行,
		 * 并将返回结果传给FutureTask对象
		 */
		// 新建FutureTask,需要一个实现了Callable接口的类的实例作为构造函数参数
		FutureTask<Integer> futureTask = new FutureTask<Integer>(new Task());
		// 新建Thread对象并启动
		Thread thread = new Thread(futureTask);
		thread.setName("Task thread");
		thread.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");

		// 调用isDone()判断任务是否结束
		if (!futureTask.isDone()) {
			System.out.println("Task is not done");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int result = 0;
		try {
			// 调用get()方法获取任务结果,如果任务没有执行完成则阻塞等待
			result = futureTask.get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("result is " + result);

	}

	/**继承Callable接口,实现call()方法,泛型参数为要返回的类型*/
	static class Task implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");
			int result = 0;
			for (int i = 0; i < 100; ++i) {
				//0+1+2+3+...+9+...
				result += i;
			}

			Thread.sleep(3000);
			return result;
		}
	}
}

/**
 * 一个线程可以执行多个任务而不是一个任务 TaskRunner是一个通用的任务执行器, 内部维护工作者线程相当于消费者线程,
 * submit()的执行线程相当于生产者线程.
 * 
 */

class TaskRunner {
	private final BlockingQueue<Runnable> channel;

	private volatile Thread workerThread;

	// 线程停止标记
	protected volatile boolean inUse = true;
	// 待处理任务计数器
	public final AtomicInteger reservations = new AtomicInteger(0);

	public TaskRunner(BlockingQueue<Runnable> channel) {
		this.channel = channel;
		this.workerThread = new WorkThread();
	}

	public TaskRunner() {
		this(new LinkedBlockingQueue<Runnable>());
	}

	public void startRunner() {
		if (workerThread != null) {
			workerThread.start();
		}
	}

	public void submit(Runnable task) throws InterruptedException {
		channel.put(task);
	}

	public void shutDown() {
		inUse = false;// 标记位标记为不使用了
		final Thread t = this.workerThread;
		if (t != null) { // 进行中断
			t.interrupt();
		}
	}

	class WorkThread extends Thread {
		@Override
		public void run() {
			Runnable task = null;
			try {
				for (;;) {
					task = channel.take();
					task.run();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class NestedMonitorLockoutDemo {
	private final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
	private int processed = 0;
	private int accepted = 0;

	public static void main(String[] args) throws InterruptedException {
		NestedMonitorLockoutDemo demo = new NestedMonitorLockoutDemo();
		// 1,新启一个线程通过doProcess方法不断从队列中获取数据
		demo.start();
		int i = 0;
		// 2,通过accept方法不断向队列中放数据
		while (i-- < 100000) {
			demo.accept("message" + i);
            Thread.sleep(new Random().nextInt(100));
		}
	}

	/** 如果阻塞队列的take或put方法阻塞了, 需要另一个方法唤醒, 但此时阻塞方法拿到了this锁, 另一个方法执行不了, 也就唤醒不了 */
	/**
	 * 向阻塞队列中存数据
	 */
	public synchronized void accept(String message) throws InterruptedException {
		// 不要在临界区内调用BlockingQueue的阻塞方法！那样会导致嵌套监视器锁死
		queue.put(message);
		accepted++;
	}

	/**
	 * 从阻塞队列中取数据
	 */
	protected synchronized void doProcess() throws InterruptedException {
		// 不要在临界区内调用BlockingQueue的阻塞方法！那样会导致嵌套监视器锁死
		String msg = queue.take();
		System.out.println("Process:" + msg);
		processed++;
	}

	public void start() {
		new WorkerThread().start();
	}

	public synchronized int[] getStat() {
		return new int[] { accepted, processed };
	}

	class WorkerThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					doProcess();
				}
			} catch (InterruptedException e) {
				;
			}
		}
	}
	
}

class CRC8 {  
	
	 
}  


