package codeTest.v1.netty2.ssy01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 管道初始化
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年5月23日
 */
public class TestServerInit extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// 管道，一个管道可以有多个管道
		ChannelPipeline pipeline = ch.pipeline();
		
		// A combination of {@link HttpRequestDecoder} and {@link HttpResponseEncoder}
		pipeline.addLast("httpServerCodec", new HttpServerCodec());
		pipeline.addLast("testHttpHandler", new TestHttpHandler());
	}

}



class ForkJoinTask extends RecursiveTask<List<String>> {
	private static final long serialVersionUID = 1L;
	
	private int THRESHOLD = 2;
 
	private int start;
 
	private int end;
 
 
 
	public ForkJoinTask(int start, int end) {
		this.start = start;
		this.end = end;
	}
 
	@Override
	protected List<String> compute() {
		List<String> list = new ArrayList<>();
		//如果任务足够小就计算任务
		boolean canCompute = (end - start) <= THRESHOLD;
		if (canCompute) {
			for (int i = start; i < end; i++) {
				try {
					Thread.currentThread().sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("线程池中线程数目："+ForkJoinTask.getPool().getPoolSize()+"，队列中等待执行的任务数目："+
						 ForkJoinTask.getPool().getQueuedTaskCount());
				list.add("wo");
			}
			return list;
		}
 
		// 如果任务仍然很大，拆分任务
		int middle = (end + start) / 2;
		ForkJoinTask task1 = new ForkJoinTask(start, middle);
		ForkJoinTask task2 = new ForkJoinTask(middle, end);
		invokeAll(task1, task2);
		//等待任务执行结束合并其结果
        List<String> leftResult = task1.join();
        List<String> rightResult = task2.join();
        list.addAll(rightResult);
        list.addAll(leftResult);
        
		return list;
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		 
		List<String> list = new ArrayList<String>();
        for(int i=1;i<=330;i++){
            list.add("i------"+i);
        }
        System.out.println(list.size());
        long s1 = System.currentTimeMillis();
        // 参数默认是cpu数（可以自定义）
		ForkJoinPool pool = new ForkJoinPool();
        // 提交可分解的PrintTask任务
		Future<List<String>> ss =  pool.submit(new ForkJoinTask(0,list.size()));
        //线程阻塞，等待所有任务完成
		pool.awaitTermination(1, TimeUnit.SECONDS);
        // 关闭线程池
        pool.shutdown();
        long s2 = System.currentTimeMillis();
        long s =s2-s1;
        System.out.println(s);
	}
}

