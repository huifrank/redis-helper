package com.huifrank.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(value = "com.huifrank.*.*")
public class RedisHelperDemoRunner {

    public static void main(String[] args) {

        SpringApplication.run(RedisHelperDemoRunner.class, args);

    }
}
