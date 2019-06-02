package codeTest.v1.thread.threadpool.self;

/**
 * 自定义线程池接口
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年5月16日
 */
public interface ThreadPool {
	/**线程池执行任务*/
    void execute(Runnable runnable);

    /**关闭线程池*/
    void shutDown();

    /**获取线程池初始化大小*/
    int getInitSize();

    /**获取线程池的最大数*/
    int getMaxSize();

    /**获取任务队列的大小*/
    int getQueueSize();

    /**获取线程池活跃的线程数*/
    int getActivityCount();

    /**判断线程池是否关闭*/
    boolean isShutDown();

    /**获取线程池中的核心数*/
    int getCoreSize();
}
