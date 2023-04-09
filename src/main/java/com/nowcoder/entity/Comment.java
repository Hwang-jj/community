package com.nowcoder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @ClassName Comment
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/9 9:45
  * @version: 1.0
  */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private int id;
    private int userId;
    private int entityType;
    private int entityId;
    private int targetId;
    private String content;
    private int status;
    private Date createTime;
}
