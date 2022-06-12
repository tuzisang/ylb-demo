package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.mapper.IncomeRecordMapper;
import com.bjpowernode.money.model.IncomeRecord;
import com.bjpowernode.money.vo.IncomeAndUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author tzsang
 * @create 2022-06-11 13:34
 */
@Component
@Service(interfaceClass = IncomeRecordService.class, version = "1.0.0", timeout = 150000)
public class IncomeRecordServiceImpl implements IncomeRecordService {
    @Autowired
    private IncomeRecordMapper incomeRecordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<IncomeRecord> queryRanking(Integer count) {

        List<IncomeRecord> ranking = (List<IncomeRecord>) redisTemplate.opsForValue().get(Constants.INVEST_TOP);
        if (ranking == null){
            synchronized (this){
                ranking = (List<IncomeRecord>) redisTemplate.opsForValue().get(Constants.INVEST_TOP);
                if (ranking == null){
                    //从数据库取
                    ranking = incomeRecordMapper.selectRanking(count);
                    redisTemplate.opsForValue().set(Constants.INVEST_TOP, ranking,
                            30, TimeUnit.MINUTES);

                }else {
                    ranking = (List<IncomeRecord>) redisTemplate.opsForValue().get(Constants.INVEST_TOP);
                }
            }
        }
        return ranking;
    }

    @Override
    public IncomeRecord queryById(Integer id) {
        return incomeRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<IncomeAndUser> queryByLoanId(Integer id) {
        return incomeRecordMapper.selectByLoanId(id);
    }
}
