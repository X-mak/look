package com.look.mapper;

import com.look.entity.Buy;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BuyMapper extends Mapper<Buy> {

    @Select("SELECT b.*,c.* FROM buy b LEFT JOIN course c ON b.course_id = c.id WHERE b.user_account = #{userAccount}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "user_account",property = "userAccount"),
            @Result(column = "course_id",property = "courseId"),
            @Result(column = "course_id",property = "course",
                    one = @One(select = "com.look.mapper.CourseMapper.queryById",
                                fetchType = FetchType.EAGER))
    })
    List<Buy> queryForCourses(String userAccount);

}
