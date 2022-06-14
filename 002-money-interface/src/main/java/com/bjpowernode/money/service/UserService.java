package com.bjpowernode.money.service;

import com.bjpowernode.money.model.User;

/**
 * @author tzsang
 * @create 2022-06-11 9:14
 */
public interface UserService {

    long countAll();

    User queryByPhoneAndPassword(String phone, String password);

    //验证码是否正确
    boolean sendSmsCode(String phone);

    //验证手机是否重复
    boolean checkPhone(String phone);

    //检查验证码是否正确
    boolean checkCode(String code, String phone);

    //注册用户
    User register(String phone, String password);
}
