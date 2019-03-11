package codeTest.v1.mina.server.handler;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
/**
 * 服务handler类
 * @author maj
 *
 */
public class MyServerHandler extends IoHandlerAdapter{

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("出现异常");
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		System.out.println("input close");
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// 接收数据
		String msg = (String) message;
		System.out.println("服务端接收到数据" + msg);
		if(msg.equals("exit")){
			session.close();
		}
		
		// 发送数据
		Date date = new Date();
		session.write(date);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("发送消息");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("session 关闭");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("session 创建");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("进入休眠");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("session 打开");
	}
	
}
