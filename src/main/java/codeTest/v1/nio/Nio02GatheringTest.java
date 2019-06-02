package codeTest.v1.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Nio02GatheringTest {
	public static void main(String[] args) throws IOException {
		ServerSocketChannel channel = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress("localhost", 8899);
		channel.socket().bind(address);
		SocketChannel socketChannel = channel.accept();
		
		int messageLength = 2 + 3 + 4;
		ByteBuffer[] buffers = new ByteBuffer[3];
		buffers[0] = ByteBuffer.allocate(2);
		buffers[1] = ByteBuffer.allocate(3);
		buffers[2] = ByteBuffer.allocate(4);
		
		while(true){
			int bytesRead = 0;
			// 读取满一个缓冲区数组，进行回显操作；
			//如果Client发来的信息超过messageLength，先操作一次满的，再读取下一次
			// 如果Client发来消息不足messageLength，会一直循环在这
			while(bytesRead < messageLength){
				bytesRead += socketChannel.read(buffers);
				System.out.println(bytesRead);
			}
			
			// 打印收到信息后的Buffer的size
			Arrays.asList(buffers).stream()
			.map(buffer ->"position:" +  buffer.position() + ", limit:" + buffer.limit())
			.forEach(System.out::println);
			
			// 重置buffer，读取信息回显
			Arrays.asList(buffers).forEach(buffer -> {
				buffer.flip();
			});
			long bytesWritten = 0;
			while(bytesWritten < messageLength){
				bytesWritten += socketChannel.write(buffers);
			}
			
			Arrays.asList(buffers).forEach(buffer -> {
				buffer.clear();
			});
			
			System.out.println("bytesRead: " + bytesRead + ", bytesWritten:" + bytesWritten);
		}
	}
}



























