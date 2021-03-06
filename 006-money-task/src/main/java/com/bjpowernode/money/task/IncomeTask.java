package com.bjpowernode.money.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.common.HttpClientUtils;
import com.bjpowernode.money.model.BidInfo;
import com.bjpowernode.money.model.RechargeRecord;
import com.bjpowernode.money.service.BidInfoService;
import com.bjpowernode.money.service.IncomeRecordService;
import com.bjpowernode.money.service.RechargeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tzsang
 * @create 2022-06-16 16:04
 */
@Component
@EnableScheduling
@Slf4j
public class IncomeTask {
    @Reference(interfaceClass = IncomeRecordService.class, version = "1.0.0", check = false)
    private IncomeRecordService incomeRecordService;


    //每天凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void createIncome() {
        log.info("生成收益计划");
        incomeRecordService.generateIncomeRecords();

        log.info("返还收益计划");
        incomeRecordService.returnIncome();
    }


}
