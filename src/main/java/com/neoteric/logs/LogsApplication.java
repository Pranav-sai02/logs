package com.neoteric.logs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogsApplication.class, args);
	}

}
