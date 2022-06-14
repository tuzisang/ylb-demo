package com.bjpowernode.money.service;

import com.bjpowernode.money.vo.loanAndMoney;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-14 13:37
 */
public interface LoanAndMoneyService {
    List<loanAndMoney> queryById(Integer uid, Integer pageNum, Integer pageSize);
}
