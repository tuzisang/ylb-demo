package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.common.Constants;
import com.bjpowernode.money.common.HttpClientUtils;
import com.bjpowernode.money.mapper.UserMapper;
import com.bjpowernode.money.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author tzsang
 * @create 2022-06-11 9:12
 */
@Component
@Service(interfaceClass = UserService.class, version = "1.0.0", timeout = 150000)
public class UserServiceImpl implements UserService {
    @Value("${Uid}")
    private String Uid;
    @Value("${Key}")
    private String Key;

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

    @Override
    public boolean sendSmsCode(String phone) {
        //生成随机数
        String code = RandomStringUtils.randomNumeric(4);

        //设置短信网关请求地址
        String url = "http://utf8.api.smschinese.cn/";
        String smsText = "验证码:" + code;

        System.out.println(smsText);
        //设置请求参数
        Map<String,String> params = new HashMap<>();
        params.put("Uid", Uid);
        params.put("Key", Key);
        params.put("smsMob", phone);
        params.put("smsText", smsText);

        //向云服务发出请求
        String result = null;
        try {

            //result = HttpClientUtils.doGet(url, params);
            result = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }

        //发送失败
        if (!"1".equals(result)){
            return false;
        }

        //发送成功
        //把短信缓存到 redis
        redisTemplate.opsForValue().set("sms-code-" + phone, code, 5, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public boolean checkPhone(String phone) {
        int count = userMapper.isExistPhone(phone);
        return count > 0 ? false : true;
    }
}
