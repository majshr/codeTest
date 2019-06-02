package codeTest.v1.thread.threadpool.self;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.TimeoutUtils;

public class BaseThreadPool extends Thread implements ThreadPool {

	/** 初始化线程数 */
	private int initSize;

	/** 最大工作线程数 */
	private int maxSize;

	/** 核心线程数 */
	private int coreSize;

	/** 当前活跃线程数 */
	private int activityCount = 0;

	/** 指定任务队列的大小数 */
	private int queueSize;

	/** 创建工作线程的工厂，在构造方法由线程池规定好 */
	private ThreadFactory threadFactory;

	/** 1. 任务队列，在构造方法由线程池规定好 */
	private RunnableQueue runnableQueue;

	/** 2. 工作队列 */
	private final static Queue<ThreadTask> threadQueue = new ArrayDeque<>();

	/** 3. 本线程池默认的拒绝策略 */
	private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.IgnoreDenyPolicy();

	/** 4. 默认的线程工厂 */
	private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();
	
	private final static long DEFAULT_KEEP_ALIVE_TIME = 0;

	/** 线程池是否关闭，默认为false */
	boolean isShutdown = false;

	private long keepAliveTime;

	private TimeUnit timeUnit;

	/**
	 * 工作线程和内部任务绑定在一起 <B>系统名称：</B><BR>
	 * <B>模块名称：</B><BR>
	 * <B>中文类名：</B><BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @author bhz（maj）
	 * @since 2019年5月17日
	 */
	class ThreadTask {
		Thread thread;
		Worker worker;

		public ThreadTask(Thread thread, Worker worker) {
			this.thread = thread;
			this.worker = worker;
		}
	}

	public BaseThreadPool(int initSize, int maxSize, int coreSize, int queueSize) {
		this(initSize, maxSize, coreSize, queueSize, DEFAULT_THREAD_FACTORY, DEFAULT_DENY_POLICY, DEFAULT_KEEP_ALIVE_TIME, TimeUnit.SECONDS);
	}

	public BaseThreadPool(int initSize, int maxSize, int coreSize, int queueSize, ThreadFactory threadFactory,
			DenyPolicy denyPolicy, long keepAliveTime, TimeUnit timeUnit) {
		this.initSize = initSize;
		this.maxSize = maxSize;
		this.coreSize = coreSize;
		this.threadFactory = threadFactory;
		// 自定义任务的队列
		this.runnableQueue = new RunnableQueueImpl(queueSize, this, denyPolicy);
		this.keepAliveTime = keepAliveTime;
		this.timeUnit = timeUnit;
		this.init();
	}
	
	@Override
	public void run() {
		// 如果没被中断
		while(!isShutdown && !isInterrupted()){
			try{
				// 如果等待时，线程池被中断，关闭线程池
				timeUnit.sleep(keepAliveTime);
			} catch(InterruptedException e){
				isShutdown = true;
				continue;
			}
			
			// 同步代码块，保证每次访问都是最新数据
			synchronized (this) {
				// 如果线程池被关闭，退出
				if(isShutdown){
					break;
				}
				
				// 任务队列不为空(存在为被执行的任务)，且未达到核心线程数，直接增加线程数到最大
				// 如果活跃线程数大于核心的，就先不新增线程了
				if(runnableQueue.size() > 0 && activityCount < coreSize){
					for(int i = runnableQueue.size(); i < maxSize; i++){
						newThread();
					}
				}
				
				// 如果任务队列为空（没有未执行的任务），核心线程之外的线程就可以销毁了
				if(runnableQueue.size() == 0 && activityCount > coreSize){
					removeWhrker();
				}
			}
		}
		
	}
	
	/**
	 * 清除多余核心线程数的线程
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：</B><BR>
	 */
	private void removeWhrker(){
		activityCount--;
		ThreadTask task = threadQueue.remove();
		task.worker.stop();
	}

	/**
	 * 线程池初始化 <B>方法名称：</B><BR>
	 * <B>概要说明：</B><BR>
	 */
	private void init() {
		// 启动本地线程池
		this.start();
		
		// 初始化initSize个线程在线程池中
		for(int i = 0; i < initSize; i++){
			newThread();
		}
	}
		
	/**
	 * 启动一个工作线程
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：</B><BR>
	 */
	private void newThread() {
		// 工作线程的run方法，会从任务队列取任务，并运行
		Worker worker = new Worker(runnableQueue);
		Thread thread = threadFactory.newThread(worker);
		
		// 将工作线程和任务绑定在一起
		ThreadTask threadTask = new ThreadTask(thread, worker);
		threadQueue.offer(threadTask);
		
		this.activityCount++;
		
		// 启动
		thread.start();
	}

	@Override
	public void execute(Runnable runnable) {
		if(isShutdown){
			throw new RuntimeException("线程池关闭了！");
		}
		// 如果线程池没事，放到任务队列。如果任务队列满了，会执行相应拒绝策略
		runnableQueue.offer(runnable);
	}

	@Override
	public void shutDown() {
		// 如果已经被关闭
		if(isShutdown){
			return;
		}

		isShutdown = true;
		/*全部线程停止工作*/
        for (ThreadTask task: threadQueue) {
        	// 设置自定义中断标志位
            task.worker.stop();
            // 设置线程中断标志
            task.thread.interrupt();
        }
	}

	@Override
	public int getInitSize() {
		if(isShutdown){
			throw new RuntimeException("线程池已经终止。");
		}
		return initSize;
	}

	@Override
	public int getMaxSize() {
		if(isShutdown){
			throw new RuntimeException("线程池已经终止。");
		}
		return maxSize;
	}

	@Override
	public int getQueueSize() {
		if(isShutdown){
			throw new RuntimeException("线程池已经终止。");
		}
		return queueSize;
	}

	@Override
	public int getActivityCount() {
		if(isShutdown){
			throw new RuntimeException("线程池已经终止。");
		}
		return activityCount;
	}

	@Override
	public boolean isShutDown() {
		return isShutdown;
	}

	@Override
	public int getCoreSize() {
		return coreSize;
	}
}