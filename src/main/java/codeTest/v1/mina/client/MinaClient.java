package codeTest.v1.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import codeTest.v1.mina.client.handler.MyClientHandler;
import codeTest.v1.mina.filter.client.MyFilterClientVer1;

public class MinaClient {
	public static String HOST = "127.0.0.1";
	private static int PORT = 7080;
	public static void main(String[] args) {
		IoSession ioSession = null;
		
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(3000);
		
		// ���ù�����, codec(����������һ������)
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(
						Charset.forName("UTF-8"), 
						LineDelimiter.WINDOWS.getValue(), 
						LineDelimiter.WINDOWS.getValue())));
		
		connector.getFilterChain().addFirst("myFilter", new MyFilterClientVer1());
		
		connector.setHandler(new MyClientHandler());
		
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(HOST, PORT));
		// �ȴ����ǵ����� �ȴ��������, ���Ի�ȡsession
		connectFuture.awaitUninterruptibly();
		ioSession = connectFuture.getSession();
		
		ioSession.write("hello");
		// �ȴ��ر�����
		ioSession.getCloseFuture().awaitUninterruptibly();
		
		connector.dispose();
		
	}
}






















