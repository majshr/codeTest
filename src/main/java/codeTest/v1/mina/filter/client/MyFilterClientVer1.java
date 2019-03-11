package codeTest.v1.mina.filter.client;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

public class MyFilterClientVer1 extends IoFilterAdapter{

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		System.out.println("�Զ��������client->message Reveived");
		nextFilter.messageReceived(session, message);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
		System.out.println("�Զ��������client->messageSent");
		nextFilter.messageSent(session, writeRequest);
	}

}
