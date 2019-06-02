package codeTest.v1.thread.threadpool.self;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 工厂创建一个新的线程*/
public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger GROUP_COUNTER  = new AtomicInteger(0);
    private static  AtomicInteger COUNTER = new AtomicInteger(1);
    private static final ThreadGroup group  = new ThreadGroup("MyThreadPool-" + GROUP_COUNTER.getAndIncrement());

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(group, r, "threadPool-" + COUNTER.getAndIncrement());
	}
}
