package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.IncomeRecordMapper;
import com.bjpowernode.money.model.IncomeRecord;
import com.bjpowernode.money.vo.MyRanking;
import com.bjpowernode.money.vo.MyIncomeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-11 13:34
 */
@Component
@Service(interfaceClass = IncomeRecordService.class, version = "1.0.0", timeout = 150000)
public class IncomeRecordServiceImpl implements IncomeRecordService {
    @Autowired
    private IncomeRecordMapper incomeRecordMapper;


    @Override
    public IncomeRecord queryById(Integer id) {
        return incomeRecordMapper.selectByPrimaryKey(id);
    }



    @Override
    public List<MyRanking> queryByLoanId(Integer id) {
        return incomeRecordMapper.selectByLoanId(id);
    }

    @Override
    public List<MyIncomeRecord> queryDescByUid(Integer uid, Integer pageNum, Integer pageSize) {
        return incomeRecordMapper.selectByUid(uid, pageNum, pageSize);
    }
}
