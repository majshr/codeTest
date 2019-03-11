package codeTest.v1.netty.netty1hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HelloServerHandler extends ChannelInboundHandlerAdapter{

	
	/**
	 * ͨ������
	 * @param ctx
	 * @throws Exception
	 */
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
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "utf-8");
		System.out.println("Server :" + body );
		String response = "���з��ظ��ͻ��˵���Ӧ��" + body ;
		// ���ͻ�����Ӧ��Ϣ���첽��, ����ͨ����ӻص�����, ֪���Ƿ�����Ϣ���
		ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
		// ��ʾ����˷�����Ϣ��ɺ�ر����� 
			f.addListener(ChannelFutureListener.CLOSE);
	}

//	public void channelReadComplete(ChannelHandlerContext ctx)
//			throws Exception {
//		System.out.println("������");
//		ctx.flush();
//	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t)
			throws Exception {
		ctx.close();
	}
	
}
































