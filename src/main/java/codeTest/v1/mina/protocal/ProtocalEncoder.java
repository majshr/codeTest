package codeTest.v1.mina.protocal;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ProtocalEncoder extends ProtocolEncoderAdapter{
	private final Charset charset;
	public ProtocalEncoder(Charset charset){
		this.charset = charset;
	}
	/**
	 * 编码: 将java对象转换成二进制流
	 */
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		if(message instanceof ProtocalPackage){
			ProtocalPackage pac = (ProtocalPackage) message;	
			IoBuffer buf = IoBuffer.allocate(pac.getLength());
			buf.setAutoExpand(true);
			buf.putInt(pac.getLength());
			buf.put(pac.getVersion());
			buf.put(pac.getContent().getBytes());
			
			buf.flip();
			out.write(buf);
		}
	}

}
