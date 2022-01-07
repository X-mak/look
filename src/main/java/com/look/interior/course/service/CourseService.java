package com.look.interior.course.service;

import com.look.entity.Course;

import java.util.List;

public interface CourseService {

    public int addCourse(String userAccount,Course course);

    public List<Course> getOnesCourse(String userAccount,int status);

    public int updateCourse(Course course);

    public Course getCourseById(Integer id);

    public List<Course> getAllCourse(String keyword,String order);

    public List<Course> getClassCourse(String age,String subject,String order);

}
