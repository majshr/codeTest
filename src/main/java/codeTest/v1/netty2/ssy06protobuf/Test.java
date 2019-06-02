package codeTest.v1.netty2.ssy06protobuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.jboss.marshalling.ByteInputStream;

import com.google.protobuf.InvalidProtocolBufferException;

import codeTest.v1.netty2.ssy06protobuf.entity.MyDataInfo;

public class Test {
	public static void main(String[] args) throws InvalidProtocolBufferException {
		MyDataInfo.Dog dog = MyDataInfo.Dog.newBuilder().setName("狗").setAge(10).build();
		
		// 转换为二进制信息
		byte[] byteData = dog.toByteArray();
		
		// 解析二进制
		MyDataInfo.Dog newDog = dog.parseFrom(byteData);
		
		System.out.println(newDog.getAge() + "==" + newDog.getName());
		
	}
}
