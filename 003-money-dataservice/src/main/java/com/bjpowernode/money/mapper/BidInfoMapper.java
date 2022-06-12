package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.BidInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface BidInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    //平台总投资金额
    long sumBid();
}