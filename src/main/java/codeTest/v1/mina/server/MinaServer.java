package codeTest.v1.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import codeTest.v1.mina.filter.server.MyFilterServerVer1;
import codeTest.v1.mina.server.handler.MyServerHandler;

public class MinaServer {
	static int PORT = 7080;
	static int BUFFER_SIZE = 1024;
	static IoAcceptor accept = null;
	public static void main(String[] args) {
		try {
			accept = new NioSocketAcceptor();
			// ���ñ��������
			accept.getFilterChain().addLast("codec", new ProtocolCodecFilter(
					new TextLineCodecFactory(
							Charset.forName("UTF-8"), 
							LineDelimiter.WINDOWS.getValue(), 
							LineDelimiter.WINDOWS.getValue())));
			
			accept.getFilterChain().addFirst("myfilter", new MyFilterServerVer1());
			
			accept.getSessionConfig().setReadBufferSize(BUFFER_SIZE);
			
			// ���ö೤ʱ��û�ж�д����, ��������
			accept.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			
			// �Զ���ҵ����
			accept.setHandler(new MyServerHandler());
			
			accept.bind(new InetSocketAddress(PORT));
			
			System.out.println("aaaaa������������������������������������");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




























