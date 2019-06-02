package codeTest.v1.netty.tomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class GPTomcat {
	public void start(int port) throws Exception{
		/*主从线程模型*/
		// boss 线程
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// worker线程
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			//netty服务, 链路式编程
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workerGroup)
			// 主线程处理类
			.channel(NioServerSocketChannel.class)
			// 子线程处理类
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel client) throws Exception {
					/** 无锁化串行编程 */
					// 业务逻辑链路
					//编码器
					client.pipeline().addLast(new HttpResponseEncoder());
					//解码器
					client.pipeline().addLast(new HttpRequestDecoder());
					
					//业务逻辑处理
					client.pipeline().addLast(new TomcatHanlder());
					
				}
				
			})
			// 配置信息
			.option(ChannelOption.SO_BACKLOG, 128) //针对主线程  128表示分配线程的最大数量
			.childOption(ChannelOption.SO_KEEPALIVE, true); //针对子线程  客户端请求过来, 为长连接(keeplive)
			
			// 阻塞
			ChannelFuture f = server.bind(port).sync();
			
			System.out.println("Tomcat self已经启动!");
			
			f.channel().closeFuture().sync();			
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		try{
			new GPTomcat().start(8080);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}


























