package com.look.interior.management.service;

import com.github.pagehelper.PageInfo;
import com.look.entity.Comments;
import com.look.entity.Course;

import java.util.List;

public interface ManageService {

    public int everydaySign(String userAccount);

    public int buyCourse(Integer courseId,String userAccount);

    public int validCourse(Integer courseId,String userAccount);

    public int checkSign(String userAccount,String date);

    public List<Course> test();

    public PageInfo<Course> subscribeUpdated(String userAccount, Integer pageNum, Integer pageSize);
}
