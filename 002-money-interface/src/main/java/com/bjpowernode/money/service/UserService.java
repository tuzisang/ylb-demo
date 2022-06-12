package com.bjpowernode.money.service;

import com.bjpowernode.money.model.User;

/**
 * @author tzsang
 * @create 2022-06-11 9:14
 */
public interface UserService {

    long countAll();

    User queryByPhoneAndPassword(String phone, String password);
}
