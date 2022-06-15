package com.bjpowernode.money.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author tzsang
 * @create 2022-06-11 19:28
 */
@SpringBootTest
class IncomeRecordMapperTest {
    @Autowired
    private IncomeRecordMapper incomeRecordMapper;

    @Test
    void selectByPrimaryKey() {
        System.out.println(incomeRecordMapper.selectByPrimaryKey(1));
    }

    @Test
    void selectByUserId() {
        incomeRecordMapper.selectByUid(27,1,4).forEach(System.out::println);
    }
}