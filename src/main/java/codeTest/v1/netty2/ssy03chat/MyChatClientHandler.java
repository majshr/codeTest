package codeTest.v1.netty2.ssy03chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyChatClientHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println(msg);
	}

}
