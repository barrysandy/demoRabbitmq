package com.xgb.demoRabbitmq;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xgb.demoRabbitmq.config.AmqpConfig;
import com.xgb.demoRabbitmq.dto.DtoMessage;
import com.xgb.demoRabbitmq.publish.DeadLetterPublishService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoRabbitmqApplicationTests {
	@Autowired DeadLetterPublishService deadLetterPublishService;

	@Test
	public void contextLoads() {
		String message = "1212121";
		DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), "http://localhost/receive", 
				"get", "message=" + message, "");
		String msg = DtoMessage.transformationToJson(dtoMessage);
		System.out.println("deadLetterPublishService: " + deadLetterPublishService);
		deadLetterPublishService.send(AmqpConfig.TEST_TTL_QUEUE_NAME, msg);
		System.out.println("------------------------------------------------------");
		System.out.println("send message Ok!!" + msg);
		System.out.println("------------------------------------------------------");
		
	}

}
