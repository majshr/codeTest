package codeTest.v1.netty.netty3serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import codeTest.v1.util.GzipUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	private int port;
	public Client(int port) {
		this.port = port;
	}
	public void start() throws InterruptedException, IOException{
		// �ͻ��˽���������, ����Ҫ�����������ӵ��߳���
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				// ����marshalling�������
				sc.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder())
				.addLast(MarshallingCodeFactory.buildMarshllingDecoder());
				
				sc.pipeline().addLast(new ClientHandler());
			}
		});
		
		// ���ӷ����
		ChannelFuture cf = b.connect("127.0.0.1", port).sync();
		
		// ������Ϣ
		for(int i = 0; i < 5; i++){
			Request request = new Request();
			request.setId("" + i);
			request.setName("pro" + i);
			request.setRequestMessage("������Ϣ" + i);
			
			// �����ļ���Ϣ
			FileInputStream fileInputStream = 
					new FileInputStream(new File("C:\\Users\\maj\\Desktop\\spring.java"));
			// ��ȡ�ļ���Ϣ����������
			byte[] data = new byte[fileInputStream.available()];
			fileInputStream.read(data);
			
			// ��ѹ�����ݷŽ�����
			request.setAttachment(GzipUtils.gzip(data));
			
			cf.channel().writeAndFlush(request);
		}
		
		cf.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception{
		new Client(8765).start();
	}
}
