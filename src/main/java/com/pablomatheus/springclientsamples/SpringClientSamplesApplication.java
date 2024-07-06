package com.pablomatheus.springclientsamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringClientSamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringClientSamplesApplication.class, args);
	}

}
