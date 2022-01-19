package com.look.interior.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.look.common.Result;
import com.look.entity.Publish;
import com.look.interior.course.service.CourseService;
import com.look.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/publish/{userAccount}/{pageNum}")
    public Result<?> selectOnesCourses(@PathVariable String userAccount,@RequestParam String status,
                                       @PathVariable Integer pageNum,@RequestParam Integer pageSize){
        //设置每页数据量
        String orderBy = "p.publish_date desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);

        List<Course> onesCourse = courseService.getOnesCourse(userAccount, status);
        PageInfo<Course> res = new PageInfo<>(onesCourse);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
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
    public Result<?> getSelectedCourses(@RequestParam String keyword, @RequestParam String order,
                                        @RequestParam String age, @RequestParam String subject,
                                        @PathVariable int pageNum, @RequestParam Integer pageSize){
        //设置每页数据量

        PageHelper.startPage(pageNum,pageSize,true);
        if(age.equals(""))age="%";
        if(subject.equals(""))subject="%";
        List<Course> allCourse = courseService.getAllCourse("%"+keyword+"%", order,age,subject);
        PageInfo<Course> res = new PageInfo<>(allCourse);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
    }


    @GetMapping("/bought/{pageNum}")
    public Result<?> getBoughtCourses(@PathVariable Integer pageNum,@RequestParam String userAccount,@RequestParam Integer pageSize){
        //设置每页数据量

        PageHelper.startPage(pageNum,pageSize,true);
        List<Course> boughtCourse = courseService.getBoughtCourse(userAccount);
        PageInfo<Course> res = new PageInfo<>(boughtCourse);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
    }

    @GetMapping("/status/{pageNum}")
    public Result<?> getCoursesByStatus(@PathVariable Integer pageNum,@RequestParam Integer status,@RequestParam Integer pageSize){
        //设置每页数据量

        PageHelper.startPage(pageNum,pageSize,true);
        List<Course> courseByStatus = courseService.getCourseByStatus(status);
        PageInfo<Course> res = new PageInfo<>(courseByStatus);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
    }
}
