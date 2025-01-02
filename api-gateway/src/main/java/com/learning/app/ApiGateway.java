package com.learning.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiGateway {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}