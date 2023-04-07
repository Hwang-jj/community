package com.nowcoder.dao;

import org.springframework.stereotype.Repository;

/**
  * @ClassName HelloDaoImpl
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/6 13:34
  * @version: 1.0
  */ 
@Repository
public class HelloDaoImpl implements HelloDao{
    @Override
    public String hello() {
        return "hello springboot";
    }
}
