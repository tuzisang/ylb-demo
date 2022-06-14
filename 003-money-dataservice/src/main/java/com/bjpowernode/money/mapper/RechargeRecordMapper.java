package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    //根据uid查询充值记录
    List<RechargeRecord> selectByUid(Integer uid, Integer pageNum, Integer pageSize);
}