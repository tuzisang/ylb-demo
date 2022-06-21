package com.bjpowernode.money.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.bjpowernode.money.common.HttpClientUtils;
import com.bjpowernode.money.model.RechargeRecord;
import com.bjpowernode.money.service.FinanceAccountService;
import com.bjpowernode.money.service.RechargeRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tzsang
 * @create 2022-06-18 13:18
 */

@Component
@EnableScheduling
@Slf4j
public class QueryTradeTask {
    @Reference(interfaceClass = RechargeRecordService.class, version = "1.0.0", check = false)
    private RechargeRecordService rechargeRecordService;
    @Reference(interfaceClass = FinanceAccountService.class, version = "1.0.0", check = false)
    private FinanceAccountService financeAccountService;

    //每10分钟执行一次，支付宝订单查询接口
    @Scheduled(cron = "0 0/10 * * * ? ")
//    @Scheduled(fixedDelay = 1000 * 30)
    public void queryAliPayTrade(){
        String url = "http://localhost:8083/tradeQuery";
        List<RechargeRecord> rechargeRecordList = rechargeRecordService.queryByStatusAndDesc("0", "支付宝");
        if (rechargeRecordList!=null){
            rechargeRecordList.forEach(x->{
                Map<String, String> param = new HashMap<>();
                param.put("out_trade_no", x.getRechargeNo());
                String body = null;
                try {
                    body = HttpClientUtils.doGet(url, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //解析
                String code = JSON.parseObject(body).getJSONObject("alipay_trade_query_response").getString("code");
                log.info("code="+code);
                RechargeRecord record = rechargeRecordService.queryByNo(x.getRechargeNo());
                if ("10000".equals(code)){
                    //交易记录存在，更新交易状态并增加余额
                    record.setRechargeStatus("1");
                    financeAccountService.addAvailableMoneyByUid(x.getRechargeMoney(), x.getUid());
                }else {
                    //交易失败
                    record.setRechargeStatus("2");
                }

                rechargeRecordService.edit(record);
                log.info("支付宝充值状态更新完毕");
            });
        }else {
            log.info("支付宝没有充值状态为充值的中的数据");
        }

    }

    //每10分钟执行一次，快钱的查询接口不能用，要企业证书才行
    @Scheduled(cron = "0 0/10 * * * ? ")
//    @Scheduled(fixedDelay = 1000 * 30)
    public void queryBillPayTrade(){
        String url = "http://localhost:8084/tradeQuery";
        List<RechargeRecord> rechargeRecordList = rechargeRecordService.queryByStatusAndDesc("0", "快钱");
        if (rechargeRecordList!=null){
            rechargeRecordList.forEach(x->{
                Map<String, String> param = new HashMap<>();
                param.put("out_trade_no", x.getRechargeNo());
                String body = null;
                try {
                    body = HttpClientUtils.doGet(url, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //快钱的查询接口不能用，要企业证书才行
                String code = JSON.parseObject(body).getString("error");







                log.info("快钱状态更新完毕");
            });
        }else {
            log.info("快钱没有充值状态为充值的中的数据");
        }

    }
}
