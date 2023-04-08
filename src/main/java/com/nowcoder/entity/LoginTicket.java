package com.nowcoder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @ClassName LoginTicket
  * @description: 登录凭证类
  * @author Hwangjj
  * @date 2023/4/8 9:51
  * @version: 1.0
  */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTicket {

    private int id;
    private int userId;
    private String ticket;
    private int status;
    private Date expired;

}
