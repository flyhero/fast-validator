package cn.iflyapi.validator.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//@ComponentScan("cn.iflyapi")
public class FastValidatorDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastValidatorDemoApplication.class, args);
	}
}
