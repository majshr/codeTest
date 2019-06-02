package codeTest.v1.thread.threadpool.self;

/**
 * 拒绝策略 <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author bhz（maj）
 * @since 2019年5月16日
 */
public interface DenyPolicy {
	/**
	 * 拒绝Runnable <B>方法名称：</B><BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @param runnable
	 * @param pool
	 */
	void reject(Runnable runnable, ThreadPool pool);

	/**
	 * 拒绝就什么都不做
	 */
	class IgnoreDenyPolicy implements DenyPolicy {

		@Override
		public void reject(Runnable runnable, ThreadPool pool) {

		}
	}

	/**
	 * 该拒绝策略会向任务提交者抛出异常 <B>系统名称：</B><BR>
	 * <B>模块名称：</B><BR>
	 * <B>中文类名：</B><BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @author bhz（maj）
	 * @since 2019年5月16日
	 */
	class AbortDenyPolicy implements DenyPolicy {

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			throw new RuntimeException("The runnable " + runnable + "will be abort");
		}
	}
	
	/**
	 * 拒绝策略就是如果线程池还没有关闭，该提交的线程完成先*/
    class RunnerDenyPolicy implements DenyPolicy {
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            if (!threadPool.isShutDown()) {
                runnable.run();
            }
        }
    }
}
