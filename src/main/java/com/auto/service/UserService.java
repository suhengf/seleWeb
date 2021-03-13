package com.auto.service;

import com.auto.entity.User;
import com.auto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:liulingfeng
 * @Date: 2021/3/12
 * @Time: 15:23
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User Sel(int id){
        return userMapper.Sel(id);
    }

    public void insert(){
        userMapper.insert(User.builder().userName("llf").id(1).realName("liulingfeng").passWord("password").build() );
    }


}
