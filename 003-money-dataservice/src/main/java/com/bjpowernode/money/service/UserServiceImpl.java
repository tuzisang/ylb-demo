package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.mapper.UserMapper;
import com.bjpowernode.money.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author tzsang
 * @create 2022-06-11 9:12
 */
@Component
@Service(interfaceClass = UserService.class, version = "1.0.0", timeout = 150000)
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public long countAll() {
        Integer userCount =  (Integer) redisTemplate.opsForValue().get(Constants.ALL_USER_COUNT);
        if (userCount == null){
            synchronized (this){
                userCount = (Integer) redisTemplate.opsForValue().get(Constants.ALL_USER_COUNT);
                if (userCount == null){
                    //从数据库取
                    userCount = Math.toIntExact(userMapper.countAll());
                    //缓存到redis
                    redisTemplate.opsForValue().set(Constants.ALL_USER_COUNT, userCount);
                    redisTemplate.expire(Constants.ALL_USER_COUNT, 30, TimeUnit.MINUTES);
                }else {
                    //从缓存取
                    userCount = (Integer) redisTemplate.opsForValue().get(Constants.ALL_USER_COUNT);
                }
            }
        }else {
            //从缓存取
            userCount = (Integer) redisTemplate.opsForValue().get(Constants.ALL_USER_COUNT);
        }
        return userCount;
    }

    @Override
    public User queryByPhoneAndPassword(String phone, String password) {
        return userMapper.selectByPhoneAndPassword(phone, password);
    }
}
