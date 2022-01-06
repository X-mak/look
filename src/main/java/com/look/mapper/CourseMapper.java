package com.look.mapper;

import com.look.entity.Course;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CourseMapper extends Mapper<Course> {

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE (c.course_name LIKE #{keyword} OR cs.age LIKE #{keyword} OR cs.subject LIKE #{keyword}) AND c.status=1 ")
    @Results(id = "fullCourse",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "course_video",property = "courseVideo"),
            @Result(column = "course_img",property = "courseImg"),
            @Result(column = "id",property = "courseClass",
                    one = @One(select = "com.look.mapper.CourseClassMapper.selectByPrimaryKey",
                            fetchType = FetchType.EAGER))
    })
    List<Course> queryCourseInfoByKeyword(String keyword);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE c.status=1 ")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseInfo();

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE (c.course_name LIKE #{keyword} OR cs.age LIKE #{keyword} OR cs.subject LIKE #{keyword}) AND c.status=1  ORDER BY c.clicks DESC")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseInfoByKeywordClicks(String keyword,String order);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE c.status=1 ORDER BY c.clicks DESC")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseInfoByClicks(String order);
}
