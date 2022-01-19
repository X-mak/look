package com.look.interior.course.service;

import com.look.entity.Course;
import com.look.entity.Publish;

import java.util.List;

public interface CourseService {

    public int addCourse(String userAccount,Course course);

    public List<Course> getOnesCourse(String userAccount, String status);

    public int updateCourse(Course course);

    public Course getCourseById(Integer id);

    public List<Course> getAllCourse(String keyword,String order,String age,String subject);

    public List<Course> getBoughtCourse(String userAccount);

    public List<Course> getCourseByStatus(Integer status);

}
