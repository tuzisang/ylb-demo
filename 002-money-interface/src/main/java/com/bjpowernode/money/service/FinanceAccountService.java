package com.bjpowernode.money.service;

import com.bjpowernode.money.model.FinanceAccount;

/**
 * @author tzsang
 * @create 2022-06-12 16:45
 */
public interface FinanceAccountService {

    FinanceAccount queryByUserId(Integer id);
}
