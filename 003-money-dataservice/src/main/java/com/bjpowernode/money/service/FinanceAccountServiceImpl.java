package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.FinanceAccountMapper;
import com.bjpowernode.money.model.FinanceAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tzsang
 * @create 2022-06-12 16:46
 */
@Component
@Service(interfaceClass = FinanceAccountService.class, version = "1.0.0", timeout = 150000)
public class FinanceAccountServiceImpl implements FinanceAccountService {
    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public FinanceAccount queryByUserId(Integer id) {
        return financeAccountMapper.selectByUserId(id);
    }
}
