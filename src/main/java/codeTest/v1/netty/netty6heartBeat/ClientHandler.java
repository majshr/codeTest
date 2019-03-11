package codeTest.v1.netty.netty6heartBeat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter{
	
	private static final String SUCCESS_KEY = "auth_success_key";
	
	/**
	 * ��ʱ����
	 */
	private static final ScheduledExecutorService schedule = 
			Executors.newScheduledThreadPool(1);
	
	private String ip;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// ͨ�������, client�������˷�����֤��Ϣ
		// ��֤��ϢΪip_ip������Ӧ����֤key
		ip = InetAddress.getLocalHost().getHostAddress();
		String key = "123456";
		
		// ƴ����֤��Ϣ, ���͸������
		String auth = ip + "," + key;
		ctx.writeAndFlush(auth);
	}
	
	public static void main(String[] args) throws UnknownHostException {
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			if(msg instanceof String){
				// ��֤�Ƿ�ͨ����Ϣ
				String authRes = (String) msg;
				// �����֤�ɹ�, ��ʱ��������
				if(SUCCESS_KEY.equals(authRes)){
					// 2s֮��ִ��һ��, ֮��ÿ3sִ��һ��
					schedule.scheduleWithFixedDelay(new HeartBeatTask(ctx), 
							2, 3, TimeUnit.SECONDS);
				}else{
					// ��֤ʧ��, ��ӡ��֤��Ϣ, ���ӱ��ر�
					System.out.println(msg);
				}
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// �ر��̳߳�
		schedule.shutdown();
		cause.printStackTrace();
		ctx.close();
	}	
	
	/**
	 * ��ʱ������������
	 * @author maj
	 *
	 */
	private class HeartBeatTask implements Runnable{

		private ChannelHandlerContext ctx;
		
		public HeartBeatTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}
		
		@Override
		public void run() {
			try {
				Request request = new Request();
				//ip
				request.setIp(ip);
				Sigar sigar = new Sigar();
				//cpu prec
				CpuPerc cpuPerc = sigar.getCpuPerc();
				HashMap<String, Object> cpuPercMap = new HashMap<String, Object>();
				cpuPercMap.put("combined", cpuPerc.getCombined());
				cpuPercMap.put("user", cpuPerc.getUser());
				cpuPercMap.put("sys", cpuPerc.getSys());
				cpuPercMap.put("wait", cpuPerc.getWait());
				cpuPercMap.put("idle", cpuPerc.getIdle());
				// memory
				Mem mem = sigar.getMem();
				HashMap<String, Object> memoryMap = new HashMap<String, Object>();
				memoryMap.put("total", mem.getTotal() / 1024L);
				memoryMap.put("used", mem.getUsed() / 1024L);
				memoryMap.put("free", mem.getFree() / 1024L);
				request.setCpuPercMap(cpuPercMap);
				request.setMemoryMap(memoryMap);
				
				// ����������Ϣ
				ctx.writeAndFlush(request);
			} catch (SigarException e) {
				e.printStackTrace();
			}
		}
		
	}
}
