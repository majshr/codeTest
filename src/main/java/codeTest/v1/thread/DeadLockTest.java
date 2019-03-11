package codeTest.v1.thread;

public class DeadLockTest {
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	public static void methodA() throws InterruptedException{
		synchronized (lock1) {
			Thread.sleep(1000);
			synchronized (lock2) {
				
			}
		}
	}
	
	public static synchronized void methodB() throws InterruptedException{
		synchronized (lock2) {
			Thread.sleep(1000);
			synchronized (lock1) {
				
			}
		}
	}
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				try {
					methodA();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					methodB();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
