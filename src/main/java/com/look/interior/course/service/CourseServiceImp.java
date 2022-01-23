package com.look.interior.course.service;

import com.look.entity.Buy;
import com.look.entity.Course;
import com.look.entity.CourseClass;
import com.look.entity.Publish;
import com.look.mapper.BuyMapper;
import com.look.mapper.CourseClassMapper;
import com.look.mapper.CourseMapper;
import com.look.mapper.PublishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImp implements CourseService{

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseClassMapper courseClassMapper;

    @Autowired
    PublishMapper publishMapper;

    @Autowired
    BuyMapper buyMapper;

    public int addCourse(String userAccount,Course course){

        try{
            //补全初始值
            course.setClicks(0);
            course.setStatus(0);
            course.setCost(0);
            courseMapper.insertSelective(course);
            int courseId = course.getId();
            CourseClass courseClass = course.getCourseClass();
            courseClass.setId(courseId);
            courseClassMapper.insertSelective(courseClass);
            Publish publish = new Publish(userAccount,courseId);
            publishMapper.insertSelective(publish);
            Buy buy = new Buy(userAccount,courseId);
            buyMapper.insertSelective(buy);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }


    public List<Course> getOnesCourse(String userAccount,String status){
        if(status.equals(""))status="%";
        List<Course> courses = courseMapper.queryPublishCourse(userAccount, status);
        return courses;
    }


    public int updateCourse(Course course){
        try {
            courseMapper.updateByPrimaryKeySelective(course);
            if(course.getCourseClass() != null){
                courseClassMapper.updateByPrimaryKeySelective(course.getCourseClass());
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 1;
    }

    public Course getCourseById(Integer id){
        Course course = courseMapper.querySingleCourse(id);
        return course;
    }

    public List<Course> getAllCourse(String keyword,String order,String age,String subject){
        List<Course> courses = null;
        if(keyword.equals("")){
            if(order.equals("")){
                courses = courseMapper.queryCourseInfo(age, subject);
            }else{
                courses = courseMapper.queryCourseInfoByClicks(order,age,subject);
            }
        }else {
            if(order.equals("")){
                courses = courseMapper.queryCourseInfoByKeyword(keyword,age,subject);
            }else{
                courses = courseMapper.queryCourseInfoByKeywordClicks(keyword, order,age,subject);
            }
        }
        return courses;
    }


    public List<Course> getCourseByStatus(Integer status){
        List<Course> courses = courseMapper.queryCourseByStatus(status);
        return courses;
    }

    public List<Course> getBoughtCourse(String userAccount){
        List<Course> courses = courseMapper.queryBoughtCourse(userAccount);
        return courses;
    }
}
