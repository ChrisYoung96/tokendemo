package com.chrisyoung.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("dao")
@ComponentScan(basePackages = "com.chrisyoung.demo*")

public class TokenDemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TokenDemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class applicationClass =TokenDemoApplication.class;
}
