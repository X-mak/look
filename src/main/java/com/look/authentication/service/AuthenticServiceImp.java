package com.look.authentication.service;

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


    public LoginUser checkLogin(UserAccount userAccount){
        UserAccount selectedAccount = userAccountMapper.selectByPrimaryKey(userAccount.getUserAccount());
        //没有账号或者密码错误
        String encodePwd = encode.getSHA256StrJava(userAccount.getUserPassword());
        if(selectedAccount == null || ! encodePwd.equals(selectedAccount.getUserPassword().substring(64)))
            return null;
        Example example = new Example(AccountRole.class);

        //加入角色
        List<String> ans = new ArrayList<String>();
        example.createCriteria().andEqualTo("userAccount",userAccount.getUserAccount());
        List<AccountRole> accountRoles = accountRoleMapper.selectByExample(example);
        for(AccountRole i : accountRoles){
            UserRole userRole = userRoleMapper.selectByPrimaryKey(i.getRoleId());
            ans.add(userRole.getRoleName());
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userAccount.getUserAccount());

        return new LoginUser(userAccount.getUserAccount(),ans,userInfo.getUserName(),userInfo.getUserImg());
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
        UserInfo userInfo = new UserInfo(loginUser.getUserAccount(),loginUser.getUserName(),loginUser.getUserImg());
        userInfoMapper.updateByPrimaryKeySelective(userInfo);

        //更新用户角色,暂时不需要

        return 1;
    }
}
