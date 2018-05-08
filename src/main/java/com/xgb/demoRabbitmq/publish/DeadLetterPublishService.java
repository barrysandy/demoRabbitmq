package com.xgb.demoRabbitmq.publish;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deadLetterPublishService")
public class DeadLetterPublishService {
	
	@Autowired
	private RabbitTemplate rmqpTemplate;
    
    public void send(String routingKey, Object obj) {  
    	rmqpTemplate.convertAndSend(routingKey, obj);
//    	String msgId = UUID.randomUUID().toString();
//		Message message = MessageBuilder.withBody(obj.toString().getBytes())
//				.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
//				.setCorrelationId(msgId).build();
//		CorrelationData correlationData = new CorrelationData(msgId);
//		
//		MessageProperties messageProperties = message.getMessageProperties();
//		messageProperties.setExpiration("5000");
//		System.out.println("------------------------------------------------------");
//		System.out.println("binding 将 msgId 与 message 的关系保存起来,例如放到缓存中  !!" );
//		System.out.println("------------------------------------------------------");
//		rmqpTemplate.correlationConvertAndSend(message, correlationData);
    }  
    
}
