package com.look.mapper;

import com.look.entity.Course;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CourseMapper extends Mapper<Course> {

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id LEFT JOIN publish p ON p.course_id = c.id WHERE (c.course_name LIKE #{keyword} OR cs.age LIKE #{keyword} OR cs.subject LIKE #{keyword}) AND c.status=1 AND (cs.age LIKE #{age} AND cs.subject LIKE #{subject}) ORDER BY p.publish_date")
    @Results(id = "fullCourse",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "course_video",property = "courseVideo"),
            @Result(column = "course_img",property = "courseImg"),
            @Result(column = "id",property = "courseClass",
                    one = @One(select = "com.look.mapper.CourseClassMapper.selectByPrimaryKey",
                            fetchType = FetchType.EAGER))
    })
    List<Course> queryCourseInfoByKeyword(String keyword,String age,String subject);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id LEFT JOIN publish p ON p.course_id = c.id WHERE c.status=1 AND (cs.age LIKE #{age} AND cs.subject LIKE #{subject}) ORDER BY p.publish_date")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseInfo(String age,String subject);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE (c.course_name LIKE #{keyword} OR cs.age LIKE #{keyword} OR cs.subject LIKE #{keyword}) AND c.status=1 AND(cs.age LIKE #{age} AND cs.subject LIKE #{subject}) ORDER BY c.clicks DESC")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseInfoByKeywordClicks(String keyword,String order,String age,String subject);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE c.status=1 AND (cs.age LIKE #{age} AND cs.subject LIKE #{subject}) ORDER BY c.clicks DESC")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseInfoByClicks(String order,String age,String subject);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE c.id = #{id}")
    @ResultMap(value = "fullCourse")
    Course queryById(Integer id);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id LEFT JOIN publish p ON p.course_id = c.id WHERE cs.age LIKE #{age} AND cs.subject LIKE #{subject} ORDER BY p.publish_date")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseByClass(String age,String subject);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE cs.age LIKE #{age} AND cs.subject LIKE #{subject} ORDER BY c.clicks DESC")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseByClassClicks(String age,String subject,String order);

    @Select("SELECT c.*,cs.* FROM courseclass cs LEFT JOIN course c ON cs. id = c.id WHERE c.status=#{status}")
    @ResultMap(value = "fullCourse")
    List<Course> queryCourseByStatus(Integer status);


    @Select("SELECT c.* FROM course c LEFT JOIN publish p ON c.id = p.course_id WHERE p.user_account = #{userAccount} AND c.status LIKE #{status} ORDER BY p.publish_date DESC")
    @Results(id = "oneCourse",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "course_video",property = "courseVideo"),
            @Result(column = "course_img",property = "courseImg"),
    })
    List<Course> queryPublishCourse(String userAccount,String status);

    @Select("SELECT c.* FROM course c LEFT JOIN buy b ON b.course_id = c.id WHERE b.user_account=#{userAccount} ORDER BY b.date")
    @ResultMap(value="oneCourse")
    List<Course> queryBoughtCourse(String userAccount);

    @Select("SELECT c.* ,u.* FROM course c LEFT JOIN publish p ON p.course_id=c.id LEFT JOIN userinfo u ON u.user_account=p.user_account WHERE c.id=#{id}")
    @Results(id = "courseAndUser",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "course_video",property = "courseVideo"),
            @Result(column = "course_img",property = "courseImg"),
            @Result(column = "user_account",property = "userInfo",
                    one = @One(select = "com.look.mapper.UserInfoMapper.selectByPrimaryKey"))
    })
    Course querySingleCourse(Integer id);
}
