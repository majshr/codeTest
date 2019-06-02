package codeTest.v1.netty.netty1hello;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class HelloClientHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 读取服务端返回的消息, 仅读取消息操作, 需要读取完成后, 清空缓冲区
			//do something msg
			ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String request = new String(data, "utf-8");
			System.out.println("Client: " + request);
			
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
