package com.bjpowernode.money.service;

import com.bjpowernode.money.model.LoanInfo;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-10 20:34
 */
public interface LoanInfoService {

    double queryHistoryAverageRate();

    List<LoanInfo> queryLoanInfo(Integer productType, Integer pageNum, Integer pageSize);

    long queryCount(Integer productType);

    LoanInfo queryById(Integer id);
}
