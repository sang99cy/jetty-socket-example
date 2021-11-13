package com.dineshsawant.websocketdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@ComponentScan("com.dineshsawant.websocketdemo")
//@EnableAutoConfiguration
@SpringBootApplication
public class WebsocketdemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebsocketdemoApplication.class, args);
	}
}
