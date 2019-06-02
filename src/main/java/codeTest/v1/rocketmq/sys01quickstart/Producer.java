package codeTest.v1.rocketmq.sys01quickstart;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class Producer {
	public static void main(String[] args) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		DefaultMQProducer producer = new DefaultMQProducer("quickstart_producer");
		producer.setNamesrvAddr("localhost:9876");
		producer.start();
		for(int i = 0; i < 100; i++){
			Message msg = new Message("TopicQuickStart", //topic,消息主题，想象成一个队列
					"TagA", // tag
					("Hello RocketMQ" + i).getBytes() //body
					);
			SendResult sendResult = producer.send(msg);
			System.out.println(sendResult);
		}
		producer.shutdown();
	}
}






















