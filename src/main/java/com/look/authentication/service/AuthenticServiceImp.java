package com.look.authentication.service;

import com.look.entity.AccountRole;
import com.look.entity.UserAccount;
import com.look.entity.UserInfo;
import com.look.entity.UserRole;
import com.look.mapper.AccountRoleMapper;
import com.look.mapper.UserAccountMapper;
import com.look.mapper.UserInfoMapper;
import com.look.mapper.UserRoleMapper;
import com.look.utils.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticServiceImp implements AuthenticService{

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserAccountMapper userAccountMapper;

    @Autowired
    AccountRoleMapper accountRoleMapper;

    @Autowired
    Encode encode;

    public int addAccount(UserAccount userAccount, String roleName , UserInfo userInfo){
        UserAccount oldAccount = userAccountMapper.selectByPrimaryKey(userAccount.getUserAccount());
        if(oldAccount != null)
            return -1;
        //加密过程
        String randomNum = (int)((Math.random()*9+1)*100)+"";
        String encodePwd = encode.getSHA256StrJava(randomNum) + encode.getSHA256StrJava(userAccount.getUserPassword());
        userAccount.setUserPassword(encodePwd);

        //数据库操作
        userAccountMapper.insertSelective(userAccount);
        userInfoMapper.insertSelective(userInfo);
        UserRole userRole = new UserRole(roleName);
        userRoleMapper.insertSelective(userRole);
        int roleId = userRole.getId();
        AccountRole accountRole = new AccountRole(roleId,userAccount.getUserAccount());
        accountRoleMapper.insertSelective(accountRole);

        return 1;
    }
}
