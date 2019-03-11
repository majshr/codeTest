package codeTest.v1.netty.tomcat.http;

import java.io.UnsupportedEncodingException;

import javax.print.attribute.ResolutionSyntax;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.HttpHeaders.Values;

public class Response {

	private ChannelHandlerContext ctx;
	private HttpRequest req;
	
	public Response(ChannelHandlerContext ctx, HttpRequest req) {
		this.ctx = ctx;
		this.req = req;
	}
	
	public void write(String out) throws UnsupportedEncodingException{
		try {
			if(out == null){
				return;
			}
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
					Unpooled.wrappedBuffer(out.getBytes("UTF-8")));
			response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/json");
			response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
			response.headers().set(HttpHeaders.Names.EXPIRES, 0);//²»¹ýÊ±
			if (HttpHeaders.isKeepAlive(req)) {
				response.headers().set(HttpHeaders.Names.CONNECTION, Values.KEEP_ALIVE);
			}
			ctx.write(response);
		} finally {
			ctx.flush();
		}
	}

}
