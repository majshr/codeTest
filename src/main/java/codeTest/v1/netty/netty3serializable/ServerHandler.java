package codeTest.v1.netty.netty3serializable;

import java.io.File;
import java.io.FileOutputStream;

import org.omg.CORBA.RepositoryIdHelper;

import codeTest.v1.util.GzipUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter{

	
	/**
	 * ͨ������
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server channel active... ");
	}


	/**
	 * ���յ��ͻ��˴���������
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		/****��ȡ������Ϣ*****/
		Request request = (Request) msg;
		System.out.println(request);
		// �����ļ�
		// �ļ�����
		byte[] data = GzipUtils.ungzip(request.getAttachment());
		File file = new File("C:\\Users\\maj\\Desktop\\netty_copy\\spring" + request.getId() + ".java");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(data);
		
		/*****������Ӧ��Ϣ************/
		// ���ͻ�����Ӧ��Ϣ���첽��, ����ͨ����ӻص�����, ֪���Ƿ�����Ϣ���
		Response response = new Response();
		response.setId(request.getId());
		response.setName("resp" + request.getId());
		response.setResponseMessage("��Ӧ����" + request.getId());
		ctx.writeAndFlush(response);
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
































