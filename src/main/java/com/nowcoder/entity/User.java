package com.nowcoder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @ClassName User
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/6 15:51
  * @version: 1.0
  */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;

    public User(String username, String password, String salt, String email, String headerUrl, Date createTime) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.headerUrl = headerUrl;
        this.createTime = createTime;
    }
}
