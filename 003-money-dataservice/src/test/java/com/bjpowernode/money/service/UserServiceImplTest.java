package com.bjpowernode.money.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tzsang
 * @create 2022-06-13 18:16
 */
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    void sendSmsCode() {
        boolean ok = userService.sendSmsCode("15807741053");

        System.out.println(ok);
    }
}