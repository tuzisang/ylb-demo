package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.BidInfo;
import com.bjpowernode.money.vo.BidInfoOfLoanInfo;
import com.bjpowernode.money.vo.MyBidInfo;
import com.bjpowernode.money.vo.MyRanking;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    //根据产品id查询投资详细
    List<BidInfoOfLoanInfo> selectDescByProductId(Integer loanId, Integer count);

    //平台总投资金额
    long sumBid();

    //根据uid查询投资详细
    List<MyBidInfo> selectDescByUid(Integer uid, Integer pageNum, Integer pageSize);

    //排行榜
    List<MyRanking> selectRanking(Integer count);


    List<BidInfo> selectByLoanId(Integer loanId);
}