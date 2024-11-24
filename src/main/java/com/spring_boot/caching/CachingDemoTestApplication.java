package com.spring_boot.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CachingDemoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingDemoTestApplication.class, args);
	}

}
