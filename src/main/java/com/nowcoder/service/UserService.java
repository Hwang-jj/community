package com.nowcoder.service;

import com.nowcoder.dao.UserMapper;
import com.nowcoder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
  * @ClassName UserService
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/6 17:24
  * @version: 1.0
  */ 
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }
}
