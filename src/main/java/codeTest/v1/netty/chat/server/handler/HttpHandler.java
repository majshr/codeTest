package codeTest.v1.netty.chat.server.handler;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
/**
 * netty����: ���������0, ����ʵ����ķ���, ���ǽӿڵķ���
 * @author maj
 * netty��, �ṩ�˷ǳ��ḻ�Ĺ�����, �õ����Ѿ��ǽ����, ��Ҫ������Ĵ���
 *
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		// ��ȡurl
		String uri = request.uri();
		// �������urlû��ֵ, Ĭ�Ϸ���chat.html
		String page = uri.equals("/") ? "/chat.html" : uri;
		
		RandomAccessFile file = new RandomAccessFile(getFileFromRoot(page), "r");
		
		String contextType = "text/html;";
		if(uri.endsWith(".css")){
			contextType = "text/css;";
		}else if(uri.endsWith(".js")){
			contextType = "text/javascript;";
		}else if(uri.toLowerCase().matches("(jpg|png|gif|ico)$")){
			String ext = uri.substring(uri.lastIndexOf('.'));
			contextType = "image/" + ext + ";";
		}
		HttpResponse response = 
				new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, contextType + "charset=utf-8");
		
		boolean isKeepAlive = HttpHeaders.isKeepAlive(request);
		if(isKeepAlive){
			response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
			response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}
		
		// дЭ��, д�ļ�
		ctx.write(response);
		ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
		
		// ��ջ�����
		ChannelFuture f = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		if(!isKeepAlive){
			f.addListener(ChannelFutureListener.CLOSE);
		}
		
		file.close();
	}
	
	// classPath ��ȡ��ĸ�Ŀ¼
	private URL baseUrl = HttpHandler.class.getProtectionDomain().getCodeSource().getLocation();
	
	private final String WEB_ROOT = "webroot";
	
	/**
	 * �Ӹ�Ŀ¼������Դ����
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 */
	private File getFileFromRoot(String fileName) throws URISyntaxException{
		String path = null;
		if(baseUrl.toString().startsWith("file:/")){
			path = baseUrl.toString().replace("file:/", "") + WEB_ROOT + fileName;
		}else{
			path = baseUrl.toString() + WEB_ROOT + fileName;
		}
		
		return new File(path);
	}

}





















