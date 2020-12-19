package com.client.rest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = { "com.client.core.feign" })
@EnableDiscoveryClient
@SpringBootApplication
public class SpringBootApplication8080 {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication8080.class,args);
    }
}
