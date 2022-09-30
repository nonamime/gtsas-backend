package com.gtassignment.salary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
//
//	@Bean
//	public MethodValidationPostProcessor methodValidationPostProcessor() {
//		MethodValidationPostProcessor mvpp = new MethodValidationPostProcessor();
//		mvpp.setProxyTargetClass(true);
//		return mvpp;
//	}
}
