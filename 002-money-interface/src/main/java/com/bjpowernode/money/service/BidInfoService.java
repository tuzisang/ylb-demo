package com.bjpowernode.money.service;

import com.bjpowernode.money.vo.BidInfoOfLoanInfo;
import com.bjpowernode.money.vo.MyBidInfo;
import com.bjpowernode.money.vo.MyRanking;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-11 9:30
 */
public interface BidInfoService {
    long sumBid();

    List<MyBidInfo> queryDescByUid(Integer uid, Integer pageNum, Integer pageSize);

    //查询排行榜
    List<MyRanking> queryRanking(Integer count);
    //初始化排行榜
    void initRanking();

    //根据loanId查询投资详细
    List<BidInfoOfLoanInfo> queryDescByLoanId(Integer loanId, Integer count);

    //投资成功后，更新排行榜
    void updateRanking(String phone, Double bidMoney);

}
