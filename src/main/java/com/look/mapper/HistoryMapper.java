package com.look.mapper;

import com.look.entity.History;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface HistoryMapper extends Mapper<History> {
}
