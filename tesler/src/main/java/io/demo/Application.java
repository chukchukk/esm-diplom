package io.demo;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@EnableProcessApplication
@ConfigurationPropertiesScan("io.demo.conf")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}
