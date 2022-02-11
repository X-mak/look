package com.look.mapper;

import com.look.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserInfoMapper extends Mapper<UserInfo> {

    @Select("SELECT u.* FROM userinfo u LEFT JOIN subscribe s ON u.user_account = s.main_account WHERE s.follow_account = #{userAccount}")
    @Results(id = "singleUser",value = {
            @Result(id = true,column = "user_account",property = "userAccount"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "user_img",property = "userImg"),
    })
    List<UserInfo> queryMyLikes(String userAccount);

    @Select("SELECT * FROM userinfo  u WHERE u.user_account = #{userAccount}")
    @ResultMap(value = "singleUser")
    UserInfo queryOneUserInfo(String userAccount);

    @Select("SELECT u.user_account,u.user_name,u.user_img,u.fans FROM userinfo u LEFT JOIN accountrole ar ON ar.user_account=u.user_account " +
            "WHERE ar.role_id = 3 AND u.user_name LIKE #{keyword} ORDER BY u.fans DESC")
    @Results(id = "userPageInfo",value = {
            @Result(id = true,column = "user_account",property = "userAccount"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "user_img",property = "userImg"),
            @Result(column = "user_account",property = "recentCourse",
                    many = @Many(select = "com.look.mapper.CourseMapper.queryRecentPublishedCourse"))
    })
    List<UserInfo> querySelectedUserDown(String keyword);

    @Select("SELECT u.user_account,u.user_name,u.user_img,u.fans FROM userinfo u LEFT JOIN accountrole ar ON ar.user_account=u.user_account " +
            "WHERE ar.role_id = 3 AND u.user_name LIKE #{keyword} ORDER BY u.fans DEC")
    @ResultMap(value = "userPageInfo")
    List<UserInfo> querySelectedUserUp(String keyword);
}
