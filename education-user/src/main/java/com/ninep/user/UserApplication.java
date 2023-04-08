package com.ninep.user;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "com.ninep.user.mapper")
@ComponentScan(basePackages = {"com.ninep.user","com.ninep.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.ninep.api.**")
@EnableTransactionManagement
@EnableHystrix
public class UserApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UserApplication.class);
    }
}
