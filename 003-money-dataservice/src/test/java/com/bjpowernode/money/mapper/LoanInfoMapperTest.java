package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.LoanInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tzsang
 * @create 2022-06-10 20:27
 */
@SpringBootTest
class LoanInfoMapperTest {

    @Autowired
    private LoanInfoMapper loanInfoMapper;
    @Test
    void selectByPrimaryKey() {
        LoanInfo loanInfo = loanInfoMapper.selectByPrimaryKey(1);
        System.out.println(loanInfo);
    }

    @Test
    void selectByProductStatus() {
        loanInfoMapper.selectByProductStatus(1).forEach(System.out::println);
    }
}