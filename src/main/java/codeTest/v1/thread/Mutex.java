package codeTest.v1.thread;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * 同步类在实现时一般都将自定义同步器（sync）定义为内部类，供自己使用；而同步类自己（Mutex）则实现某个接口，对外服务。当然，接口的实现要
 * 直接依赖sync，它们在语义上也存在某种对应关系！！而sync只用实现资源state的获取-释放方式tryAcquire-tryRelelase，至于线程的排队、
 * 等待、唤醒等，上层的AQS都已经实现好了，我们不用关心。
 */
public class Mutex implements Lock, Serializable{
	private static final long serialVersionUID = 1L;
	
	/**自定义同步器*/
	private static class Sync extends AbstractQueuedSynchronizer{
		
		private static final long serialVersionUID = 1L;

		/**
		 * 判断锁是否被占用了
		 */
		@Override
		protected boolean isHeldExclusively() {
			return (getState() == 1);
		}
		
		@Override
		protected int tryAcquireShared(int arg) {
			return super.tryAcquireShared(arg);
		}
		
		@Override
		protected boolean tryReleaseShared(int arg) {
			return super.tryReleaseShared(arg);
		}
		
		/**
		 * 尝试获取独占锁
		 */
		@Override
		protected boolean tryAcquire(int arg) {
			//不可重入锁, state为0时, 表示资源未被占用, 将state设置制为1, 表示获取锁
			//如果获取资源成功成功, 将当前运行线程设置为本线程
			if(compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			//获取资源没成功
			return false;
		}
		
		/**
		 * 尝试释放独占锁
		 */
		@Override
		protected boolean tryRelease(int arg) {
			//释放锁, 肯定是占用的线程才能释放, 判断一下, 为了保险
			if(getState() != 1) {
				throw new IllegalMonitorStateException();
			}
			//释放资源, 将资源设置为0即可
			setState(0);
			//将当前运行线程去掉本线程
			setExclusiveOwnerThread(null);
			return true;
		}
		
		/**获取Condition对象, AQS中定义好了, 直接new使用即可*/
		public final Condition newCondition() {
			return new ConditionObject();
		}
	}
	
	/**真正同步类的实现都依赖继承于AQS的自定义同步器！*/
	private Sync sync = new Sync();
	
	/**
	 * 获取锁, 调用aqs的acqueire方法, 里面会调用自定义的tryAcquire
	 * lock<-->acquire。两者语义一样：获取资源，即便等待，直到成功才返回。
	 */
	@Override
	public void lock() {
		sync.acquire(1);
	}

	/**
	 * 可中断方式获取锁(AQS内部实现了可中断方式)
	 */
	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	/**
	 * 尝试获取锁
	 * tryLock<-->tryAcquire。两者语义一样：尝试获取资源，要求立即返回。成功则为true，失败则为false。
	 */
	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	/**
	 * 在timeout时间段内尝试获取锁
	 */
	@Override
	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(timeout));
	}

	/**
	 * 释放资源
	 * unlock<-->release。两者语文一样：释放资源。
	 */
	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}

	public static void main1(String[] args) {
		Mutex lock = new Mutex();
		Condition condition = lock.newCondition();
		for(int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						if(lock.tryLock(3, TimeUnit.SECONDS)) {
							try {
								for(int i = 1; i <= 10; i++) {
									Thread.sleep(1000);
									System.out.println(Thread.currentThread().getName() + ":  " + i);
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}finally {
								lock.unlock();
							}	
						}else {
							System.out.println("我不等了!");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
						
					
					
				}
			}).start();	
		}
		
	}


	
	
	
	public static void main(String[] args) {
		/*
    	DockerClient dockerClient = dockerClientService.getSpecialDockerClientInstance();
    	List<com.github.dockerjava.api.model.Image> images = dockerClient.listImagesCmd().
    			withImageNameFilter("172.16.3.123:5000/tomcat:7-jre7-nio").exec();
    	System.out.println(images.size());
    	double a = images.get(0).getSize() / (1000 * 1000);
    	System.out.println();
    	*/
    	//537105878  537.1
		String.format("%.1f", (double)537105878 / (1000*1000));
    	System.out.println(String.format("%.1f", (double)537105878 / (1000*1000)));
    	
	}
	

	
	

	
	
	
	
	
	
	
}
