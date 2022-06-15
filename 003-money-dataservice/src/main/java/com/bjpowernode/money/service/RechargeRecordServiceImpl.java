package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.*;
import com.bjpowernode.money.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-14 16:32
 */
@Transactional
@Component
@Service(interfaceClass = RechargeRecordService.class, version = "1.0.0", timeout = 150000)
public class RechargeRecordServiceImpl implements RechargeRecordService {
    @Autowired
    private IncomeRecordMapper incomeRecordMapper;
    @Autowired
    private FinanceAccountMapper financeAccountMapper;
    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;
    @Autowired
    private LoanInfoMapper loanInfoMapper;
    @Autowired
    private  BidInfoMapper bidInfoMapper;

    @Override
    public List<RechargeRecord> queryById(Integer id, Integer pageNum, Integer pageSize) {
        return rechargeRecordMapper.selectByUid(id, pageNum, pageSize);
    }

    @Override
    public boolean addRecharge(Integer uid, Integer loanId, Double money) {
        //减少账户的余额
        FinanceAccount account = financeAccountMapper.selectByUserId(uid);
        System.out.println("account="+account);
        Double availableMoney = account.getAvailableMoney();
        if (availableMoney < money){
            return false;
        }
        account.setAvailableMoney( availableMoney - money);
        int fi = financeAccountMapper.updateByPrimaryKey(account);
        System.out.println("账户更新了"+ fi);
        //添加投资记录
        IncomeRecord incomeRecord = new IncomeRecord();
        incomeRecord.setUid(uid);
        incomeRecord.setLoanId(loanId);
        incomeRecord.setBidId(account.getId() + 500); //随便搞的
        incomeRecord.setIncomeDate(new Date());
        LoanInfo loanInfo = loanInfoMapper.selectByPrimaryKey(loanId);
        incomeRecord.setIncomeMoney(money * (loanInfo.getRate() /100/365 ) * loanInfo.getCycle());
        incomeRecord.setBidMoney(money);
        incomeRecord.setIncomeStatus(0);

        incomeRecordMapper.insert(incomeRecord);
        //添加投标记录
        BidInfo bidInfo = new BidInfo();
        bidInfo.setLoanId(loanId);
        bidInfo.setUid(uid);
        bidInfo.setBidMoney(money);
        bidInfo.setBidTime(new Date());
        bidInfo.setBidStatus(1);

        bidInfoMapper.insert(bidInfo);

        return true;
    }
}
