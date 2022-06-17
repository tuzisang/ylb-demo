package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.mapper.BidInfoMapper;
import com.bjpowernode.money.model.BidInfo;
import com.bjpowernode.money.vo.BidInfoOfLoanInfo;
import com.bjpowernode.money.vo.MyBidInfo;
import com.bjpowernode.money.vo.MyRanking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @Override
    public List<MyBidInfo> queryDescByUid(Integer uid, Integer pageNum, Integer pageSize) {
        return bidInfoMapper.selectDescByUid(uid, pageNum, pageSize);
    }

    @Override
    public List<MyRanking> queryRanking(Integer count) {
        ArrayList<MyRanking> myRankings = new ArrayList<>();
        Set<DefaultTypedTuple> set = redisTemplate.opsForZSet().reverseRangeWithScores(Constants.INVEST_TOP, 0, count-1);
        for(DefaultTypedTuple d:set){
            MyRanking ranking = new MyRanking(d.getValue().toString(), d.getScore());
            myRankings.add(ranking);
        }

        return myRankings;
    }

    @Override
    public void initRanking() {
        List<MyRanking> myRankings = bidInfoMapper.selectRanking(1000000);

        for(MyRanking ranking: myRankings){
            redisTemplate.opsForZSet().add(Constants.INVEST_TOP, ranking.getPhone(), ranking.getBidMoney());
        }
    }

    @Override
    public List<BidInfoOfLoanInfo> queryDescByLoanId(Integer loanId, Integer count) {
        return bidInfoMapper.selectDescByProductId(loanId, count);
    }

    @Override
    public void updateRanking(String phone, Double bidMoney) {
        redisTemplate.opsForZSet().incrementScore(Constants.INVEST_TOP, phone, bidMoney);
    }

}
