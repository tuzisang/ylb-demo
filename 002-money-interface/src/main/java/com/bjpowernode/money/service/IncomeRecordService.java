package com.bjpowernode.money.service;

import com.bjpowernode.money.model.IncomeRecord;
import com.bjpowernode.money.vo.IncomeAndUser;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-11 13:32
 */
public interface IncomeRecordService {

    List<IncomeRecord> queryRanking(Integer count);

    IncomeRecord queryById(Integer id);

    List<IncomeAndUser> queryByLoanId(Integer id);
}
