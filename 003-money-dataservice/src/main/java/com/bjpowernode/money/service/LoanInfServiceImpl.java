package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.mapper.LoanInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author tzsang
 * @create 2022-06-10 20:35
 */
@Component
@Service(interfaceClass = LoanInfoService.class, version = "1.0.0", timeout = 150000)
public class LoanInfServiceImpl implements LoanInfoService {
    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public double queryHistoryAverageRate() {
        //从缓存从获取历史年化收益率
        Double historyRate = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
        //双重检查避免缓存击穿
        if (historyRate == null) {
            synchronized (this) {
                historyRate = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
                if (historyRate == null) {
                    //从数据库取
                    historyRate = loanInfoMapper.selectHistoryAverageRate();
                    //缓存到redis
                    redisTemplate.opsForValue().set(Constants.HISTORY_AVERAGE_RATE, historyRate, 30, TimeUnit.MINUTES);
                } else {
                    //从缓存取
                    historyRate = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
                }
            }
        } else {
            //从缓存取
            historyRate = (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
        }
        return historyRate;
    }

    @Override
    public List<LoanInfo> queryLoanInfo(Integer productType, Integer pageNum, Integer pageSize) {
        List<LoanInfo> loanList = (List<LoanInfo>) redisTemplate.opsForValue()
                .get("loanList:" + productType + "-pageNum:" + pageNum + "-pageSize" + pageSize);
        if (loanList == null) {
            synchronized (this) {
                loanList = (List<LoanInfo>) redisTemplate.opsForValue()
                        .get("loanList:" + productType + "-pageNum:" + pageNum + "-pageSize" + pageSize);
                if (loanList == null) {
                    loanList = loanInfoMapper.selectLoanInfo(productType, pageNum, pageSize);
                    redisTemplate.opsForValue()
                            .set("loanList:" + productType + "-pageNum:" + pageNum + "-pageSize" + pageSize, loanList, 30, TimeUnit.MINUTES);
                } else {
                    loanList = (List<LoanInfo>) redisTemplate.opsForValue()
                            .get("loanList:" + productType + "-pageNum:" + pageNum + "-pageSize" + pageSize);
                }
            }
        }
        return loanList;
    }

    @Override
    public long queryCount(Integer productType) {
        Integer count = (Integer) redisTemplate.opsForValue().get("count:" + productType);
        if (count == null){
            synchronized (this){
                count = (Integer) redisTemplate.opsForValue().get("count:" + productType);
                if (count == null){
                    count = Math.toIntExact(loanInfoMapper.selectCount(productType));
                    redisTemplate.opsForValue().set("count:" + productType, count, 30, TimeUnit.MINUTES);
                }else {
                    count = (Integer) redisTemplate.opsForValue().get("count:" + productType);
                }
            }
        }
        return count;
    }

    @Override
    public LoanInfo queryById(Integer id) {
        return loanInfoMapper.selectByPrimaryKey(id);
    }
}
