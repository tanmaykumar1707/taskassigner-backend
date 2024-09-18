package com.assinger.taskeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TaskeurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskeurekaApplication.class, args);
	}

}
