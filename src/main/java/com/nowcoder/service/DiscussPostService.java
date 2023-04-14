package com.nowcoder.service;

import com.nowcoder.dao.DiscussPostMapper;
import com.nowcoder.entity.DiscussPost;
import com.nowcoder.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit, int orderMode){
        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
    }

    public int findDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    // 添加帖子的业务
    public int addDiscussPost(DiscussPost post){
        if (post == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        //转义HTML标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        //过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    // 根据帖子id查询帖子
    public DiscussPost findDiscussPostById(int id){
        return discussPostMapper.selectDiscussPostById(id);
    }

    // 根据帖子id更新评论数量
    public int updateCommentCount(int id, int commentCount){
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    // 修改帖子类型：1为置顶，0为普通
    public int updateType(int id, int type){
        return discussPostMapper.updateType(id, type);
    }

    // 修改帖子状态：0为普通，1为加精，2为删除
    public int updateStatus(int id, int status){
        return discussPostMapper.updateStatus(id, status);
    }

    // 修改帖子分数
    public int updateScore(int id, double score){
        return discussPostMapper.updateScore(id, score);
    }
}
