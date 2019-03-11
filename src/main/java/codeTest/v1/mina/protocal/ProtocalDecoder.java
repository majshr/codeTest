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
	 * session中保存上下文
	 */
	private final AttributeKey CONTEXT = new AttributeKey(this.getClass(), "context");
	/**
	 * 编码
	 */
	private final Charset charset;
	/**
	 * 限制数据包最大长度
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
		// 数据包头长度
		final int packHeadLength = 4 + 1;
		
		// 数据从上下文中取出来(可能本次数据不全, 需要和上次遗留在上下文中的数据一起凑成全的数据)
		Context ctx = this.getContext(session);
		ctx.append(in);
		IoBuffer buf = ctx.buf;
		
		// 开始读取
		buf.flip();
		// 如果有content内容
		while(buf.remaining() >= packHeadLength){
			// 有数据可以读
			buf.mark();
			// 数据包长度
			int length = buf.getInt();
			// 数据包版本
			byte flag = buf.get();
			if(length < 0 || length > maxPackageLen){
				// 如果数据包长度小于0, 或大于最大长, 当成无效数据, 清空缓冲区
				buf.reset();
				break;
			}else if(length >= packHeadLength && length <= buf.remaining()){
				// 正常情况, 数据包长度大于包头长, 数据包长度小于缓冲区剩余数据长度(包含一个完整的数据包)
				int oldLimit = buf.limit();
				buf.limit(buf.position() + length - packHeadLength);
				String content = buf.getString(ctx.decoder);
				buf.limit(oldLimit);
				ProtocalPackage pac = new ProtocalPackage(flag, content);
				out.write(pac);
			} else{
				// 半包, 下一次取, 直到取到整包
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
				// 清空操作
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
	 * 解决半包的问题
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
	 * 解决半包问题
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
		 * 外部IoBuffer放到buf里
		 * @param in
		 */
		public void append(IoBuffer in){
			buf.put(in);
		}
		
	}
	
}
















