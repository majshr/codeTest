package codeTest.v1.netty.tomcat.http;

import java.util.List;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

public class Request {
	
	private ChannelHandlerContext ctx;
	private HttpRequest r;
	
	public Request(ChannelHandlerContext ctx, HttpRequest req) {
		this.ctx = ctx;
		this.r = req;
	}
	
	public String getUri(){
		return r.getUri();
	}
	
	public String getMethod(){
		return r.getMethod().name();
	}
	
	public Map<String, List<String>> getParmeters(){
		QueryStringDecoder decoder = new QueryStringDecoder(getUri());
		return decoder.parameters();
	}

	public String getParmeter(String name){
		Map<String, List<String>> params = getParmeters();
		List<String> param = params.get(name);
		if(param == null){
			return null;
		}else{
			return param.get(0);
		}
	}
}






















