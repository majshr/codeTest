package codeTest.v1.mina.protocal;

import org.apache.mina.filter.codec.serialization.ObjectSerializationEncoder;

/**
 * 协议包
 * 
 * @author maj
 *
 */
public class ProtocalPackage {
	private int length;
	private byte version;
	private String content;

	public ProtocalPackage(byte version, String content) {
		super();
		this.version = version;
		this.content = content;
		// 4: length字节数 1: version字节数
		length = content.getBytes().length + 4 + 1;

	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ProtocalPackage [length=" + length + ", version=" + version + ", content=" + content + "]";
	}

}
