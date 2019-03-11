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
		/*�����߳�ģ��*/
		// boss �߳�
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// worker�߳�
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			//netty����, ��·ʽ���
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workerGroup)
			// ���̴߳�����
			.channel(NioServerSocketChannel.class)
			// ���̴߳�����
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel client) throws Exception {
					/** ���������б�� */
					// ҵ���߼���·
					//������
					client.pipeline().addLast(new HttpResponseEncoder());
					//������
					client.pipeline().addLast(new HttpRequestDecoder());
					
					//ҵ���߼�����
					client.pipeline().addLast(new TomcatHanlder());
					
				}
				
			})
			// ������Ϣ
			.option(ChannelOption.SO_BACKLOG, 128) //������߳�  128��ʾ�����̵߳��������
			.childOption(ChannelOption.SO_KEEPALIVE, true); //������߳�  �ͻ����������, Ϊ������(keeplive)
			
			// ����
			ChannelFuture f = server.bind(port).sync();
			
			System.out.println("Tomcat self�Ѿ�����!");
			
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


























