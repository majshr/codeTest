package codeTest.v1.netty.netty2unpacking.special.characters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// ��ȡ����˷��ص���Ϣ, ����ȡ��Ϣ����, ��Ҫ��ȡ��ɺ�, ��ջ�����
			String response = (String) msg;
			System.out.println(response);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}	
}