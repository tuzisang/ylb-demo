package com.bjpowernode.money;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author tzsang
 * @create 2022-06-10 19:46
 */
@EnableDubboConfiguration
@SpringBootApplication
public class Webapp {
    public static void main(String[] args) {

        SpringApplication.run(Webapp.class, args);
    }
}
