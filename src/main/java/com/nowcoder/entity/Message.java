package com.nowcoder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @ClassName Message
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/9 13:51
  * @version: 1.0
  */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private int status;
    private Date createTime;
}
