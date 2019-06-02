package codeTest.v1.thread.threadpool.self;

public class Test {
    public static void main(String[] args) {
        ThreadPool threadPool = new BaseThreadPool(4,30,6,30);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is running and done.");
            });
        }

    }
}

