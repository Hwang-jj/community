package com.nowcoder.dao;

import com.nowcoder.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    // 查询评论
    List<Comment> selectCommentByEntity(int entityType, int entityId, int offset, int limit);

    // 查询评论数量
    int selectCountByEntity(int entityType, int entityId);

    //增加评论
    int insertComment(Comment comment);

    // 根据id查评论
    Comment selectCommentById(int id);

    // 查询某用户的评论
    List<Comment> selectCommentByUserId(int userId, int entityType, int offset, int limit);

    // 查询某用户的评论数
    int selectCommentCountByUserId(int userId, int entityType);
}
