package codeTest.v1.netty.tomcat;

import java.awt.HeadlessException;

import codeTest.v1.netty.tomcat.http.Request;
import codeTest.v1.netty.tomcat.http.Response;
import codeTest.v1.netty.tomcat.http.servlet.MyServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
/**
 * 处理器类
 * @author maj
 *
 */
public class TomcatHanlder extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof HttpRequest){
			HttpRequest req = (HttpRequest) msg;
			
			Request request = new Request(ctx, req);
			Response response = new Response(ctx, req);
			
			new MyServlet().doGet(request, response);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

}
