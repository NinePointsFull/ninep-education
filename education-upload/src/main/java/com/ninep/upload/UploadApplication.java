package com.ninep.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ninep.api.**")
@EnableHystrix
public class UploadApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UploadApplication.class, args);
    }
}
