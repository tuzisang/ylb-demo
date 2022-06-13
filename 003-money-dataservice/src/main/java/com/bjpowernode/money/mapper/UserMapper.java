package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //注册总人数
    long countAll();

    //根据电话和密码查询用户是否存在
    User selectByPhoneAndPassword(String phone, String password);

    int isExistPhone(String phone);
}