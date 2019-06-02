package codeTest.v1.thread.threadpool.self;

/**
 * 任务队列接口
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年5月16日
 */
public interface RunnableQueue {
    /**
     * 把新增进来的任务添加到任务队列中
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     * @param runnable
     */
    void offer(Runnable runnable);

    /**
     * 从任务队列中获取任务，然后执行
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     * @return
     */
    Runnable take();

    /**
     * 查找任务队列的大小
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     * @return
     */
    int size();
}
