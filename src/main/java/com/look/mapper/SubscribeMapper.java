package com.look.mapper;

import com.look.entity.Subscribe;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SubscribeMapper extends Mapper<Subscribe> {

    @Select("SELECT COUNT(*) FROM subscribe WHERE follow_account=#{userAccount}")
    int queryCounts(String userAccount);

}
