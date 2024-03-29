package com.look.interior.management.service;

import cn.hutool.core.date.DateUtil;
import com.look.entity.*;
import com.look.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManageServiceImp implements ManageService{

    @Autowired
    SignRecordMapper signRecordMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    BuyMapper buyMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CommentsMapper commentsMapper;

    @Autowired
    SubscribeMapper subscribeMapper;

    public int everydaySign(String userAccount){
        //登陆奖励
        int award = 10;

        try{
            Example example = new Example(SignRecord.class);
            example.createCriteria().andEqualTo("userAccount",userAccount).andEqualTo("date",DateUtil.today());
            List<SignRecord> signRecords = signRecordMapper.selectByExample(example);
            if(signRecords.size() != 0){
                return -1;
            }
            //添加签到记录
            SignRecord signRecord = new SignRecord(userAccount);
            signRecordMapper.insertSelective(signRecord);
            //用户获得奖励
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userAccount);
            userInfo.setCoins(userInfo.getCoins()+award);
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 1;
    }


    public int buyCourse(Integer courseId,String userAccount){
        try{
            Course course = courseMapper.selectByPrimaryKey(courseId);
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userAccount);
            if(userInfo.getCoins() < course.getCost())
                return -1;
            userInfo.setCoins(userInfo.getCoins() - course.getCost());
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
            Buy buy = new Buy(userAccount,courseId);
            buyMapper.insertSelective(buy);
            Integer clicks = course.getClicks();
            course.setClicks(clicks+1);
            courseMapper.updateByPrimaryKeySelective(course);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 1;
    }


    public int validCourse(Integer courseId,String userAccount){

        try{
            Buy buy = new Buy(userAccount,courseId);
            Example example = new Example(Buy.class);
            example.createCriteria().andEqualTo("userAccount",userAccount).andEqualTo("courseId",courseId);
            List<Buy> buys = buyMapper.selectByExample(example);
            if(buys.size() == 0)
                return -1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 1;
    }




    public int checkSign(String userAccount,String date){
        Example example = new Example(SignRecord.class);
        try{
            example.createCriteria().andEqualTo("userAccount",userAccount).andEqualTo("date",date);
            List<SignRecord> signRecords = signRecordMapper.selectByExample(example);
            if(signRecords.size() > 0){
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public List<Course> test(){
        return courseMapper.queryCoursesInfo();
    }
}
