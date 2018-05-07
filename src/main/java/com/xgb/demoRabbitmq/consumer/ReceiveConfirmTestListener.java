package com.xgb.demoRabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.xgb.demoRabbitmq.common.ToolsHttpRequest;
import com.xgb.demoRabbitmq.dto.DtoMessage;

import java.io.IOException;


@Service("receiveConfirmTestListener")
public class ReceiveConfirmTestListener implements ChannelAwareMessageListener {  
	private static Logger logger = LoggerFactory.getLogger(ReceiveConfirmTestListener.class);

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try{
			String flag = "fail";
			String json = new String(message.getBody());
			System.out.println("消费消息  consumer -- : " + message.getMessageProperties() + " : " + json );
			DtoMessage messageJson = DtoMessage.transformationToJson(json);
			if(messageJson != null) {
				if(messageJson.getRequestMethod() != null) {
					if("get".equals(messageJson.getRequestMethod())) {
						flag = ToolsHttpRequest.sendGet(messageJson.getUrl(), messageJson.getParams());
					}else if("post".equals(messageJson.getRequestMethod())) {
						flag = ToolsHttpRequest.sendPost(messageJson.getUrl(), messageJson.getParams());
					}
				}
			}
			
			if("ok".equals(flag)) {
				System.out.println("Ack message ："  + json);
				basicACK(message, channel);//处理正常 ACK消息
			}else {
				System.out.println("NAck message ："  + json);
				basicNACK(message, channel);//处理异常 NACK消息
			}
		}catch(Exception e){
			e.printStackTrace();
			//TODO 业务处理
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
		}
	}

	//正常消费掉后通知mq服务器移除此条mq
	private void basicACK(Message message,Channel channel){
		try{
			channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
		}catch(IOException e){
			logger.error("通知服务器移除mq时异常，异常信息：" + e);
		}
	}
	//处理异常，mq重回队列
	private void basicNACK(Message message,Channel channel){
		try{
			channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
		}catch(IOException e){
			logger.error("mq重新进入服务器时出现异常，异常信息：" + e);
		}
	}
  
}  
