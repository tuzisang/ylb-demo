package com.bjpowernode.money.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tzsang
 * @create 2022-06-10 20:36
 */
@SpringBootTest
class LoanInfServiceImplTest {
    @Autowired
    private LoanInfServiceImpl loanInfService;
    @Test
    void queryHistoryAverageRate() {
        System.out.println(loanInfService.queryHistoryAverageRate());

    }
}