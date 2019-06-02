package codeTest.v1.netty2.ssy06protobuf;

import codeTest.v1.netty2.ssy06protobuf.entity.MyDataInfo;
import codeTest.v1.netty2.ssy06protobuf.entity.MyDataInfo.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
		MyDataInfo.MyMessage.DataType type = msg.getDataType();
		
		switch(type){
		case CatType:
			MyDataInfo.Cat cat = msg.getCat();
			System.out.println(cat.getName() + "==" + cat.getAge());
			break;
		case DogType:
			MyDataInfo.Dog dog = msg.getDog();
			System.out.println(dog.getName() + "==" + dog.getAge());
			break;
		case PersonType:
			MyDataInfo.Person person = msg.getPerson();
			System.out.println(person.getName() + "==" + person.getAge() + person.getAddress());
			break;
		default:
			break;
			
		}
	}

}
