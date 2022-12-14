package com.powershop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.powershop.mapper")
public class PowerShopItemApp {
    public static void main(String[] args) {
        SpringApplication.run(PowerShopItemApp.class, args);
    }
}