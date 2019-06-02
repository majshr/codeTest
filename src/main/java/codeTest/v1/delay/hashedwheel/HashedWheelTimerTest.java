package codeTest.v1.delay.hashedwheel;

import java.util.concurrent.TimeUnit;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

/**
 * 我们用Netty的HashedWheelTimer来实现, 时间轮算法 
 * @author maj
 *
 */
public class HashedWheelTimerTest {
	static class MyTimerTask implements TimerTask{

		boolean flag;
		
		public MyTimerTask(boolean flag) {
			super();
			this.flag = flag;
		}

		@Override
		public void run(Timeout timeout) throws Exception {
			System.out.println("要去删除数据库订单了!");
			this.flag = false;
		}
		
	}
	public static void main(String[] args) {
		MyTimerTask task = new MyTimerTask(true);
		
		// 时间轮工具类
		Timer timer = new HashedWheelTimer();
		
		// 5s后执行task任务
		timer.newTimeout(task, 5, TimeUnit.SECONDS);
		
		int i = 1;
		while (task.flag) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i + "秒过去了");
			i++;

		}
	}
}





























