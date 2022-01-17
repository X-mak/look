package com.look.mapper;

import com.look.entity.Publish;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface PublishMapper extends Mapper<Publish> {

    @Select("select p.id,p.course_id,c.*  from publish p left join course c on p.course_id = c.id  where p.user_account = #{userAccount} and c.status = #{status}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_id",property = "courseId"),
            @Result(column = "course_id",property = "courses",
                    many = @Many(select = "com.look.mapper.CourseMapper.selectByPrimaryKey",
                            fetchType = FetchType.EAGER))
    })
    Publish queryPublishedCourse(String userAccount,Integer status);
}
