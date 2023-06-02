package com.example.SuperMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("com.example")
@Configuration
public class Stater {
    public static void main(String[] args) {
        SpringApplication.run(Stater.class,args);
    }
}
