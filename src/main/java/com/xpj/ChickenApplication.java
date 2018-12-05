package com.xpj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan
@ComponentScan(value = "com.xpj")
@MapperScan(basePackages = {"com.**.mapper"})
public class ChickenApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChickenApplication.class, args);
    }
}
