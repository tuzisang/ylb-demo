package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.RechargeRecordMapper;
import com.bjpowernode.money.model.RechargeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tzsang
 * @create 2022-06-14 16:32
 */
@Component
@Service(interfaceClass = RechargeRecordService.class, version = "1.0.0", timeout = 150000)
public class RechargeRecordServiceImpl implements RechargeRecordService {
    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public List<RechargeRecord> queryById(Integer id, Integer pageNum, Integer pageSize) {
        return rechargeRecordMapper.selectByUid(id, pageNum, pageSize);
    }
}
