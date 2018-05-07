package com.xgb.demoRabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.xgb.demoRabbitmq")
public class DemoRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRabbitmqApplication.class, args);
	}
}
