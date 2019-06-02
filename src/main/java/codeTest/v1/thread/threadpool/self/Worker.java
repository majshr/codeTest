package codeTest.v1.thread.threadpool.self;

/**
 * 工作线程
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年5月17日
 */
public class Worker implements Runnable{
	/**
	 * 任务队列，方便获取
	 */
	private RunnableQueue runnableQueue;
	
	private boolean running = true;
	
	public Worker(RunnableQueue queue){
		this.runnableQueue = queue;
	}

	@Override
	public void run() {
		// 取出任务，调用run方法
		while(running && !Thread.currentThread().isInterrupted()){
			Runnable task = runnableQueue.take();
			task.run();
		}
	}
	
	/**
	 * 终止线程(设置终止标志位)
	 * <B>方法名称：</B><BR>
	 * <B>概要说明：</B><BR>
	 */
	public void stop(){
		running = false;
	}
}
