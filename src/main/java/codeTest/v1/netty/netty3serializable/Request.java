package codeTest.v1.netty.netty3serializable;

import java.io.Serializable;

/**
 * ���л�һ��Ҫʵ��Serializable�ӿ� ���������netty��Ŀ�����໥ͨ��, Req��Resp��������Ŀ���붼����,
 * �Ұ�·��������ͬ, �������л����ܳɹ�
 */
public class Request implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id ;
	private String name ;
	private String requestMessage ;
	/**
	 * �ļ���Ϣ
	 */
	private byte[] attachment;
	
	@Override
	public String toString() {
		return "[ " + id + "    " + name + "    " +  requestMessage + " ]";
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
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
}
