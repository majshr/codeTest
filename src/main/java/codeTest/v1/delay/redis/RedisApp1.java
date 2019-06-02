package codeTest.v1.delay.redis;

import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

public class RedisApp1 {
	private static final String ADDR = "localhost";
	private static final int PORT = 6379;
	private static JedisPool jedisPool = new JedisPool(ADDR, PORT);
	
	private static String ORDER_KEY = "orderId";
	
	public static Jedis getJedis(){
		return jedisPool.getResource();
	}
	
	/**
	 * 生产者, 生产订单放进去
	 */
	public void productionDelayMessage(){
		for(int i = 0; i < 5; i++){
			// 先获取当前时间, 当前时间加3s, 获取3s之后的时间, 转换为秒数
			Calendar ca1 = Calendar.getInstance();
			ca1.add(Calendar.SECOND, 3);
			int second3later = (int)(ca1.getTimeInMillis() / 1000);
			
			// 向redis集合中添加到排序集合中
			getJedis().zadd(ORDER_KEY, second3later, "OID000000" + i);
			System.out.println(System.currentTimeMillis() + 
					"ms : redis生成了一个订单任务: 订单ID为OID000000" + i);
		}
	}
	
	/**
	 * 消费者, 取消订单
	 * 在redis中不断轮询, 查询最近一个过期的, 删除, 然后轮询下一个
	 */
	public void consumerDelayMessage(){
		Jedis jedis = getJedis();
		while(true){
			Set<Tuple> items = jedis.zrangeWithScores(ORDER_KEY, 0, 1);
			if(items == null || items.isEmpty()){
				System.out.println("当前没有等待的任务!");
				try{
					Thread.sleep(500);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
				continue;
			}
			
			int score = (int) ((Tuple)(items.toArray()[0])).getScore();
			Calendar cal = Calendar.getInstance();
			int nowSecond = (int) (cal.getTimeInMillis() / 1000);
			if(nowSecond >= score){
				// 删除订单ID
				String orderId = ((Tuple)(items.toArray()[0])).getElement();
				// 如果有多个消费者, 会有线程安全问题, 只让一个线程删除成功
				long num = jedis.zrem(ORDER_KEY, orderId);
				// 删除成功的线程
				if(num > 0){
					System.out.println(System.currentTimeMillis() + "ms:redis消费了一个任务: 消费的订单orderId为" + orderId);	
				}
			}
		}
	}
	
	public static void main(String[] args) {
		RedisApp1 app = new RedisApp1();
		
		app.productionDelayMessage();
		
		app.consumerDelayMessage();
	}
}


/**高并发下, 线程安全问题*/
class ThreadTest{
	private static final int threadNum = 10;
	private static CountDownLatch latch = new CountDownLatch(threadNum);
	static class DelayMessage implements Runnable{

		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			RedisApp1 app = new RedisApp1();
			app.consumerDelayMessage();
		}
		
	}
}




























