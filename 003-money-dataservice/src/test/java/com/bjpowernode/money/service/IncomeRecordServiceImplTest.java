package com.bjpowernode.money.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tzsang
 * @create 2022-06-14 21:05
 */
@SpringBootTest
class IncomeRecordServiceImplTest {
    @Autowired
    private IncomeRecordServiceImpl incomeRecordService;

    @Test
    void queryDescByUid() {
        incomeRecordService.queryDescByUid(27,1,4).forEach(System.out::println);
    }
}