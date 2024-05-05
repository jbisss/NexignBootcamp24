package ru.jbisss.cdrservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CdrServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdrServiceApplication.class, args);
    }
}
