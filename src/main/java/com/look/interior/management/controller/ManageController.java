package com.look.interior.management.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.look.common.Result;
import com.look.entity.Course;
import com.look.interior.management.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    ManageService manageService;

    @PostMapping("/sign/{userAccount}")
    public Result<?> signForTickets(@PathVariable String userAccount){
        int res = manageService.everydaySign(userAccount);
        if(res == -1)
            return Result.error("400","今日已签到!");
        return Result.success("签到成功!");
    }

    @GetMapping("/sign")
    public Result<?> checkSign(@RequestParam String userAccount,@RequestParam String date){
        int res = manageService.checkSign(userAccount, date);
        if(res == -1)
            return Result.error("400","已签到");
        return Result.success("未签到");
    }

    @PostMapping("/course/{courseId}")
    public Result<?> buyNewCourse(@PathVariable Integer courseId, @RequestParam String userAccount){
        int res = manageService.buyCourse(courseId, userAccount);
        if(res == -1)
            return Result.error("400","硬币不足!");
        return Result.success("购买成功!");
    }

    @GetMapping("/course/{courseId}")
    public Result<?> checkUserValid(@PathVariable Integer courseId, @RequestParam String userAccount){
        int res = manageService.validCourse(courseId, userAccount);
        if(res == -1)
            return Result.error("400","未购买本课程!");
        return Result.success("可以观看!");
    }


}
