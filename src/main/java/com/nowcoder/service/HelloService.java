package com.nowcoder.service;

import com.nowcoder.dao.HelloDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
  * @ClassName HelloService
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/6 13:32
  * @version: 1.0
  */ 
@Service
public class HelloService {

    @Autowired
    private HelloDao helloDao;


    public String find(){
        return helloDao.hello();
    }
}
