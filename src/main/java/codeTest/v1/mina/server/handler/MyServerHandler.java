package codeTest.v1.mina.server.handler;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
/**
 * ����handler��
 * @author maj
 *
 */
public class MyServerHandler extends IoHandlerAdapter{

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("�����쳣");
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		System.out.println("input close");
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// ��������
		String msg = (String) message;
		System.out.println("����˽��յ�����" + msg);
		if(msg.equals("exit")){
			session.close();
		}
		
		// ��������
		Date date = new Date();
		session.write(date);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("������Ϣ");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("session �ر�");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("session ����");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("��������");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("session ��");
	}
	
}
