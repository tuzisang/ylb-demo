package com.bjpowernode.money;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tzsang
 * @create 2022-06-10 19:46
 */
@SpringBootApplication
@EnableDubboConfiguration
public class Webapp {
    public static void main(String[] args) {

        SpringApplication.run(Webapp.class, args);
    }
}
