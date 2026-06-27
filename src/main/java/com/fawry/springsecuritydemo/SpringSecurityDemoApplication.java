package com.fawry.springsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringSecurityDemoApplication {

  void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

}
