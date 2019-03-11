package codeTest.v1.netty.netty6heartBeat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import org.omg.CORBA.RepositoryIdHelper;

import codeTest.v1.util.GzipUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter{

	/** key:ip value:auth */
	private static HashMap<String, String> AUTH_IP_MAP = new HashMap<String, String>();
	private static final String SUCCESS_KEY = "auth_success_key";
	
	static {
		AUTH_IP_MAP.put("192.168.31.21", "123456");
	}
	
	/**
	 * ��֤�ͻ��˷���������֤��Ϣ�Ƿ���ȷ
	 * @param ctx
	 * @param auth
	 * @return {@link Boolean}
	 */
	private boolean auth(ChannelHandlerContext ctx, String auth){
		// �յ�slave��֤��Ϣ
		if(auth.contains(",")){
			String[] strs = auth.split(",");
			String ip = strs[0];
			String key = strs[1];
			
			
			if(key.equals(AUTH_IP_MAP.get(ip))){
				// ��֤ͨ��, ��ͻ��˷�����֤ͨ����Ϣ
				ctx.writeAndFlush(SUCCESS_KEY);
				return true;
			}	
		}
		
		// ��֤ʧ��, ֪ͨ�ͻ���ʧ��, ���ر�����
		ctx.writeAndFlush("auth failure! ").addListener(ChannelFutureListener.CLOSE);
		return false;
	}
	
	/**
	 * ���յ��ͻ��˴���������
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof String){
			// ��֤��Ϣ
			auth(ctx, (String)msg);
		} else if(msg instanceof Request){
			// ������Ϣ
			Request info = (Request) msg;
			System.out.println("--------------------------------------------");
			System.out.println("��ǰ����ipΪ: " + info.getIp());
			System.out.println("��ǰ����cpu���: ");
			HashMap<String, Object> cpu = info.getCpuPercMap();
			System.out.println("��ʹ����: " + cpu.get("combined"));
			System.out.println("�û�ʹ����: " + cpu.get("user"));
			System.out.println("ϵͳʹ����: " + cpu.get("sys"));
			System.out.println("�ȴ���: " + cpu.get("wait"));
			System.out.println("������: " + cpu.get("idle"));
			
			System.out.println("��ǰ����memory���: ");
			HashMap<String, Object> memory = info.getMemoryMap();
			System.out.println("�ڴ�����: " + memory.get("total"));
			System.out.println("��ǰ�ڴ�ʹ����: " + memory.get("used"));
			System.out.println("��ǰ�ڴ�ʣ����: " + memory.get("free"));
			System.out.println("--------------------------------------------");
			
			ctx.writeAndFlush("info received!");
		} else{
			//  �������, �ر�����
			ctx.writeAndFlush("connect failure!").addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t)
			throws Exception {
		ctx.close();
	}
	
}
































