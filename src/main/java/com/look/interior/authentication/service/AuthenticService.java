package com.look.interior.authentication.service;

import com.github.pagehelper.PageInfo;
import com.look.combinedentity.user.LoginUser;
import com.look.entity.Subscribe;
import com.look.entity.UserAccount;
import com.look.entity.UserInfo;

public interface AuthenticService {

    //验证功能相关
    public int addAccount(UserAccount userAccount, Integer role , UserInfo userInfo);

    public LoginUser checkLogin(UserAccount userAccount);

    public int changePwd(UserAccount userAccount,String new_pwd);

    public int changeInfo(UserInfo userInfo);

    public int addRole(String userAccount,Integer role);

    public LoginUser getUser(String userAccount);



    //关注功能相关
    public int subscribeSomeone(Subscribe subscribe);

    public int cancelSubscribe(String mainAccount,String followAccount);

    public PageInfo<UserInfo> getSubscribeList(String userAccount,Integer pageNum,Integer pageSize);

    public int checkSubscribed(String mainAccount,String followAccount);

    public int countSubscribe(String userAccount);
}
