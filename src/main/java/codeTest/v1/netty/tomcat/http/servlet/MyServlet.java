package codeTest.v1.netty.tomcat.http.servlet;

import java.io.UnsupportedEncodingException;

import codeTest.v1.netty.tomcat.http.Request;
import codeTest.v1.netty.tomcat.http.Response;
import codeTest.v1.netty.tomcat.http.Servlet;

public class MyServlet extends Servlet{

	@Override
	public void doGet(Request request, Response response) {
		try {
			response.write(request.getParmeter("name"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doPost(Request request, Response response) {
		// TODO Auto-generated method stub
		
	}
	
}
