package com.look.course.service;

import com.look.entity.Course;
import com.look.entity.CourseClass;
import com.look.entity.Publish;
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
        return 1;
    }


    public List<Course> getOnesCourse(String userAccount){
        List<Course> ans = new ArrayList<Course>();
        Example example = new Example(Publish.class);
        example.createCriteria().andEqualTo("userAccount",userAccount);
        List<Publish> publishes = publishMapper.selectByExample(example);
        for(Publish publish : publishes){
            Course course = courseMapper.selectByPrimaryKey(publish.getCourseId());
            CourseClass courseClass = courseClassMapper.selectByPrimaryKey(publish.getCourseId());
            course.setCourseClass(courseClass);
            ans.add(course);
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
}
