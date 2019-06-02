package codeTest.v1.netty2.ssy06protobuf;

import codeTest.v1.netty2.ssy06protobuf.entity.MyDataInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyServer {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO)) // 日志信息
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					// 解码成的对象
					ch.pipeline().addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
					ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					ch.pipeline().addLast(new MyServerHandler());
				}
			});
			
			ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
			channelFuture.channel().closeFuture().sync();
			
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}












































