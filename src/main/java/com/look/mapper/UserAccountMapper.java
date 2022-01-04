package com.look.mapper;

import com.look.entity.UserAccount;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserAccountMapper extends Mapper<UserAccount> {
}
