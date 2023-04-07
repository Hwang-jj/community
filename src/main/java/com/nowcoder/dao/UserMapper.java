package com.nowcoder.dao;

import com.nowcoder.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    //通过id查询用户
    User selectById(int i);

    //通过username查询用户
    User selectByName(String username);

    //通过email查询用户
    User selectByEmail(String email);

    //添加一个用户
    int insertUser(User user);

    //dao层方法中，出现多个参数（参数类型不一致），在MyBatis配置文件中就要对参数进行类型声明
    //更新一个用户的状态
    int updateStatus(@Param("id") int id, @Param("status") int status);

    //更新一个用户的头像
    int updateHeader(@Param("id") int id, @Param("headerUrl") String headUrl);

    //更新一个用户的密码
    int updatePassword(@Param("id") int id, @Param("password") String password);
}
