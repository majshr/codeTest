package codeTest.v1.netty.netty2unpacking.special.characters;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Server {
	
	private int port;
	
	public Server(int port) {
		super();
		this.port = port;
	}

	private void start(){
		//1, 启动两个线程 
		// 结束client端连接处理
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 真正处理任务, 网络通信, 网络读写
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			// 2, 创建辅助工具类, 用于服务器通道的系列配置
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup) // 绑定两个线程组
			.channel(NioServerSocketChannel.class) // 指定NIO的模式
			.option(ChannelOption.SO_BACKLOG, 1024) // 设置tcp缓冲区(tcp缓冲区内核, 一般设置100多个就行)
			.option(ChannelOption.SO_SNDBUF, 32*1024)	//设置发送缓冲大小
			.option(ChannelOption.SO_RCVBUF, 32*1024)	//这是服务器端接收缓冲大小
			.option(ChannelOption.SO_KEEPALIVE, true)	//保持连接
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// 设置特殊分隔符
					ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
					// 长度随便指定的
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
					// 设置字符串形式的解码(客户端发送消息时, 可以直接发送字符串)
					ch.pipeline().addLast(new StringDecoder());
					
					//3, 配置具体数据接收方法的处理
					ch.pipeline().addLast(new ServerHandler());
				}
				
			});
			
			// 4,进行绑定
			ChannelFuture f = b.bind(this.port).sync();
			// 绑定多个端口
//			ChannelFuture f2 = b.bind(1111).sync();
			System.out.println("服务已经启动, 监听端口" + this.port);
			// 5, 等待关闭
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			// 释放资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new Server(8765).start();
	}
	
}
