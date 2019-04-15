package cn.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.demo","cn.iflyapi.validator"})
public class FastValidatorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastValidatorDemoApplication.class, args);
    }
}
