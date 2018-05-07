package com.xgb.demoRabbitmq.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xgb.demoRabbitmq.config.AmqpConfig;
import com.xgb.demoRabbitmq.dto.DtoMessage;
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
//		deadLetterPublishService.send(AmqpConfig.DEAD_QUEUE_NAME, msg);
		System.out.println("------------------------------------------------------");
		System.out.println("send message Ok!!" + msg);
		System.out.println("------------------------------------------------------");
		return "send message Ok!!" + msg;
	}
	
	
	@RequestMapping("receive")
	public String receive(String message){
		DtoMessage dtoMessage = DtoMessage.transformationToJson(message);
		System.out.println("------------------------------------------------------");
		System.out.println("receive message : " + dtoMessage);
		System.out.println("------------------------------------------------------");
		return "receive message : " + dtoMessage;
	}
	
	
}
