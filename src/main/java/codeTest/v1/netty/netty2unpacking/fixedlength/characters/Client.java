package codeTest.v1.netty.netty2unpacking.fixedlength.characters;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class Client {
	private int port;
	public Client(int port) {
		this.port = port;
	}
	public void start() throws InterruptedException{
		// �ͻ��˽���������, ����Ҫ�����������ӵ��߳���
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				// �������ָ����, ֻ�ǹ�5λ, ����ͬһ������
				sc.pipeline().addLast(new FixedLengthFrameDecoder(5));;
				// �����ַ�����ʽ�Ľ���(�ͻ��˷�����Ϣʱ, ����ֱ�ӷ����ַ���)
				sc.pipeline().addLast(new StringDecoder());
				
				sc.pipeline().addLast(new ClientHandler());
			}
		});
		
		// ���ӷ����
		ChannelFuture cf1 = b.connect("127.0.0.1", port).sync();
		System.out.println("����" + port + ".....");
		// �����ݵ�ʱ������Ҫ����buf, ������һ�˺��ֱ�ӽ���Ϊ�ַ���
		//������Ϣ cf.channel() ��ȡͨ�Źܵ�
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("7777777".getBytes()));
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("88888".getBytes()));
		
		cf1.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception{
		new Client(8765).start();
	}
}
