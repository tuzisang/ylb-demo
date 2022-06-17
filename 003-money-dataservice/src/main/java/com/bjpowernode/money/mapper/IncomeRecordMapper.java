package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.IncomeRecord;
import com.bjpowernode.money.vo.MyRanking;
import com.bjpowernode.money.vo.MyIncomeRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    IncomeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IncomeRecord record);

    int updateByPrimaryKey(IncomeRecord record);


    //根据产品的id 查询记录
    List<MyRanking> selectByLoanId(Integer id);

    //根据用户id查询记录
    List<MyIncomeRecord> selectByUid(Integer uid, Integer pageNum, Integer pageSize);

    //根据状态和当前日期过滤，且根据uid分组
    List<IncomeRecord> selectCurDateIncomeByIncomeStatus(int incomeStatus);


    Double sumCurDateIncomeMoneyByUid(Integer uid, Integer incomeStatus);

    //根据uid修改
    Integer updateByUid(IncomeRecord incomeRecord);
}