package com.look.mapper;

import com.look.entity.Course;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CourseMapper extends Mapper<Course> {

    @Select("select c.*,p.publish_date,u.user_name from course c left join publish p on c.id=p.course_id left join userinfo u on u.user_account=p.user_account left join courseclass cs on c.id=cs.id WHERE (c.course_name LIKE #{keyword} OR cs.age LIKE #{keyword} OR cs.subject LIKE #{keyword}) AND c.status=1 AND (cs.age LIKE #{age} AND cs.subject LIKE #{subject}) ORDER BY p.publish_date")
    @ResultMap(value = "courseInfo")
    List<Course> queryCourseInfoByKeyword(String keyword,String age,String subject);

    @Select("select c.*,p.publish_date,u.user_name from course c left join publish p on c.id=p.course_id left join userinfo u on u.user_account=p.user_account left join courseclass cs on c.id=cs.id WHERE c.status=1 AND (cs.age LIKE #{age} AND cs.subject LIKE #{subject}) ORDER BY p.publish_date")
    @ResultMap(value = "courseInfo")
    List<Course> queryCourseInfo(String age,String subject);

    @Select("select c.*,p.publish_date,u.user_name from course c left join publish p on c.id=p.course_id left join userinfo u on u.user_account=p.user_account left join courseclass cs on c.id=cs.id WHERE (c.course_name LIKE #{keyword} OR cs.age LIKE #{keyword} OR cs.subject LIKE #{keyword}) AND c.status=1 AND(cs.age LIKE #{age} AND cs.subject LIKE #{subject}) ORDER BY c.clicks DESC")
    @ResultMap(value = "courseInfo")
    List<Course> queryCourseInfoByKeywordClicks(String keyword,String order,String age,String subject);

    @Select("select c.*,p.publish_date,u.user_name from course c left join publish p on c.id=p.course_id left join userinfo u on u.user_account=p.user_account left join courseclass cs on c.id=cs.id WHERE c.status=1 AND (cs.age LIKE #{age} AND cs.subject LIKE #{subject}) ORDER BY c.clicks DESC")
    @ResultMap(value = "courseInfo")
    List<Course> queryCourseInfoByClicks(String order,String age,String subject);

    @Select("select c.*,p.publish_date,u.user_name from course c left join publish p on c.id=p.course_id left join userinfo u on u.user_account=p.user_account left join courseclass cs on c.id=cs.id WHERE c.status=#{status}")
    @ResultMap(value = "courseInfo")
    List<Course> queryCourseByStatus(Integer status);

    @Select("SELECT c.*,p.publish_date,u.user_name FROM course c LEFT JOIN publish p ON c.id = p.course_id  left join userinfo u on u.user_account=p.user_account WHERE p.user_account = #{userAccount} AND c.status LIKE #{status} ORDER BY p.publish_date DESC")
    @ResultMap(value = "courseInfo")
    List<Course> queryPublishCourse(String userAccount,String status);

    @Select("SELECT c.*,p.publish_date,u.user_name FROM course c LEFT JOIN buy b ON b.course_id = c.id left join publish p on c.id=p.course_id left join userinfo u on u.user_account=p.user_account WHERE b.user_account=#{userAccount} ORDER BY b.date")
    @ResultMap(value = "courseInfo")
    List<Course> queryBoughtCourse(String userAccount);

    @Select("SELECT c.* ,u.*,publish_date,cs.age FROM course c LEFT JOIN publish p ON p.course_id=c.id LEFT JOIN userinfo u ON u.user_account=p.user_account left join courseclass cs on c.id=cs.id WHERE c.id=#{id}")
    @Results(id = "courseUserClass",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "course_video",property = "courseVideo"),
            @Result(column = "course_img",property = "courseImg"),
            @Result(column = "publish_date",property = "publishDate"),
            @Result(column = "age",property = "age"),
            @Result(column = "user_account",property = "userInfo",
                    one = @One(select = "com.look.mapper.UserInfoMapper.selectByPrimaryKey",
                            fetchType = FetchType.EAGER))
    })
    Course querySingleCourse(Integer id);

    @Select("SELECT c.*,h.date,u.user_name FROM course c LEFT JOIN history h ON c.id = h.course_id  left join userinfo u on u.user_account=h.user_account WHERE h.user_account = #{userAccount} ORDER BY h.date DESC")
    @Results(id = "historyCourseInfo",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "course_video",property = "courseVideo"),
            @Result(column = "course_img",property = "courseImg"),
            @Result(column = "date",property = "publishDate"),
            @Result(column = "user_name",property = "userName"),
    })
    List<Course> queryHistoryCourse(String userAccount);

    @Select("select c.*,p.publish_date,u.user_name from course c left join publish p on c.id=p.course_id left join userinfo u on u.user_account=p.user_account")
    @Results(id = "courseInfo",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "course_video",property = "courseVideo"),
            @Result(column = "course_img",property = "courseImg"),
            @Result(column = "publish_date",property = "publishDate"),
            @Result(column = "user_name",property = "userName"),
    })
    List<Course> queryCoursesInfo();

    @Select("SELECT c.*,p.publish_date,u.user_name FROM course c LEFT JOIN publish p ON c.id=p.course_id LEFT JOIN userinfo u ON u.user_account=p.user_account LEFT JOIN courseclass cs ON c.id=cs.id WHERE cs.age LIKE #{age} ORDER BY RAND() LIMIT #{limit}")
    @ResultMap(value = "courseInfo")
    List<Course> queryRandCourses(String age,Integer limit);

    @Select("SELECT c.*,u.user_name,p.publish_date FROM course c LEFT JOIN publish p ON p.course_id=c.id LEFT JOIN userinfo u ON u.user_account=p.user_account LEFT JOIN subscribe s ON s.main_account=u.user_account WHERE p.publish_date >= s.date AND s.follow_account=#{userAccount} ORDER BY p.publish_date DESC")
    @ResultMap(value = "courseInfo")
    List<Course> queryUpdatedCourses(String userAccount);

    @Select("SELECT c.*,p.publish_date,u.user_name FROM course c LEFT JOIN publish p ON c.id=p.course_id " +
            "LEFT JOIN userinfo u ON u.user_account=p.user_account ORDER BY p.publish_date DESC LIMIT 4")
    @ResultMap(value = "courseInfo")
    List<Course> queryRecentCourses();

    @Select("SELECT c.*,p.publish_date,u.user_name FROM course c LEFT JOIN publish p ON c.id=p.course_id " +
            "LEFT JOIN userinfo u ON u.user_account=p.user_account ORDER BY c.clicks DESC LIMIT 4")
    @ResultMap(value = "courseInfo")
    List<Course> queryHotCourses();

    @Select("SELECT c.*,p.publish_date,u.user_name FROM course c LEFT JOIN publish p ON c.id=p.course_id " +
            "LEFT JOIN userinfo u ON u.user_account=p.user_account WHERE c.cost=0 ORDER BY RAND() LIMIT 4")
    @ResultMap(value = "courseInfo")
    List<Course> queryFreeCourses();

    @Select("SELECT c.id,c.course_name,c.course_img,p.publish_date FROM course c LEFT JOIN publish p ON " +
            "p.course_id = c.id WHERE p.user_account=#{userAccount} ORDER BY p.publish_date DESC LIMIT 3")
    @Results(id = "smallCourse",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "course_name",property = "courseName"),
            @Result(column = "course_img",property = "courseImg"),
            @Result(column = "publish_date",property = "publishDate")
    })
    List<Course> queryRecentPublishedCourse(String userAccount);
}
