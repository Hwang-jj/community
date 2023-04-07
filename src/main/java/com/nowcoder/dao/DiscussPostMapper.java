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


}
