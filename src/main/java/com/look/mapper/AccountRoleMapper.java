package com.look.mapper;

import com.look.entity.AccountRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AccountRoleMapper extends Mapper<AccountRole> {

    @Select("SELECT a.*,u.* FROM accountrole a LEFT JOIN userrole u ON a.role_id = u.id WHERE a.user_account = #{userAccount}")
    @Results({
        @Result(id = true,column = "id",property = "id"),
        @Result(column = "role_id",property = "roleId"),
        @Result(column = "user_account",property = "userAccount"),
        @Result(column = "role_id",property = "userRole",
                one = @One(select = "com.look.mapper.UserRoleMapper.selectByPrimaryKey",
                            fetchType = FetchType.EAGER))
    })
    List<AccountRole> queryRoles(String userAccount);
}
