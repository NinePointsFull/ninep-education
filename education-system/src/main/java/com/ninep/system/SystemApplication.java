package com.ninep.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.ninep.system.mapper")
@EnableCaching
@ComponentScan(basePackages = {"com.ninep.system","com.ninep.common"})
@EnableTransactionManagement
public class SystemApplication{
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SystemApplication.class, args);
    }
}
