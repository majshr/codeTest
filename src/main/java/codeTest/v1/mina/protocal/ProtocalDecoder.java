package codeTest.v1.mina.protocal;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ProtocalDecoder implements ProtocolDecoder{
	/**
	 * session�б���������
	 */
	private final AttributeKey CONTEXT = new AttributeKey(this.getClass(), "context");
	/**
	 * ����
	 */
	private final Charset charset;
	/**
	 * �������ݰ���󳤶�
	 */
	private int maxPackageLen = 100;
	
	public ProtocalDecoder() {
		this(Charset.defaultCharset());
	}
	
	public ProtocalDecoder(Charset charset) {
		super();
		this.charset = charset;
	}


	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		// ���ݰ�ͷ����
		final int packHeadLength = 4 + 1;
		
		// ���ݴ���������ȡ����(���ܱ������ݲ�ȫ, ��Ҫ���ϴ��������������е�����һ��ճ�ȫ������)
		Context ctx = this.getContext(session);
		ctx.append(in);
		IoBuffer buf = ctx.buf;
		
		// ��ʼ��ȡ
		buf.flip();
		// �����content����
		while(buf.remaining() >= packHeadLength){
			// �����ݿ��Զ�
			buf.mark();
			// ���ݰ�����
			int length = buf.getInt();
			// ���ݰ��汾
			byte flag = buf.get();
			if(length < 0 || length > maxPackageLen){
				// ������ݰ�����С��0, ��������, ������Ч����, ��ջ�����
				buf.reset();
				break;
			}else if(length >= packHeadLength && length <= buf.remaining()){
				// �������, ���ݰ����ȴ��ڰ�ͷ��, ���ݰ�����С�ڻ�����ʣ�����ݳ���(����һ�����������ݰ�)
				int oldLimit = buf.limit();
				buf.limit(buf.position() + length - packHeadLength);
				String content = buf.getString(ctx.decoder);
				buf.limit(oldLimit);
				ProtocalPackage pac = new ProtocalPackage(flag, content);
				out.write(pac);
			} else{
				// ���, ��һ��ȡ, ֱ��ȡ������
				buf.clear();
				break;
			}
			
			if(buf.hasRemaining()){
				// 
				IoBuffer temp = IoBuffer.allocate(maxPackageLen).setAutoExpand(true);
				temp.put(buf);
				temp.flip();
				buf.put(temp);
			}else{
				// ��ղ���
				buf.reset();
			}
		}
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void dispose(IoSession session) throws Exception {
		session.removeAttribute(CONTEXT);
	}

	public Charset getCharset() {
		return charset;
	}

	public int getMaxPackageLen() {
		return maxPackageLen;
	}

	public void setMaxPackageLen(int maxPackageLen) {
		this.maxPackageLen = maxPackageLen;
	}

	/**
	 * ������������
	 * @return
	 */
	public Context getContext(IoSession session){
		Context ctx = (Context) session.getAttribute(CONTEXT);
		if(ctx == null){
			ctx = new Context();
			session.setAttribute(CONTEXT, ctx);
		}
		return ctx;
	}
	
	/**
	 * ����������
	 * @author maj
	 *
	 */
	private class Context{
		private final CharsetDecoder decoder;
		
		private IoBuffer buf;

		public Context() {
			decoder = charset.newDecoder();
			buf = IoBuffer.allocate(80).setAutoExpand(true);
		}
		
		public void rest(){
			decoder.reset();
		}
		
		/**
		 * �ⲿIoBuffer�ŵ�buf��
		 * @param in
		 */
		public void append(IoBuffer in){
			buf.put(in);
		}
		
	}
	
}
















