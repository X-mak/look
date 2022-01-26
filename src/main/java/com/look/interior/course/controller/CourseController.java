package com.look.interior.course.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.look.common.Result;
import com.look.entity.Comments;
import com.look.entity.Likes;
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
    public Result<?> getCoursesByStatus(@PathVariable Integer pageNum,@RequestParam Integer status,
                                        @RequestParam Integer pageSize){
        //设置每页数据量

        PageHelper.startPage(pageNum,pageSize,true);
        List<Course> courseByStatus = courseService.getCourseByStatus(status);
        PageInfo<Course> res = new PageInfo<>(courseByStatus);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
    }

    @PostMapping("/comments")
    public Result<?> addComment(@RequestBody Comments comments){
        int res = courseService.addComment(comments);
        if(res == -1)return Result.error("400","评论失败!");
        return Result.success("评论成功!");
    }

    @GetMapping("/comments/{pageNum}")
    public Result<?> getComments(@PathVariable Integer pageNum,@RequestParam Integer id,
                                 @RequestParam String userAccount,@RequestParam Integer pageSize,
                                 @RequestParam String order){
        PageHelper.startPage(pageNum,pageSize,true);
        List<Comments> comments = courseService.getComments(id, order, userAccount);
        PageInfo<Comments> res = new PageInfo<>(comments);
        long total = res.getTotal();
        return Result.success(res.getList(),total+"");
    }

    @DeleteMapping("/comments")
    public Result<?> deleteComments(@RequestParam Integer id){
        int res = courseService.deleteComment(id);
        if(res == -1)return Result.error("400","删除失败!");
        return Result.success("删除成功!");
    }

    @GetMapping("/comments/star")
    public Result<?> getCommentStars(@RequestParam String userAccount,@RequestParam Integer courseId){
        int stars = courseService.getStars(courseId, userAccount);
        if(stars == -1)return Result.error("400","还没有评分");
        return Result.success(stars,"已评分");
    }

    @PostMapping("/comments/like")
    public Result<?> likeComments(@RequestBody Likes likes){
        int res = courseService.likeComment(likes);
        if(res == -1)return Result.error("400","点赞失败!");
        return Result.success("点赞成功!");
    }


    @PostMapping("/history")
    public Result<?> watchedCourse(@RequestParam Integer id,@RequestParam String userAccount){
        int res = courseService.watchedCourse(userAccount, id);
        if(res == -1)return Result.error("400","记录失败!");
        return Result.success("记录成功!");
    }

    @GetMapping("/history/{pageNum}")
    public Result<?> getHistory(@PathVariable Integer pageNum,@RequestParam String userAccount,
                                @RequestParam Integer pageSize){
        PageInfo<Course> watchHistory = courseService.getWatchHistory(userAccount, pageNum, pageSize);
        long total = watchHistory.getTotal();
        return Result.success(watchHistory.getList(),total+"");
    }

}
