package com.look.mapper;

import com.look.entity.Publish;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PublishMapper extends Mapper<Publish> {


}
