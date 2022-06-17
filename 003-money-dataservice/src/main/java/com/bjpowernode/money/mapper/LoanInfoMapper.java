package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.LoanInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanInfo record);

    int insertSelective(LoanInfo record);

    LoanInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanInfo record);

    int updateByPrimaryKey(LoanInfo record);

    //历史年化收益率
    double selectHistoryAverageRate();

    //显示产品信息
    List<LoanInfo> selectLoanInfo(
            Integer productType, Integer pageNum, Integer pageSize);

    //按类型查数量
    long selectCount(Integer productType);

    //按状态查询产品
    List<LoanInfo> selectByProductStatus(Integer productStatus);
}