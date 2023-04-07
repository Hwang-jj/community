package com.nowcoder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @ClassName DiscussPost
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/6 16:53
  * @version: 1.0
  */ 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussPost {

    private int id;
    private int userId;
    private String title;
    private String content;
    private int type;
    private int status;
    private Date createTime;
    private int commentCount;
    private double score;
}
