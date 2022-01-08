package com.look.interior.authentication.service;

import com.look.combinedentity.user.LoginUser;
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
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

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

    public int addAccount(UserAccount userAccount, Integer role , UserInfo userInfo){
        UserAccount oldAccount = userAccountMapper.selectByPrimaryKey(userAccount.getUserAccount());
        if(oldAccount != null)
            return -1;
        //加密过程
        String randomNum = (int)((Math.random()*9+1)*100)+"";
        String encodePwd = encode.getSHA256StrJava(randomNum) + encode.getSHA256StrJava(userAccount.getUserPassword());
        userAccount.setUserPassword(encodePwd);

        //数据库操作
        userInfo.setCoins(0);
        userAccountMapper.insertSelective(userAccount);
        userInfoMapper.insertSelective(userInfo);
        AccountRole accountRole = new AccountRole(role,userAccount.getUserAccount());
        accountRoleMapper.insertSelective(accountRole);
        for (int i = 1; i < role; i++) {
            if(i == 2)continue;
            accountRole = new AccountRole(i,userAccount.getUserAccount());
            accountRoleMapper.insertSelective(accountRole);
        }
        return 1;
    }


    public LoginUser checkLogin(UserAccount userAccount){
        UserAccount selectedAccount = userAccountMapper.selectByPrimaryKey(userAccount.getUserAccount());
        //没有账号或者密码错误
        String encodePwd = encode.getSHA256StrJava(userAccount.getUserPassword());
        if(selectedAccount == null || ! encodePwd.equals(selectedAccount.getUserPassword().substring(64)))
            return null;

        //加入角色
        List<String> ans = new ArrayList<String>();
        List<AccountRole> accountRoles = accountRoleMapper.queryRoles(userAccount.getUserAccount());
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userAccount.getUserAccount());
        for (AccountRole accountRole : accountRoles){
            ans.add(accountRole.getUserRole().getRoleName());
        }
        return new LoginUser(userInfo,ans);
    }



    public int changePwd(UserAccount userAccount,String newPwd){
        UserAccount selectedAccount = userAccountMapper.selectByPrimaryKey(userAccount.getUserAccount());
        //没有账号或者密码错误
        String encodePwd = encode.getSHA256StrJava(userAccount.getUserPassword());
        if(selectedAccount == null || ! encodePwd.equals(selectedAccount.getUserPassword().substring(64)))
            return -1;

        //加密过程
        String randomNum = (int)((Math.random()*9+1)*100)+"";
        String newEncodePwd = encode.getSHA256StrJava(randomNum) + encode.getSHA256StrJava(newPwd);

        userAccountMapper.updateByPrimaryKeySelective(new UserAccount(userAccount.getUserAccount(),newEncodePwd));
        return 1;
    }



    public int changeInfo(LoginUser loginUser){

        //更新用户信息
        UserInfo userInfo = loginUser.getUserInfo();
        userInfoMapper.updateByPrimaryKeySelective(userInfo);

        //更新用户角色,暂时不需要

        return 1;
    }

    public int addRole(String userAccount,Integer role){
        AccountRole accountRole = new AccountRole(role,userAccount);
        accountRoleMapper.insertSelective(accountRole);
        return 1;
    }
}
