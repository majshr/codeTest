package codeTest.v1.nio.sys01chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {
	private static Map<String, SocketChannel> clientMap = new HashMap<>();
	
	public static void main(String[] args) throws IOException{
		// 固定不变，获取服务端Channel，设置非阻塞；根据服务端Channel获取服务端Socket，绑定端口号
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress(8899));
		
		// 生成选择器，Channel注册到selector上边
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		// 注册完成后，进行事件处理
		while(true){
			// 返回关注的事件数量，阻塞到有准备好的通道
			selector.select();
			
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			
			readyKeys.forEach(selectionKey -> {
				// 与客户端建立连接的Channel
				SocketChannel clientChannel = null;
				try{
					if(selectionKey.isAcceptable()){
						// 1,一定是ServerSocketChannel
						// 连接建立之前仅仅是把ServerSocketChannel注册到了Selector，没有把任何的Channel注册到Selector上
						ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
						// 2,建立连接，获取连接的Channel（ServerSocketChannel工作完成）
						clientChannel = channel.accept();
						// 3,新Channel注册到选择器上
						clientChannel.configureBlocking(false);
						clientChannel.register(selector, SelectionKey.OP_READ);
						/**共注册了两个channel，ServerSocketChannel（处理连接）和连接的SocketChannel（处理数据通信）*/
						
						String key = "[" + UUID.randomUUID().toString() + "]";
						clientMap.put(key, clientChannel);
						
					} else if(selectionKey.isReadable()){
						// 只给SocketChannel注册了read事件，所以返回是这个
						clientChannel = (SocketChannel) selectionKey.channel();
						ByteBuffer readBuffer = ByteBuffer.allocate(1024);
						
						int count = clientChannel.read(readBuffer);
						String recMsg = null;
						if(count > 0){
							readBuffer.flip();
							Charset charset = Charset.forName("utf-8");
							recMsg = String.valueOf(charset.decode(readBuffer).array());
							System.out.println(clientChannel + ":" + recMsg);
						}
						
						String senderKey = null;
						// 找到发送者，然后发送给其他人
						for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
							if(clientChannel == entry.getValue()){
								senderKey = entry.getKey();
							}
						}
						for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
							SocketChannel value = entry.getValue();
							ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
							writeBuffer.put((senderKey + "发送了消息:" + recMsg).getBytes());
							writeBuffer.flip();
							value.write(writeBuffer);
						}
					}
				}catch(Exception e){
					
				}
			});
			
			// 处理完的通道清除
			readyKeys.clear();
		}
		
	}
}






























