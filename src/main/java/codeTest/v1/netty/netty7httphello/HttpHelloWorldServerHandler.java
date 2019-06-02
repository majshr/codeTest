package codeTest.v1.netty.netty7httphello;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter {
    private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		  // 去过是http请求
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            if (HttpHeaders.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
			  // 请求是否一直活着
            boolean keepAlive = HttpHeaders.isKeepAlive(req);
			  // 响应数据放到对象里
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
			  // 设置一些请求头
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

            if (!keepAlive) {
				  // 如果不一直活着, 发送响应数据, 就关闭连接
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
				  // 如果一直活着, 保持连接
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}