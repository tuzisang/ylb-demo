package com.bjpowernode.money.service;

import com.bjpowernode.money.model.IncomeRecord;
import com.bjpowernode.money.vo.MyRanking;
import com.bjpowernode.money.vo.MyIncomeRecord;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-11 13:32
 */
public interface IncomeRecordService {

    IncomeRecord queryById(Integer id);

    List<MyRanking> queryByLoanId(Integer id);

    //根据uid查询投资详细
    List<MyIncomeRecord> queryDescByUid(Integer uid, Integer pageNum, Integer pageSize);

    //生成收益
    void generateIncomeRecords();

    //返还收益
    void returnIncome();
}
