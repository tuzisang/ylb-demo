package com.bjpowernode.money.service;

import com.bjpowernode.money.vo.MyBidInfo;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-11 9:30
 */
public interface BidInfoService {
    long sumBid();

    List<MyBidInfo> queryDescByUid(Integer uid, Integer pageNum, Integer pageSize);
}
