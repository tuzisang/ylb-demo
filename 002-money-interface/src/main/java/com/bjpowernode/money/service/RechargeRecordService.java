package com.bjpowernode.money.service;

import com.bjpowernode.money.model.RechargeRecord;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-14 16:31
 */
public interface RechargeRecordService {

    List<RechargeRecord> queryById(Integer id, Integer pageNum, Integer pageSize);

    //投资
    boolean addBidInfo(Integer uid, Integer loanId, Double money);

    //添加充值
    int addRecharge(RechargeRecord record);

    //按订单号查询充值记录
    RechargeRecord queryByNo(String no);

    void edit(RechargeRecord record);

    List<RechargeRecord> queryByStatus(String status);
}
