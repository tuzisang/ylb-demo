package com.bjpowernode.money.service;

import com.bjpowernode.money.model.RechargeRecord;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-14 16:31
 */
public interface RechargeRecordService {

    List<RechargeRecord> queryById(Integer id, Integer pageNum, Integer pageSize);
}
