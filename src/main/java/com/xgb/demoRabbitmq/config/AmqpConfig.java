package com.xgb.demoRabbitmq.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;
import com.xgb.demoRabbitmq.common.ToolsHttpRequest;
import com.xgb.demoRabbitmq.dto.DtoMessage;


//@Configuration
public class AmqpConfig {
//	private static Logger logger = LoggerFactory.getLogger(AmqpConfig.class);
//	public static final String EXCHANGE   = "exchange-test"; 
//    public static final String QUEUE_NAME = "queue-test";  
//    public static final String DEAD_QUEUE_NAME = "dead-queue-test";  
//    
//    @Value("${spring.rabbitmq.host}")
//    private String addresses;
//    
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//    
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//    
//    @Value("${spring.rabbitmq.virtual-host}")
//    private String host;
//    
//    
//    @Bean
//    public ConnectionFactory connectionFactory(){
//    	CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
//        connectionFactory.setAddresses(addresses);  
//        connectionFactory.setUsername(username);  
//        connectionFactory.setPassword(password);  
//        connectionFactory.setVirtualHost(host);  
//        connectionFactory.setPublisherConfirms(true); //必须要设置 自动创建的ConnectionFactory无法完成事件的回调
//        return connectionFactory;  
//    }
//    
//    
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public RabbitTemplate rabbitTemplate(){
//    	RabbitTemplate template = new RabbitTemplate(connectionFactory());
//		return template;
//    }
//    
//    @Bean
//    public DirectExchange defaultExchange(){
//		return new DirectExchange(EXCHANGE);
//    	
//    }
//    
//    
//    
//    @Bean
//    public Queue queue(){
//		return new Queue(QUEUE_NAME,true);
//    }
//    
//    @Bean
//    public Queue deadQueue(){
////    	Queue queue = new Queue(DEAD_QUEUE_NAME,true);
//    	Map<String, Object> map = new HashMap<String, Object>();
//    	map.put("x-message-ttl", 15000);
//    	map.put("x-dead-letter-exchange", AmqpConfig.EXCHANGE);
//    	map.put("x-dead-letter-routing-key", AmqpConfig.QUEUE_NAME);
//    	Queue queue = new Queue(DEAD_QUEUE_NAME, true, false, false, map);
//    	System.out.println("------------------------------------------------------");
//		System.out.println("Create queue Ok!!" + queue);
//		System.out.println("------------------------------------------------------");
//		return queue;
////		return new Queue(DEAD_QUEUE_NAME,true);
//    }
//    
//    @Bean
//    public Binding binding(){
//    	return BindingBuilder.bind(queue()).to(defaultExchange()).with(AmqpConfig.EXCHANGE);
//    }
//    
//    @Bean
//    public SimpleMessageListenerContainer messageContainer(){
//    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());  
//        container.setQueues(queue());  
//        container.setExposeListenerChannel(true);  
//        container.setMaxConcurrentConsumers(1);  
//        container.setConcurrentConsumers(1);  
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认  
//        System.out.println("------------------------------------------------------");
//		System.out.println("Create SimpleMessageListenerContainer Ok!!" + container);
//		System.out.println("------------------------------------------------------");
//        container.setMessageListener(new ChannelAwareMessageListener() { 
//			
//			@Override
//			public void onMessage(Message message, Channel channel) throws Exception {
//				try{
//					String flag = "fail";
//					String json = new String(message.getBody());
//					System.out.println("消费消息  consumer -- : " + message.getMessageProperties() + " : " + json );
//					DtoMessage messageJson = DtoMessage.transformationToJson(json);
//					if(messageJson != null) {
//						if(messageJson.getRequestMethod() != null) {
//							if("get".equals(messageJson.getRequestMethod())) {
//								flag = ToolsHttpRequest.sendGet(messageJson.getUrl(), messageJson.getParams());
//							}else if("post".equals(messageJson.getRequestMethod())) {
//								flag = ToolsHttpRequest.sendPost(messageJson.getUrl(), messageJson.getParams());
//							}
//						}
//					}
//					
//					if("ok".equals(flag)) {
//						System.out.println("Ack message ："  + json);
//						basicACK(message, channel);//处理正常 ACK消息
//					}else {
//						System.out.println("NAck message ："  + json);
//						basicNACK(message, channel);//处理异常 NACK消息
//					}
//				}catch(Exception e){
//					e.printStackTrace();
//					//TODO 业务处理
//					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
//				}
//			}
//		});
//    	
//		return null;
//    }
//    
//    
//  //正常消费掉后通知mq服务器移除此条mq
//  	private void basicACK(Message message,Channel channel){
//  		try{
//  			channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//  		}catch(IOException e){
//  			logger.error("通知服务器移除mq时异常，异常信息：" + e);
//  		}
//  	}
//  	//处理异常，mq重回队列
//  	private void basicNACK(Message message,Channel channel){
//  		try{
//  			channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
//  		}catch(IOException e){
//  			logger.error("mq重新进入服务器时出现异常，异常信息：" + e);
//  		}
//  	}
 
}
