package com.nowcoder.dao;

import com.nowcoder.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DiscussPostMapper {

    //显示评论
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId, int offset, int limit);

    //查询帖子的行数
    int selectDiscussPostRows(int userId);

    //增加帖子
    int insertDiscussPost(DiscussPost discussPost);

    //查询帖子详情
    DiscussPost selectDiscussPostById(int id);

    //更新帖子评论数量
    int updateCommentCount(int id, int commentCount);
}
