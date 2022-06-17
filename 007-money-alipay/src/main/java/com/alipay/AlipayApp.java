package com.alipay;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tzsang
 * @create 2022-06-17 11:28
 */
@SpringBootApplication
@EnableDubboConfiguration
public class AlipayApp {
    public static void main(String[] args) {
        SpringApplication.run(AlipayApp.class, args);
    }
}
