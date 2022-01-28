package com.look.mapper;

import com.look.entity.Comments;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommentsMapper extends Mapper<Comments> {

    @Select("SELECT c.*,u.* FROM comments c LEFT JOIN userinfo u ON u.user_account = c.user_account WHERE course_id = #{courseId} ORDER BY c.date DESC")
    @Results(id = "singleComment",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "user_account",property = "userAccount"),
            @Result(column = "course_id",property = "courseId"),
            @Result(column = "user_account",property = "userInfo",
            one = @One(select = "com.look.mapper.UserInfoMapper.selectByPrimaryKey"))
    })
    List<Comments> queryCommentsOrderByTime(Integer courseId);

    @Select("SELECT c.* FROM comments c WHERE course_id = #{courseId} ORDER BY c.hot DESC")
    @ResultMap(value = "singleComment")
    List<Comments> queryCommentsOrderByHot(Integer courseId);
}
