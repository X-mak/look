package com.look.interior.course.service;

import com.github.pagehelper.PageInfo;
import com.look.entity.Comments;
import com.look.entity.Course;
import com.look.entity.Likes;


import java.util.List;

public interface CourseService {

    public int addCourse(String userAccount,Course course);

    public List<Course> getOnesCourse(String userAccount, String status);

    public int updateCourse(Course course);

    public Course getCourseById(Integer id);

    public List<Course> getAllCourse(String keyword,String order,String age,String subject);

    public List<Course> getBoughtCourse(String userAccount);

    public List<Course> getCourseByStatus(Integer status);

    public int addComment(Comments comments);

    public List<Comments> getComments(Integer id,String order,String userAccount);

    public int getStars(Integer courseId,String userAccount);

    public int watchedCourse(String userAccount,Integer courseId);

    public int deleteComment(Integer id);

    public int likeComment(Likes likes);

    public PageInfo<Course> getWatchHistory(String userAccount,Integer pageNum,Integer pageSize);

    public List<Course> recommendCourses(String age,Integer limit);
}
