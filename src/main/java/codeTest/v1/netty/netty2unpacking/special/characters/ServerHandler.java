package codeTest.v1.netty.netty2unpacking.special.characters;

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
		// �����Զ��������ַ���
		String body = (String) msg;
		System.out.println("Server :" + body );
		
		String response = "��������Ӧ��" + msg + "$_";
		System.out.println(response);
		// ���ͻ�����Ӧ��Ϣ���첽��, ����ͨ����ӻص�����, ֪���Ƿ�����Ϣ���
		ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
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
































