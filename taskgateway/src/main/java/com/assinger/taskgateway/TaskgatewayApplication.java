package com.assinger.taskgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;


@SpringBootApplication
public class TaskgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskgatewayApplication.class, args);
	}


}
