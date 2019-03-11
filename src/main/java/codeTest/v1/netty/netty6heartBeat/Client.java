package codeTest.v1.netty.netty6heartBeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Client {
	private EventLoopGroup group;
	private Bootstrap b;
	private ChannelFuture cf;
	private String host;
	private int port;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	static class Instance{
		public static Client instance = new Client();
	}
	
	/**
	 * ��ȡChannelFuture����(���channel�ر�, ��������)
	 * @return
	 */
	public ChannelFuture getChannelFuture(){
		return cf;
	}
	
	public EventLoopGroup getEventLoopGroup() {
		return group;
	}

	public Bootstrap getBootstrap() {
		return b;
	}

	/**
	 * �ͻ��˵���
	 */
	public static Client getInstance(){
		return Instance.instance;
	}
	
	/**
	 * ��ʼ��ʱ, ��������
	 */
	private Client(){
		group = new NioEventLoopGroup();
		b = new Bootstrap();
		b.group(group)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				// ����marshalling�������
				sc.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder())
				.addLast(MarshallingCodeFactory.buildMarshllingDecoder());
				
				// ��ʱhandler, ������������ͻ�����ָ��ʱ������û���κ�ͨ��, ���ر���Ӧͨ��, ��ҪΪ��С������Դռ��
				// 5s
				// �ͻ��˲���Ҳ��, �����Ǽ��Ϻ�; ���߶����ʱ
				sc.pipeline().addLast(new ReadTimeoutHandler(5));
				
				sc.pipeline().addLast(new ClientHandler());
			}
		});
	}
	
	/**
	 * ���ӷ���
	 * @param host
	 * @param port
	 */
	private void connect(String host, int port){
		try {
			cf = b.connect(host, port).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ӷ����
	 */
	public void connect(){
		connect(host, port);
	}
	
	public static void main(String[] args) throws Exception{
		// ���ӷ����, ���ӳɹ���, �����ú�ChannelFutureֵ
		Client client =  getInstance();
		String host = "localhost";
		int port = 8765;
		client.setHost(host);
		client.setPort(port);
		
		// ����, ��ȡChannelFuture
		client.connect();
		ChannelFuture cf = client.getChannelFuture();
		
		// �����ߵ���, ˵�����ڿͻ��˺�server�˵ķ���Ͽ���,
        // ����client��������Ǵ��ڵ�, ֻ��ͨ���Ͽ���
		cf.channel().closeFuture().sync();
		client.getEventLoopGroup().shutdownGracefully();
	}

}
