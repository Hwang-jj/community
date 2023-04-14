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
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId, int offset, int limit, int orderMode);

    //查询帖子的行数
    int selectDiscussPostRows(int userId);

    //增加帖子
    int insertDiscussPost(DiscussPost discussPost);

    //查询帖子详情
    DiscussPost selectDiscussPostById(int id);

    //更新帖子评论数量
    int updateCommentCount(int id, int commentCount);

    // 修改帖子类型
    int updateType(int id, int type);

    // 修改帖子状态
    int updateStatus(int id, int status);

    // 更新帖子分数
    int updateScore(int id, double score);

}
