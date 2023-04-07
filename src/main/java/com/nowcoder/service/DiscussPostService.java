package com.nowcoder.service;

import com.nowcoder.dao.DiscussPostMapper;
import com.nowcoder.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * @ClassName DiscussPostService
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/6 17:20
  * @version: 1.0
  */ 
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }

}
