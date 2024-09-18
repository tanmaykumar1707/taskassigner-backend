package com.assinger.taskusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class TaskusersApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskusersApplication.class, args);
	}


}