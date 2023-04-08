package com.nowcoder.util;

import com.nowcoder.entity.User;
import org.springframework.stereotype.Component;

/**
  * @ClassName HostHolder
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/8 11:59
  * @version: 1.0
  */ 
//持有用户信息，用于替代session对象
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
