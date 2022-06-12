package com.bjpowernode.money;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tzsang
 * @create 2022-06-10 19:23
 */
@SpringBootApplication
@MapperScan("com.bjpowernode.money.mapper")
@EnableTransactionManagement
@EnableDubboConfiguration
public class DataServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(DataServiceApp.class, args);
    }
}
