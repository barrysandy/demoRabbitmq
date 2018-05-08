package com.xgb.demoRabbitmq.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xgb.demoRabbitmq.config.AmqpConfig;
import com.xgb.demoRabbitmq.dto.DtoMessage;
import com.xgb.demoRabbitmq.enumeration.EnumsMQAck;
import com.xgb.demoRabbitmq.publish.DeadLetterPublishService;

@RestController
public class DemoController {
	
	@Autowired DeadLetterPublishService deadLetterPublishService;
	
	@RequestMapping("/send")
	public String sendMsg(String message){
		DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), "http://localhost/receive", 
				"get", "message=" + message, "");
		String msg = DtoMessage.transformationToJson(dtoMessage);
		System.out.println("deadLetterPublishService: " + deadLetterPublishService);
		deadLetterPublishService.send(AmqpConfig.TEST_TTL_QUEUE_NAME, msg);
		System.out.println("------------------------------------------------------");
		System.out.println("send message Ok!!" + msg);
		System.out.println("------------------------------------------------------");
		return "send message Ok!!" + msg;
	}
	
	/**
	 * (所有消息处理的返回如果为‘ok’则ack消息，‘fail’则nack消息，只对需要手动确认的消息有效)
	 * @param message
	 * @return
	 */
	@RequestMapping("receive")
	public String receive(String message){
		try {
			System.out.println("------------------------------------------------------");
			System.out.println("receive message : " + message);
			System.out.println("------------------------------------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
			return EnumsMQAck.ACK_FAIL;
		}
		return EnumsMQAck.ACK_OK;
	}
	
	
}
