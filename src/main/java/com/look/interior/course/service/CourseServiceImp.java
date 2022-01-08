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
        //补全初始值
        course.setClicks(0);
        course.setStatus(0);
        courseMapper.insertSelective(course);
        int courseId = course.getId();
        CourseClass courseClass = course.getCourseClass();
        courseClass.setId(courseId);
        courseClassMapper.insertSelective(courseClass);
        Publish publish = new Publish(userAccount,courseId);
        publishMapper.insertSelective(publish);
        Buy buy = new Buy(userAccount,courseId);
        buyMapper.insertSelective(buy);
        return 1;
    }


    public List<Course> getOnesCourse(String userAccount,int status){
        List<Course> ans = new ArrayList<Course>();
        Example example = new Example(Publish.class);
        example.createCriteria().andEqualTo("userAccount",userAccount);
        List<Publish> publishes = publishMapper.selectByExample(example);
        for(Publish publish : publishes){
            Course course = courseMapper.selectByPrimaryKey(publish.getCourseId());
            if(course.getStatus() == status){
                CourseClass courseClass = courseClassMapper.selectByPrimaryKey(publish.getCourseId());
                course.setCourseClass(courseClass);
                ans.add(course);
            }
        }
        return ans;
    }


    public int updateCourse(Course course){
        courseMapper.updateByPrimaryKeySelective(course);
        if(course.getCourseClass() != null){
            courseClassMapper.updateByPrimaryKeySelective(course.getCourseClass());
        }
        return 1;
    }

    public Course getCourseById(Integer id){
        Course course = courseMapper.selectByPrimaryKey(id);
        course.setCourseClass(courseClassMapper.selectByPrimaryKey(id));
        return course;
    }

    public List<Course> getAllCourse(String keyword,String order){
        List<Course> courses = null;
        if(keyword.equals("")){
            if(order.equals("")){
                courses = courseMapper.queryCourseInfo();
            }else{
                courses = courseMapper.queryCourseInfoByClicks(order);
            }
        }else {
            if(order.equals("")){
                courses = courseMapper.queryCourseInfoByKeyword(keyword);
            }else{
                courses = courseMapper.queryCourseInfoByKeywordClicks(keyword, order);
            }
        }
        return courses;
    }

    public List<Course> getClassCourse(String age,String subject,String order){
        List<Course> courses = null;
        if(order.equals("")){
            courses = courseMapper.queryCourseByClass(age, subject);
        }else{
            courses = courseMapper.queryCourseByClassClicks(age, subject, order);
        }
        return courses;
    }
}
