package com.look.course.service;

import com.look.entity.Course;
import com.look.entity.CourseClass;

import java.util.List;

public interface CourseService {

    public int addCourse(String userAccount,Course course);

    public List<Course> getOnesCourse(String userAccount);

    public int updateCourse(Course course);

    public Course getCourseById(Integer id);

}
