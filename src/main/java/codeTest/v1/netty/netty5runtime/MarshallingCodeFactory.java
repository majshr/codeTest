package codeTest.v1.netty.netty5runtime;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * Marshalling�������������
 */
public class MarshallingCodeFactory {
	/**
	 * ����Jboss Marshalling������
	 * @return
	 */
	public static MarshallingDecoder buildMarshllingDecoder(){
		//����ͨ��Marshalling������ľ�ͨ������ȡMarshallingʵ������ ����serial��ʶ��������java���л���������
		final MarshallerFactory marshallerFactory = 
				Marshalling.getProvidedMarshallerFactory("serial");
		
		//������MarshallingConfiguration���������˰汾��Ϊ5 
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		
		//����marshallerFactory��configuration����provider
		UnmarshallerProvider provider = 
				new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		
		//����Netty��MarshallingDecoder�������������ֱ�Ϊprovider(�������н��빤�����Ǹ�����)��
		//������Ϣ���л������󳤶�(�Լ�����1024k*1024k)
		// ������Ҫָ����С, ���벻��Ҫָ����С
		MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024 * 1024 * 1);
		return decoder;
	}
	
    /**
     * ����Jboss Marshalling������MarshallingEncoder
     * @return MarshallingEncoder
     */
	public static MarshallingEncoder buildMarshallingEncoder(){
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
		//����Netty��MarshallingEncoder����MarshallingEncoder����ʵ�����л��ӿڵ�POJO�������л�Ϊ����������
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}
}
























