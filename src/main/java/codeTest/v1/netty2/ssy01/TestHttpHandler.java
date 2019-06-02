package codeTest.v1.netty2.ssy01;

import java.net.URI;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author bhz（maj）
 * @since 2019年5月24日
 * Inbound进来
 */
public class TestHttpHandler extends SimpleChannelInboundHandler<HttpObject>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		System.out.println(msg.getClass());
		if(msg instanceof HttpRequest){
			
			HttpRequest request = (HttpRequest) msg;
			System.out.println("请求方法" + request.method().name());
			URI uri = new URI(request.uri());
			if("/favicon.ico".equals(uri.getPath())){
				System.out.println("请求favicon.ico");
				return;
			}
			
			System.out.println("receive 请求！");
			
			ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
			
			response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			response.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
			
			ctx.writeAndFlush(response);	
			
			// 关闭连接
			ctx.channel().close();
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.handlerAdded(ctx);
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	
	
}

