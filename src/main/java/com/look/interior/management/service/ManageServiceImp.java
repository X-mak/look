package com.look.interior.management.service;

import cn.hutool.core.date.DateUtil;
import com.look.entity.Buy;
import com.look.entity.Course;
import com.look.entity.SignRecord;
import com.look.entity.UserInfo;
import com.look.mapper.BuyMapper;
import com.look.mapper.CourseMapper;
import com.look.mapper.SignRecordMapper;
import com.look.mapper.UserInfoMapper;
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

    public List<Course> getBoughtCourses(String userAccount){
        List<Buy> buys = buyMapper.queryForCourses(userAccount);
        List<Course> ans = new ArrayList<>();
        for (Buy buy : buys){
            ans.add(buy.getCourse());
        }
        return  ans;
    }
}
