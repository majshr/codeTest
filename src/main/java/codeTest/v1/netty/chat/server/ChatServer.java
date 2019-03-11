package codeTest.v1.netty.chat.server;

import java.util.Arrays;
import java.util.Collection;

import codeTest.v1.netty.chat.server.handler.HttpHandler;
import codeTest.v1.netty.chat.server.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServer {
	private int port;
	
	public ChatServer(int port) {
		super();
		this.port = port;
	}

	public void start(){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					/*************����ͽ���http����***********************/
					// �������Ӧ�����Ϊhttp��Ϣ(HttpRequestDecoder �� HttpResponseEncoder �Ľ��)
					/* ����ֻ�ܽ���http get����(����������uri��, ���ͨ��HttpRequest���ܽ������������.),
					����http post����, ������Ϣ����message body��, ��Ӧnetty��˵��HttpMessage, �������������������ȫ����post
					HTTP POST ����ʱ��������� ChannelPipeline �м��� HttpObjectAggregator(���԰� HttpMessage �� HttpContent
					 �ۺϳ�һ�� FullHttpRequest ���� FullHttpResponse ��ȡ�����Ǵ�����������Ӧ���������������԰�
					 �����ڽ���ʱ�����Ƿ�Ϊ���顱���䷽ʽ��)
					*/
					ch.pipeline().addLast(new HttpServerCodec());
					// httpת��������(http��Ϣ�Ķ�����ֺϲ���һ��������http��Ϣ)
					ch.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
					// ���ڴ����ļ���һ��handler, д���ļ�������(��ͻ��˷���html5�ļ�)
					ch.pipeline().addLast(new ChunkedWriteHandler());
					//�Զ��崦����
					ch.pipeline().addLast(new HttpHandler());
					
					/*************֧��websocketЭ��*************/
					// ws://im����Ϊ��websocket�Ķ���
					ch.pipeline().addLast(new WebSocketServerProtocolHandler("/im"));
					ch.pipeline().addLast(new WebSocketHandler());
				}
				
			});
			
			ChannelFuture f = b.bind(this.port).sync();
			System.out.println("�����Ѿ�����, �����˿�" + this.port);
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new ChatServer(8080).start();
	}
}








