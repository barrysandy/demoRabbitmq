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
import org.springframework.amqp.rabbit.core.RabbitAdmin;
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


@Configuration
public class AmqpConfig {
	private static Logger logger = LoggerFactory.getLogger(AmqpConfig.class);
	public static final String TEST_QUEUE_NAME = "test-queue";  
    public static final String TEST_EXCHANGE = "test-exchange"; 
    public static final String TEST_TTL_QUEUE_NAME = "test-ttl-queue";  
    public static final String TEST_TTL_EXCHANGE = "test-ttl-exchange"; 
    
    @Value("${spring.rabbitmq.host}")
    private String addresses;
    
    @Value("${spring.rabbitmq.username}")
    private String username;
    
    @Value("${spring.rabbitmq.password}")
    private String password;
    
    @Value("${spring.rabbitmq.virtual-host}")
    private String host;
    
    
    @Bean
    public ConnectionFactory connectionFactory(){
    	CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
        connectionFactory.setAddresses(addresses);  
        connectionFactory.setUsername(username);  
        connectionFactory.setPassword(password);  
        connectionFactory.setVirtualHost(host);  
        connectionFactory.setPublisherConfirms(true); //必须要设置 自动创建的ConnectionFactory无法完成事件的回调
        return connectionFactory;  
    }
    
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(){
    	RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
    }
    
    @Bean
    public DirectExchange defaultExchange(){
		DirectExchange directExchange = new DirectExchange(TEST_EXCHANGE);
    	System.out.println("------------------------------------------------------");
		System.out.println("Create defaultExchange() Ok!!" + directExchange);
		System.out.println("------------------------------------------------------");
		return directExchange;
    	
    }
    
    @Bean
    public DirectExchange ttlExchange(){
    	DirectExchange directExchange = new DirectExchange(TEST_TTL_EXCHANGE);
    	System.out.println("------------------------------------------------------");
		System.out.println("Create ttlExchange() Ok!!" + directExchange);
		System.out.println("------------------------------------------------------");
		return directExchange;
    }
    
    
    
    @Bean
    public Queue queue(){
    	Queue queue = new Queue(TEST_QUEUE_NAME, true);
    	System.out.println("------------------------------------------------------");
		System.out.println("Create queue() Ok!!" + queue);
		System.out.println("------------------------------------------------------");
		return queue;
    }
    
    @Bean
    public Queue ttlQueue(){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("x-message-ttl", 15000);
    	map.put("x-dead-letter-exchange", TEST_EXCHANGE);
    	map.put("x-dead-letter-routing-key", TEST_QUEUE_NAME);
    	Queue queue = new Queue(TEST_TTL_QUEUE_NAME, true, false, false, map);
    	System.out.println("------------------------------------------------------");
		System.out.println("Create ttlQueue() Ok!!" + queue);
		System.out.println("------------------------------------------------------");
		return queue;
    }
    
    
    @Bean
    public Binding binding(){
    	System.out.println("------------------------------------------------------");
		System.out.println("binding "+ TEST_QUEUE_NAME +" to " + TEST_QUEUE_NAME + " OK !!" );
		System.out.println("------------------------------------------------------");
    	return BindingBuilder.bind(queue()).to(defaultExchange()).with(TEST_QUEUE_NAME);
    }
    
    
    @Bean
    public SimpleMessageListenerContainer messageContainer(){
    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());  
        container.setQueues(queue());  
        container.setExposeListenerChannel(true);  
        container.setMaxConcurrentConsumers(2);  
        container.setConcurrentConsumers(2);  
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认  
        container.setMessageListener(new ChannelAwareMessageListener() { 
			
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				try{
					String flag = "fail";
					String messageBody = new String(message.getBody());
					System.out.println("Consumer Message -- ID: " + message.getMessageProperties().getMessageId() + " Properties: " + message.getMessageProperties() + " messageBody: " + messageBody );
					DtoMessage messageJson = DtoMessage.transformationToJson(messageBody);
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
						System.out.println("Ack message ："  + messageBody);
						basicACK(message, channel);//处理正常 ACK消息
					}else {
						System.out.println("NAck message ："  + messageBody);
						basicNACK(message, channel);//处理异常 NACK消息
					}
				}catch(Exception e){
					e.printStackTrace();
					//TODO 业务处理
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
				}
			}
		});
    	
		return container;
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
