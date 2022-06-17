package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.FinanceAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);
    //查余额
    FinanceAccount selectByUserId(Integer id);

    void updateAvailableMoneyByUid(Double incomeMoney, Integer uid);
}