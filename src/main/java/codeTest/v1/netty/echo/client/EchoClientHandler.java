package codeTest.v1.netty.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * ChannelInboundHandlerAdapter�ڴ�������Ϣ����Ҫ�����ͷ���Դ�������ｫ����ByteBuf.release()���ͷ���Դ��
 * SimpleChannelInboundHandler�������channelRead0���ͷ���Ϣ������ͨ��Netty����������Ϣ��
 * ChannelHandlerʵ����ReferenceCounted�ӿڴﵽ�ġ�
 * 
 * @author maj
 *
 */
public class EchoClientHandler extends  SimpleChannelInboundHandler<ByteBuf>{


	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write(Unpooled.copiedBuffer("netty rocks", CharsetUtil.UTF_8));
		ctx.flush();
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//		System.out.println("client reveived: " + ByteBufUtil.hexDump(msg.readBytes(msg.readableBytes())));
		System.out.println("client reveived: " + msg.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}



















