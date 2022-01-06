package com.look.interior.management.service;

import cn.hutool.core.date.DateUtil;
import com.look.entity.SignRecord;
import com.look.entity.UserInfo;
import com.look.mapper.SignRecordMapper;
import com.look.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ManageServiceImp implements ManageService{

    @Autowired
    SignRecordMapper signRecordMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    public int everydaySign(String userAccount){
        //登陆奖励
        int award = 10;

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
        return 1;
    }
}
