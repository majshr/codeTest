package codeTest.v1.netty2.ssy07thrift;

import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {
	public static void main(String[] args) throws TTransportException {
		TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
		THsHaServer.Args arg = new THsHaServer.Args(socket);
	}
}
