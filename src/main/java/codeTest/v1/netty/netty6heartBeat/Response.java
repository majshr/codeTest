package codeTest.v1.netty.netty6heartBeat;

import java.io.Serializable;

public class Response implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String responseMessage;
	
	@Override
	public String toString() {
		return "[ " + id + "    " + name + "    " +  responseMessage + " ]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
