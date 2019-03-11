package codeTest.v1.netty.netty7httphello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {
	  
    private final SslContext sslCtx;

    public HttpHelloWorldServerInitializer(SslContext sslCtx) {
       this.sslCtx = sslCtx;
    }

	@Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
		  // 只要加了这个, 就可以支持http协议了, 可以直接把请求封装成http对象
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpHelloWorldServerHandler());
    }
}
