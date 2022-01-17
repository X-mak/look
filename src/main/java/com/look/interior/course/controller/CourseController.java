package com.look.interior.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.look.common.Result;
import com.look.interior.course.service.CourseService;
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

    @GetMapping("/publish/{userAccount}")
    public Result<?> selectOnesCourses(@PathVariable String userAccount,@RequestParam int status){
        List<Course> onesCourse = courseService.getOnesCourse(userAccount,status);
        return Result.success(onesCourse,"搜索成功!");
    }

    @GetMapping("{id}")
    public Result<?> getOneCourse(@PathVariable int id){
        Course course = courseService.getCourseById(id);
        return Result.success(course,"搜索成功!");
    }

    @PutMapping
    public Result<?> updateCourse(@RequestBody Course course){
        int res = courseService.updateCourse(course);
        if(res == -1)
            return Result.error("400","更新失败!");
        return Result.success("更新成功!");
    }

    @GetMapping("/keyword/{pageNum}")
    public Result<?> getSelectedCourses(@RequestParam String keyword,@RequestParam String order,@PathVariable int pageNum){
        //设置每页数据量
        int pageSize = 1;

        PageHelper.startPage(pageNum,pageSize,true);
        List<Course> allCourse = courseService.getAllCourse("%"+keyword+"%", order);
        PageInfo<Course> res = new PageInfo<>(allCourse);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
    }

    @GetMapping("/class/{pageNum}")
    public Result<?> getSelectedCoursesByClass(@RequestParam String age,@RequestParam String subject,
                                               @RequestParam String order,@PathVariable int pageNum){
        //设置每页数据量
        int pageSize = 1;

        PageHelper.startPage(pageNum,pageSize,true);
        List<Course> classCourse = courseService.getClassCourse(age, subject, order);
        PageInfo<Course> res = new PageInfo<>(classCourse);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
    }

    @GetMapping("/status/{pageNum}")
    public Result<?> getCoursesByStatus(@PathVariable Integer pageNum,@RequestParam Integer status){
        //设置每页数据量
        int pageSize = 3;
        PageHelper.startPage(pageNum,pageSize,true);
        List<Course> courseByStatus = courseService.getCourseByStatus(status);
        PageInfo<Course> res = new PageInfo<>(courseByStatus);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
    }
}
