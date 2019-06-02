package codeTest.v1.nio.sys01chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {
	public static void main(String[] args) {
		try{
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			
			Selector selector = Selector.open();
			
			// 客户端是向服务端发起连接的
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			
			socketChannel.connect(new InetSocketAddress("localhost", 8899));
			
			while(true){
				selector.select();
				Set<SelectionKey> readyKeys = selector.selectedKeys();
				
				for(SelectionKey readyKey : readyKeys){
					if(readyKey.isConnectable()){
						// 只注册了SocketChannel,所以只能返回SocketChannel对象
						SocketChannel clientChannel = (SocketChannel) readyKey.channel();
						
						if(clientChannel.isConnectionPending()){
							// 连接建立好了
							clientChannel.finishConnect();
							
							ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
							writeBuffer.put((LocalDateTime.now() + "连接成功!").getBytes());
							
							writeBuffer.flip();
							clientChannel.write(writeBuffer);
							
							ExecutorService pool = Executors.newSingleThreadExecutor();
							pool.submit(() -> {
								while(true){
									writeBuffer.clear();
									// 读取控制台输入的一行数据
									BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
									String sendMessage = br.readLine();
									writeBuffer.put(sendMessage.getBytes());
									writeBuffer.flip();
									clientChannel.write(writeBuffer);
								}
							});
						}
						
						clientChannel.register(selector, SelectionKey.OP_READ);
					} else if(readyKey.isReadable()){
						SocketChannel clientChannel = (SocketChannel) readyKey.channel();
						ByteBuffer readBuffer = ByteBuffer.allocate(1024);
						
						if(clientChannel.read(readBuffer) > 0){
							Charset charset = Charset.forName("utf-8");
							readBuffer.flip();
							System.out.println(charset.decode(readBuffer).array());
						}
					}
				}
				
				readyKeys.clear();
			}
		}catch(Exception e){
			
		}
	}
}

































