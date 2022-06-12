package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.mapper.BidInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author tzsang
 * @create 2022-06-11 9:31
 */
@Component
@Service(interfaceClass = BidInfoService.class, version = "1.0.0", timeout = 150000)
public class BidInfoServiceImpl implements BidInfoService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Override
    public long sumBid() {
        Integer sumBid = (Integer) redisTemplate.opsForValue().get(Constants.ALL_BID_MONEY);
        if (sumBid == null){
            synchronized (this){
                sumBid = (Integer) redisTemplate.opsForValue().get(Constants.ALL_BID_MONEY);
                if (sumBid == null){
                    //从数据库取
                    sumBid = Math.toIntExact(bidInfoMapper.sumBid());
                    //缓存到redis
                    redisTemplate.opsForValue().set(Constants.ALL_BID_MONEY, sumBid,
                            30, TimeUnit.MINUTES);
                }else{
                    //从缓存取
                    sumBid = (Integer) redisTemplate.opsForValue().get(Constants.ALL_BID_MONEY);
                }
            }
        }else {
            //从缓存取
            sumBid = (Integer) redisTemplate.opsForValue().get(Constants.ALL_BID_MONEY);
        }
        return sumBid;
    }
}
