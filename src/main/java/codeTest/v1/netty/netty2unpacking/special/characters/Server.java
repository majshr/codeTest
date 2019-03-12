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
		//1, ���������߳� 
		// ����client�����Ӵ���
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// ������������, ����ͨ��, �����д
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			// 2, ��������������, ���ڷ�����ͨ����ϵ������
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup) // �������߳���
			.channel(NioServerSocketChannel.class) // ָ��NIO��ģʽ
			.option(ChannelOption.SO_BACKLOG, 1024) // ����tcp������(tcp�������ں�, һ������100�������)
			.option(ChannelOption.SO_SNDBUF, 32*1024)	//���÷��ͻ����С
			.option(ChannelOption.SO_RCVBUF, 32*1024)	//���Ƿ������˽��ջ����С
			.option(ChannelOption.SO_KEEPALIVE, true)	//��������
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// ��������ָ���
					ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
					// �������ָ����
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
					// �����ַ�����ʽ�Ľ���(�ͻ��˷�����Ϣʱ, ����ֱ�ӷ����ַ���)
					ch.pipeline().addLast(new StringDecoder());
					
					//3, ���þ������ݽ��շ����Ĵ���
					ch.pipeline().addLast(new ServerHandler());
				}
				
			});
			
			// 4,���а�
			ChannelFuture f = b.bind(this.port).sync();
			// �󶨶���˿�
//			ChannelFuture f2 = b.bind(1111).sync();
			System.out.println("�����Ѿ�����, �����˿�" + this.port);
			// 5, �ȴ��ر�
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			// �ͷ���Դ
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new Server(8765).start();
	}
	
}