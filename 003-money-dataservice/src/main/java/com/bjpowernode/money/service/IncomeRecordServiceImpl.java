package com.bjpowernode.money.service;

import java.util.Date;

import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.mapper.FinanceAccountMapper;
import com.bjpowernode.money.model.*;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.BidInfoMapper;
import com.bjpowernode.money.mapper.IncomeRecordMapper;
import com.bjpowernode.money.mapper.LoanInfoMapper;
import com.bjpowernode.money.vo.MyRanking;
import com.bjpowernode.money.vo.MyIncomeRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-11 13:34
 */
@Slf4j
@Component
@Service(interfaceClass = IncomeRecordService.class, version = "1.0.0", timeout = 150000)
public class IncomeRecordServiceImpl implements IncomeRecordService {
    @Autowired
    private IncomeRecordMapper incomeRecordMapper;
    @Autowired
    private BidInfoMapper bidInfoMapper;
    @Autowired
    private LoanInfoMapper loanInfoMapper;
    @Autowired
    private FinanceAccountMapper financeAccountMapper;

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

    @Override
    public void generateIncomeRecords() {
//       1.查询产品状态为1已满标的产品 -> 返回List<已满标产品>
        List<LoanInfo> loanInfoList = loanInfoMapper.selectByProductStatus(1);
//       2.循环遍历,获取到每一个产品
        loanInfoList.forEach(loanInfo -> {
            //3.获取当前产品的所有投资记录 -> 返回List<投资记录
            List<BidInfo> bidInfoList = bidInfoMapper.selectByLoanId(loanInfo.getId());
            //4.循环遍历获取到每一个投资记录
            bidInfoList.forEach(bidInfo -> {
                //5.将当前的投资记录生成对应的收益计划IncomeRecord
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setUid(bidInfo.getUid());
                incomeRecord.setLoanId(loanInfo.getId());
                incomeRecord.setBidId(bidInfo.getId());

                //a）收益时间(Date) = 产品满标时间(Date) + 产品周期(int 天| 月)
                //b）收益金额 = 投资金额 * 日利率 * 天数;
                //c）如果是新手宝--投资金额 * (年利率 / 100 / 365) * 投资周期;
                //d）如果是优选或散标--投资金额 * (年利率 / 100 / 365) * 投资周期*30

                Date incomeDate = null;
                Double incomeMoney = null;
                if (loanInfo.getProductType() == Constants.PRODUCT_TYPE_X) { //新手宝按天算
                    incomeDate = DateUtils.addDays(loanInfo.getProductFullTime(), loanInfo.getCycle());
                    //如果是新手宝--投资金额 * (年利率 / 100 / 365) * 投资周期;
                    incomeMoney = bidInfo.getBidMoney() * (loanInfo.getRate() / 100 / 365) * loanInfo.getCycle();
                } else { //其他产品按月算
                    incomeDate = DateUtils.addDays(loanInfo.getProductFullTime(), loanInfo.getCycle() * 30);
                    //如果是优选或散标--投资金额 * (年利率 / 100 / 365) * 投资周期*30
                    incomeMoney = bidInfo.getBidMoney() * (loanInfo.getRate() / 100 / 365) * loanInfo.getCycle() * 30;
                }
                incomeRecord.setIncomeDate(incomeDate);
                incomeMoney = (double) (Math.round(incomeMoney * 100)) / 100;
                incomeRecord.setIncomeMoney(incomeMoney);
                incomeRecord.setBidMoney(bidInfo.getBidMoney());
                incomeRecord.setIncomeStatus(0);
                //添加收益记录
                incomeRecordMapper.insert(incomeRecord);
            });
            //6.更新当前产品的状态为2满标且生成收益计划
            loanInfo.setProductStatus(2);
            loanInfoMapper.updateByPrimaryKey(loanInfo);
        });

    }

    //返还收益
    @Override
    public void returnIncome() {
        //获取收益记录状态为0且收益时间等于当前时间,且按uid分组的收益合计值
        List<IncomeRecord> incomeRecordList = incomeRecordMapper.selectCurDateIncomeByIncomeStatus(0);
        log.info(incomeRecordList.size() + "");
        //遍历取出，每一个的收益合计值 加到账户余额
        incomeRecordList.forEach(incomeSumByUid -> {
            //把收益加到对应uid的账户余额中
            financeAccountMapper.updateAvailableMoneyByUid(incomeSumByUid.getIncomeMoney(), incomeSumByUid.getUid());
            //修改收益记录的状态
            incomeSumByUid.setIncomeStatus(1);
            int row = incomeRecordMapper.updateByUid(incomeSumByUid);
            log.info("修了状态"+row);
        });


    }
}
