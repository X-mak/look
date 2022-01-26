package com.look.mapper;

import com.look.entity.UserInfo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
}
