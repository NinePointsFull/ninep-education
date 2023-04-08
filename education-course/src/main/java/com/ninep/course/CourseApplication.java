package com.ninep.course;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@MapperScan(basePackages = "com.ninep.course.mapper")
@ComponentScan(basePackages = {"com.ninep.course","com.ninep.common"})
@EnableFeignClients(basePackages = "com.ninep.api.**")
@EnableCaching
@EnableTransactionManagement
public class CourseApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CourseApplication.class);
    }



}
