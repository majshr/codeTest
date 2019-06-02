package codeTest.v1.netty2.ssy01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
	public static void main(String[] args) throws InterruptedException {
		// 一个死循环，一直存在（优雅退出，在不需要时关闭）
		// boos接收连接，但连接的处理交给worker处理
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			// 可以轻松启动netty程序(netty提供，简化服务端启动的class)
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class) // 通道
			.childHandler(new TestServerInit()); // 子处理器
			ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}


}























