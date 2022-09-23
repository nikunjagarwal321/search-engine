package com.searchengine.indexservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IndexserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndexserviceApplication.class, args);
	}

}
