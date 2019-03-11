package codeTest.v1.netty.echo.server;

import java.util.Iterator;
import java.util.Random;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class EchoServer {
	private final int port;
	
	private ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
	
	public EchoServer(int port){
		this.port = port;
	}
	
	public void start() throws Exception{
		// ���պʹ���������
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(group).channel(NioServerSocketChannel.class)
			.localAddress(port)
			.childHandler(new ChannelInitializer<Channel>() {// ָ�����Ӻ���õ�ChannelHandler

				@Override
				protected void initChannel(Channel ch) throws Exception {
//					ch.pipeline().addLast(new EchoServerHandler());
					
					
					// ͨ��ͨ��������д����, ����д������� 
					ChannelFuture f = ch.writeAndFlush(buf);
					f.addListener(new ChannelFutureListener() {
						
						public void operationComplete(ChannelFuture future) throws Exception {
							if(future.isSuccess()){
								System.out.println("�������ݳɹ�");
							}
						}
					});
					
				}
			});
			
			// ����֪����������ɰ�
			ChannelFuture f = b.bind().sync();
			System.out.println("��������.........");
			
			// �رղ���, Ҳ������
			f.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new EchoServer(8080).start();
	}
	
	private void compositeByteBufTest(){
		CompositeByteBuf compBuf = Unpooled.compositeBuffer();
		// �ѻ�����
		ByteBuf heapBuf = Unpooled.buffer(8);
		// ֱ�ӻ�����
		ByteBuf directBuf = Unpooled.directBuffer(16);
		compBuf.addComponents(heapBuf, directBuf);
		
		// ɾ����һ��������
		compBuf.removeComponent(0);
		
		// �������ϻ�����
		Iterator<ByteBuf> iter = compBuf.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next().toString());
		}
		
		// ʹ���������
		if(!compBuf.hasArray()){
			int len = compBuf.readableBytes();
			byte[] arr = new byte[len];
			compBuf.getBytes(0, arr);
		}
	}
	
	public void bufTest(){
		// ͨ����������, �����ƽ���������д����, ����ͨ��readerIndex()��writerIndex()�ƽ�
		ByteBuf buf = Unpooled.buffer(16);
		for(int i = 0; i < 16; i++){
			buf.writeByte(i + 1);
		}
		
		for(int i = 0; i < buf.capacity(); i++){
			System.out.println(buf.getByte(i));
		}
		
		/* �����ֽ�, ����������0��readIndex֮����ֽ�, �����������ByteBuf���Ѿ���ȡ������, �Ӷ�ʹ
		   ByteBuf�ж���Ŀռ�����������, �����漰���ڴ渴��, �ƶ��ɶ��ֽڵ���ʼλ��, ���ܻ�Ӱ������.*/
		buf.discardReadBytes();
		
		/*ѭ����ȡ, ֱ������*/
		while(buf.isReadable()){
			System.out.println(buf.readByte());
		}
		
		/*ѭ��д��, ֱ��д��*/
		Random random = new Random();
		while(buf.isWritable()){
			buf.writeInt(random.nextInt());
		}
		
		/*�������ָ�Ϊ��ʼ, read/write index��Ϊ0*/
		buf.clear();
	}
}
























