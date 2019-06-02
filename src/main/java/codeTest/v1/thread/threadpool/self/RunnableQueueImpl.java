package codeTest.v1.thread.threadpool.self;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 线程任务队列
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年5月16日
 */
public class RunnableQueueImpl implements RunnableQueue{

	private int limit;
	private ThreadPool threadPool;
	private DenyPolicy denyPolicy;
	
	private Queue<Runnable> queue;
	
	public RunnableQueueImpl(int limit, ThreadPool threadPool, DenyPolicy denyPolicy) {
		super();
		this.limit = limit;
		this.threadPool = threadPool;
		this.denyPolicy = denyPolicy;
		// 链表方式的list没有大小设置
		queue = new LinkedList<>();
	}

	@Override
	public synchronized void offer(Runnable runnable) {
		if(queue.size() > limit){
			denyPolicy.reject(runnable, threadPool);
		}else{
			queue.add(runnable);
			this.notifyAll();
		}
	}

	@Override
	public synchronized Runnable take() {
		// 如果没有可以执行的任务，等待
		while(queue.isEmpty()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return queue.poll();
	}

	@Override
	public synchronized int size() {
		return queue.size();
	}

}
