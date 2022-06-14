package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.IncomeRecord;
import com.bjpowernode.money.vo.IncomeAndUser;
import com.bjpowernode.money.vo.loanAndMoney;
import org.apache.ibatis.annotations.Mapper;
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

    //查排行榜
    List<IncomeRecord> selectRanking(Integer count);
    //根据产品的id 查询记录
    List<IncomeAndUser> selectByLoanId(Integer id);

    //根据用户id查询记录
    List<loanAndMoney> selectByUserId(Integer uid, Integer pageNum, Integer pageSize);
}