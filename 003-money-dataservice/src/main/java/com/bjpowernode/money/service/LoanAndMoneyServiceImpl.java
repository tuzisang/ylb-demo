package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.IncomeRecordMapper;
import com.bjpowernode.money.vo.loanAndMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-14 13:38
 */
@Component
@Service(interfaceClass = LoanAndMoneyService.class, version = "1.0.0", timeout = 150000)
public class LoanAndMoneyServiceImpl implements LoanAndMoneyService {
    @Autowired
    private IncomeRecordMapper incomeRecordMapper;

    @Override
    public List<loanAndMoney> queryById(Integer uid, Integer pageNum, Integer pageSize) {
        return incomeRecordMapper.selectByUserId(uid, pageNum, pageSize);
    }
}
