package com.look.course.controller;

import com.look.common.Result;
import com.look.course.service.CourseService;
import com.look.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public Result<?> addCourse(@RequestBody Course course,@RequestParam String userAccount){
        int res = courseService.addCourse(userAccount, course);
        if(res == -1)
            return Result.error("400","添加失败!");
        return Result.success("添加成功!");
    }

    @GetMapping("/user/{userAccount}")
    public Result<?> selectOnesCourses(@PathVariable String userAccount){
        List<Course> onesCourse = courseService.getOnesCourse(userAccount);
        return Result.success(onesCourse,"搜索成功!");
    }

    @GetMapping("{id}")
    public Result<?> getOneCourse(@PathVariable int id){
        Course course = courseService.getCourseById(id);
        return Result.success(course,"搜索成功!");
    }

    @PutMapping("{id}")
    public Result<?> updateCourse(@RequestBody Course course,@PathVariable int id){
        course.setId(id);
        int res = courseService.updateCourse(course);
        if(res == -1)
            return Result.error("400","更新失败!");
        return Result.success("更新成功!");
    }

}
