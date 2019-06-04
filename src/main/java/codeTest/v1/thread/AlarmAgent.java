package codeTest.v1.thread;

import java.util.Random;

import io.github.viscent.mtia.util.Tools;

/**
 * 心跳检测
 * 
 * @author mengaijun
 * @Description: TODO
 * @date: 2019年6月4日 上午9:41:19
 */
public class AlarmAgent {
	/**类的唯一实例*/
	private final static AlarmAgent instance = new AlarmAgent();
	
	/**是否连接上告警服务器*/
	private volatile boolean connectedToServer = false;
	
	/**心跳线程, 用于检测告警代理与告警服务器网络连接是否正常*/
	private final HeartbeatThread heartbeatThread = new HeartbeatThread();
	
	private AlarmAgent() {
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		AlarmAgent ins = AlarmAgent.getInstance();
		ins.init();
		for(int i = 0; i < 10; i++) {
			int n = i;
			new Thread() {
				public void run() {
					try {
						ins.sendAlarm("aaa" + n);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
	public void init() {
	    connectToServer();
	    heartbeatThread.setDaemon(true);
	    heartbeatThread.start();
	}
	
	public static AlarmAgent getInstance() {
		return instance;
	}
	
	/**
	 * 连接到服务
	 */
	private void connectToServer() {
		new Thread() {
			@Override
			public void run() {
				doConnect();
			}
		}.start();
	}
	
	/**
	 * 连接告警服务器
	 */
	private void doConnect() {
		synchronized (this) {
			//模拟连接
			Tools.randomPause(100);
			System.out.println("连接服务成功了=======================");
			connectedToServer = true;
			//连接成功了, 唤醒发送信息的线程
			this.notify();
		}
	}
	
	private synchronized void notifySelf() {
		this.notify();
	}
	
	/**
	 * 发送消息
	 * @param message    
	 * @throws InterruptedException 
	 */
	public void sendAlarm(String message) throws InterruptedException {
		synchronized (this) {
			while(!connectedToServer) {
				System.out.println("Alarm agent was not connected to server.");
				this.wait();
			}
			doSendAlarm(message);
		}
	}
	
	/**
	 * 真正发送消息的方法
	 * @Description: TODO
	 * @param message    
	 */
	private void doSendAlarm(String message) {
		System.out.println(message + "--已送达");
	}
	
	class HeartbeatThread extends Thread{
		@Override
		public void run() {
			try {
				int i = 1;
				while(true) {
					//如果没有连接
					if(!checkConnection(i++)) {
						connectedToServer = false;
						System.out.println("与服务器连接断开了, 正在重新连接....");
						//新启动一个线程去连接服务器
						connectToServer();
					}else {
						connectedToServer = true;
//						notifySelf();
					}
					//每2s检测一次
					Thread.sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 检查与服务器连接是否正常
	 */
	private boolean checkConnection(int i) {
		boolean isConnected = true;
		Random random = new Random();
		
		//用随机数模拟判断连接是否正常
		int rand = random.nextInt(1000);
		System.out.println("第" + i + "次检测: " + rand);
		if(rand <= 500) {
			isConnected = false;
		}
		return isConnected;
	}
}















































